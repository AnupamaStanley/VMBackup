package class5Todo;

import java.io.IOException;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.io.*;


public class HighestReducer extends Reducer<Text, IntWritable, Text, IntWritable>
{
      
      public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException
      {
    	  int max_temp = 0; 
          for (IntWritable value : values){
        	  int current= value.get();
                     if ( max_temp <  current)  
                        	 max_temp =  current;
                      }
          context.write(key, new IntWritable(max_temp/10));

      }
}
      
      
      
      
     
