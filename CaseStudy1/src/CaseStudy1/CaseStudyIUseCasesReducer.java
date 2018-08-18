package CaseStudy1;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;


public class CaseStudyIUseCasesReducer extends
			Reducer<Text, Text, Text, Text> {
	
		public void reduce(Text key, Iterable<Text> values, Context context)
				throws IOException, InterruptedException {
			String titles = "";
			double total = 0.0;
			int count = 0;
			System.out.println("Text Key    =>"+key.toString());
			for (Text t : values) {
				String parts[] = t.toString().split("\t");
				System.out.println("Text values =>"+t.toString());
				if (parts[0].equals("ratings")) {
					count++;
					String rating = parts[1].trim();
					System.out.println("Rating is =>"+rating);
					total += Double.parseDouble(rating);
				} else if (parts[0].equals("movies")) {
					titles = parts[1];
				}
			}
			
			double average = total / count;
			String str = String.format("%d\t%f", count, average);
			context.write(new Text(titles), new Text(str));
		}
	}