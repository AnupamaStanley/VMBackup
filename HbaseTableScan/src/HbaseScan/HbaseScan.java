package HbaseScan;

import java.io.IOException;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.conf.*;

@SuppressWarnings("deprecation")
public class HbaseScan {
	public static void main(String args[]) throws IOException{
		Configuration conf = HBaseConfiguration.create();    	// Instantiate Configuration class
		HTable table = new HTable(conf, "Transactions");    	// Instantiate HTable class. TRANSACTIONS is the table name
		Scan scan = new Scan();  	// Instantiate the Scan class
		
		//Scan the required columns, custom_details is the column family and column is fname
		scan.addColumn(Bytes.toBytes("customerdetails"), Bytes.toBytes("fname"));  
		ResultScanner scanner = table.getScanner(scan);  	// Get scan result
		
		// Reading values from scan result
		for (Result result = scanner.next(); result != null; result = scanner.next())
		System.out.println("Result Found: " + result);
		scanner.close();  	//close the scanner
	}
}