package org.example;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class CatalogueMap extends Mapper<Object, Text, Text, Text> {
    static private boolean node_was_initialized = false;
    static private boolean isHeader = true;
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {

        if (isHeader)
        {
            isHeader = false;
            return;
        }

        String valueString = value.toString();
        String[] Columns = valueString.split(",");

        String cle = Columns[0].replaceAll("[^a-zA-Z0-9]", "I").toUpperCase();

        if (cle.startsWith("\"")) {
            cle = cle.substring(1); // Enlève le premier caractère (le guillemet)
        }
        // Rest of columns exemple :
        String res = Columns[1] + ","+Columns[2]+ ","+Columns[3]+ ","+Columns[4]+ ","+Columns[5]+ ","+Columns[6]+ ","+Columns[7]+ ","+Columns[8];

        // ajouter un tag sur les valeurs pour indiquer de quelle table ils viennent
        context.write(new Text(cle), new Text("CATALOGUE:" + res));

    }
    }
