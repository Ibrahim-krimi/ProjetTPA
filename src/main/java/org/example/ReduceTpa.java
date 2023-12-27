package org.example;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class ReduceTpa extends Reducer<Text, Text, Text, Text> {

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context)
            throws IOException, InterruptedException {

        // Example of a simple reduce operation: concatenate all values
        StringBuilder sum = new StringBuilder();
        for (Text val : values) {
            sum.append(val.toString());
            sum.append(", ");
        }

        context.write(key, new Text(sum.toString()));
    }
}
