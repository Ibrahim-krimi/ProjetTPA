package org.example;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class Main {

    //Creation de l'objet de Configuration Hadoop
    public static void main(String[] args) throws Exception
    {
        Configuration conf=new Configuration();
        Job job=Job.getInstance(conf, "TPA2");
        // Deefini les classes driver, map et reduce.
        job.setJarByClass(Main.class);
        job.setReducerClass(ReduceTpa.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        // Deefini types clefs/valeurs de notre programme Hadoop.
        MultipleInputs.addInputPath(job, new Path(args[0]), TextInputFormat.class, CatalogueMap.class);
        MultipleInputs.addInputPath(job, new Path(args[1]),TextInputFormat.class, CO2Map.class);
        Path outputPath = new Path(args[2]);
        FileOutputFormat.setOutputPath(job, outputPath);

        if(job.waitForCompletion(true))
            System.exit(0);
        System.exit(-1);
    }
}
