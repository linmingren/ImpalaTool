package me.linmingren.impalatool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import parquet.format.converter.ParquetMetadataConverter;
import parquet.hadoop.ParquetFileReader;
import parquet.hadoop.metadata.ColumnChunkMetaData;
import parquet.hadoop.metadata.ParquetMetadata;

public class ParquetFileParser {
	private String hdfsIp;
	private String hdfsFolder;
	private String folderName;
	List<String[]> columns = new ArrayList<String[]>();
	
	public ParquetFileParser(String hdfsIp, String hdfsFolder) {
		this.hdfsFolder = hdfsFolder;
		this.hdfsIp = hdfsIp;
	}
	
	public boolean init() throws IOException {
		
		Configuration conf = new Configuration();
		//conf.addDefaultResource("/etc/hadoop/conf.cloudera.hdfs/core-site.xml");
		//"hdfs://" + ip +  ":8020/" + 
		Path path = new Path("hdfs://" + hdfsIp +  ":8020/" + hdfsFolder);
		
		
		//FileSystem hdfs = FileSystem.get(URI.create(filePath), conf);
		FileSystem hdfs = path.getFileSystem(conf);
		
		if (!hdfs.exists(path)) {
			System.out.println("Folder [" + hdfsFolder + "] doesn't exist");
			return false;
		}
		
		if (!hdfs.isDirectory(path)) {
			System.out.println("[" + hdfsFolder + "] is not a folder!");
			return false;
		}
		
		FileStatus[] stats = hdfs.listStatus(path);
		if (stats.length == 0) {
			System.out.println("There is no files under folder [" + hdfsFolder + "]");
			return false;
		}
		
		folderName = path.getName();
		
		//read a file to get the meta data
		ParquetMetadata meta = ParquetFileReader.readFooter(conf, stats[0].getPath(),ParquetMetadataConverter.NO_FILTER);
		
		
		for (ColumnChunkMetaData c : meta.getBlocks().get(0).getColumns()) {
			System.out.println("column: " + c.getPath().toString() +  " type: " + c.getType().toString());
			String[] t = new String[2];
			String columnName = c.getPath().toString();
			t[0] = columnName.substring(1, columnName.length() -1) ;
			t[1] = c.getType().toString();
			columns.add(t);
		}
		
		return true;
	}
	
	List<String[]> getColumns() {
		return columns;
	}
	
	String getName() {
		return folderName;
	}
}
