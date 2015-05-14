package me.linmingren.impalatool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;


public class ImpalaTable {
	private static final String CREATE_TABLE_SQL = "CREATE EXTERNAL TABLE {database}.{table} ({columns}) STORED AS PARQUET LOCATION '{location}'";
	
	private String database;
	private String tableName;
	private String storeLocation;
	private List<String[]> columns;
	
	
	public ImpalaTable(String database,String tableName, String storeLocation, List<String[]> columns) {
		this.database = database;
		this.tableName = tableName;
		this.storeLocation = storeLocation;
		this.columns = columns;
	}
	
	private String toImpalaType(String type) {
		if (type.equals("INT64")) {
			return "BIGINT";
		}

		if (type.equals("BINARY")) {
			return "STRING";
		}

		if (type.equals("INT32")) {
			return "INT";
		}

		return type;
	}
	 
	public  void generate() throws IOException {
		
		String columns = "";
		for (String[] c : this.columns) {
			columns += c[0]+ " " + toImpalaType(c[1]) + ",";
		}
		columns = columns.substring(0, columns.length() - 1);
		
		String createTableSql = CREATE_TABLE_SQL.replace("{table}", tableName);
		createTableSql = createTableSql.replace("{columns}", columns);
		createTableSql = createTableSql.replace("{location}", storeLocation);

		createTableSql = createTableSql.replace("{database}", database);
		
		Process p = new ProcessBuilder("impala-shell", "-q", createTableSql).start();

		InputStream es = p.getErrorStream();
		InputStreamReader esr = new InputStreamReader(es);
		BufferedReader er = new BufferedReader(esr);

		String line;

		while ((line = er.readLine()) != null) {
			System.out.println(line);
		}
	}
}
