package class5Counters;

import java.util.Calendar;
import java.util.Date;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


public class CustomCounterMapper extends Mapper<LongWritable,Text, Text, Text> {
        private Text out = new Text();

        protected void map(LongWritable key, Text value, Context context)
            throws java.io.IOException, InterruptedException {
        	String line = value.toString();
        	String[] strts = line.split(",");
        	long lts = Long.parseLong(strts[1]);
        	Date time = new Date(lts);
        	int m = time.getMonth();
        	
        	if(m==11){
        		context.getCounter(CustomCounter.MONTH.DEC).increment(1);	
        	}
        	if(m==0){      	  	
      	  		context.getCounter(CustomCounter.MONTH.JAN).increment(1);
        	}
        	if(m==1){
      	  		context.getCounter(CustomCounter.MONTH.FEB).increment(1);
        	}
      	  	out.set("success");
      	  context.write(out,out);
        }  
}
