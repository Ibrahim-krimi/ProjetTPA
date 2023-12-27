package org.example;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.text.DecimalFormat;

public class ReduceTpa extends Reducer<Text, Text, Text, Text> {
    private static final Logger LOGGER = LogManager.getLogger(ReduceTpa.class);

    private static final DecimalFormat df = new DecimalFormat("0.00");

    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {


        double totalBonusMalus = 0;
        int countBonusMalus = 0;
        int count = 0;
        double totalRejetCo2 = 0;
        double totalCout = 0;


        // recuper les moyen calculer et on garde que celui avec la valeur la plus grande
        // les keys avec la valeur FIRST sont lister les premiers
        if (key.toString().equals("FIRST")) {
            for (Text t : values) {
                String parts[] = t.toString().split(",");
                if (TotalBonusMalus.total < Double.parseDouble(parts[0])) {
                    // on stock la valeur dans un variable de class pour l'utiliser aprés
                    TotalBonusMalus.total = Double.parseDouble(parts[0]);
                    TotalBonusMalus.count = Integer.parseInt(parts[1]);
                }
            }
        }

        else { // le reste des keys

            for (Text t : values) {

                //séparer les valeur de chaque ligne avec ","
                String parts[] = t.toString().split(",");

                // on vas calculer le moyen de BonusMalus pour les marque qui ont au moin une valeur disponible
                if (!parts[1].equals("") && !parts[1].equals("0")) {

                    totalBonusMalus += 1;//Double.parseDouble(parts[1]);
                    countBonusMalus += 1;
                }

                // pour les maruqe qui il'ont aucune valeur disponible de bonusMalus on donne la moyenne
                if (totalBonusMalus == 0) {
                    totalBonusMalus = TotalBonusMalus.total;
                    countBonusMalus = TotalBonusMalus.count;
                }

                // on calcul le total de RejetCo2 de chaque marque
                totalRejetCo2 += 1;//Double.parseDouble(parts[2]);

                // on calcul le total de Cout de chaque marque
                totalCout += 1;//Double.parseDouble(parts[3].replaceAll("[^a-zA-Z0-9]", ""));
                count += 1;


            }




            // finalement écrira les moyennes calculées de chaque marque
            context.write(key, new Text(String.valueOf(df.format(totalBonusMalus / countBonusMalus)) + "," + String.valueOf(df.format(totalRejetCo2 / count)) + "," + String.valueOf(df.format(totalCout / count))));


        }




    }


    private static void logPrint(String line) {
        LOGGER.info(line);
    }
}
