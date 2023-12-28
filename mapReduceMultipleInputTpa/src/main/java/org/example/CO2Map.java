package org.example;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;

public class CO2Map extends  Mapper<Object, Text, Text, Text> {
    private static final Logger LOGGER = LogManager.getLogger(CO2Map.class);
    static private boolean node_was_initialized = false;
    protected void map(Object key,Text value,Context context)throws IOException,InterruptedException{
       //spliter by un espace entre le key et value
        String valueString = value.toString();
        String[] Columns = valueString.split("\t");

        //key = "MERCEDES" par exemple
        String cle = Columns[0];

        // le rest de columns par  exemple : Bonus/Malus : ...
        String res = Columns[1];
        // recuperer la ligne qui contient les moyennes de chaque column
        if (cle.equals("forAll")) {
            context.write(new Text("FIRST"), new Text(res));

        }else {

            // ajouter un tag sur les valeurs pour indiquer de quelle table ils viennent
            context.write(new Text(cle), new Text("CO2:" + res));

        }


    }


}
