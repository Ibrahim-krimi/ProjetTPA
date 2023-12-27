package org.example;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        // Instantiate the Hadoop Configuration.
        Configuration conf = new Configuration();

        // Parse command-line arguments.

        String[] ourArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
        // Check input arguments.
        if (ourArgs.length <= 1) {
            System.err.println("Usage: TPA <in> <out>");
            System.exit(1);
        }

        // Get a Job instance.
        Job job = Job.getInstance(conf, "TPA");
        // Setup the Driver/Mapper/Reducer classes.
        job.setJarByClass(Main.class);
        job.setMapperClass(MapTpa.class);
        //  Configurez le Reducer en fonction du paramètre
        job.setReducerClass(ReduceTpa.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        // Indicate from where to read input data from HDFS.
        FileInputFormat.addInputPath(job, new Path(ourArgs[0]));
        //FileInputFormat.addInputPath(job, new Path(ourArgs[1]));

        // Indicate where to write the results on HDFS.
        FileOutputFormat.setOutputPath(job, new Path(ourArgs[1]));

        // We start the MapReduce Job execution (synchronous approach).
        // If it completes with success we exit with code 0, else with code 1.
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }}