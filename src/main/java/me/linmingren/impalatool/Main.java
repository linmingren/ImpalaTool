package me.linmingren.impalatool;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class Main 
{
    public static void main( String[] args ) throws IOException
    {
    	if (args.length < 2) {
			System.out.println("Usage: <hdfs ip> <hdfs folder>");
		}
		
		String hdfsIp = args[0];
		String hdfsFolder = args[1];
		
		ParquetFileParser parser = new ParquetFileParser(hdfsIp, hdfsFolder) ;
		if (!parser.init()) {
			return;	
		}
		
		
		ImpalaTable table = new ImpalaTable("admin",parser.getName(), hdfsFolder,parser.getColumns());
		table.generate();
    }
}
