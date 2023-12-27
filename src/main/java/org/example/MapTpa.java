package org.example;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.Normalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class MapTpa extends  Mapper<Object, Text, Text, Text> {
    private static final Logger LOGGER = LogManager.getLogger(MapTpa.class);
    static private PrintStream console_log;
    static private boolean node_was_initialized = false;
    private Text word = new Text();
    private boolean isHeader = true;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        // Récupérez les paramètres depuis la configuration
        //QuestionNumber = context.getConfiguration().get("monParam1");
        //param = context.getConfiguration().get("monParam2");
        String param1 = context.getConfiguration().get("monParam1");
        String param2 = context.getConfiguration().get("monParam2");
    }

    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {

        //séparer les valeur de chaque ligne avec ","
        String valueString = value.toString();
        String[] Columns = valueString.split(",");

        //recuperer la marque de voiture dans chaque ligne et la netoyer.
        //String myKey = Columns[1].split(" ")[0].replaceAll("[^a-zA-Z0-9]", "");
        String myKey = replaceAccents(Columns[1]).replaceAll("[^a-zA-Z0-9]", "");

        // recuperer le premier char dans la colonne 'bonusMalus'
        String originalBonusMalus = Columns[2];
        String bonusMalus;
        //recuperer le numero de la ligne
         //int id = Integer.parseInt(Columns[0].replaceAll("[^a-zA-Z0-9]", ""));
         int id = 99999;
        /*
        if (!Columns[0].trim().isEmpty()){
             id = Integer.parseInt(Columns[0]);
        }
        if (originalBonusMalus.equals("-")){
            bonusMalus = "+0";
        }
        else {
            bonusMalus = originalBonusMalus.charAt(0) + originalBonusMalus.substring(1).replaceAll("[^a-zA-Z0-9]", "");

            if (id < 54) {
                bonusMalus = bonusMalus.replaceAll("1", "");
            }

            if (!bonusMalus.equals("+0")) {
                TotalBonusMalus.total += Integer.parseInt(bonusMalus);
                TotalBonusMalus.count += 1;
                logPrint(" tBonusMalus " + TotalBonusMalus.total + "count " + TotalBonusMalus.count);
            }

        }
             */
        // Regular expression to extract numeric part (including possible leading -/+)
        Pattern pattern = Pattern.compile("[-+]?\\d+");
        Matcher matcher = pattern.matcher(originalBonusMalus);
        if (matcher.find()) {
            bonusMalus = matcher.group().replaceAll("\\D+", "");
        } else {
            bonusMalus = "0"; // Default to 0 if no number found
        }

        // Convert to double and format
        double bonusMalusValue;
        try {
            bonusMalusValue = Double.parseDouble(bonusMalus);
        } catch (NumberFormatException e) {
            bonusMalusValue = 0.0; // Default to 0.0 if parsing fails
        }

        // Log and accumulate
        TotalBonusMalus.total += bonusMalusValue;
        TotalBonusMalus.count += 1;
        logPrint("La marque "+ myKey +" tBonusMalus " + TotalBonusMalus.total + " count " + TotalBonusMalus.count);
        String myValue = Columns[1].split(" ")[1].replaceAll("[^a-zA-Z0-9]", "") + "," + bonusMalus + "," + Columns[3] + "," + Columns[4];
        String myValue1 = String.valueOf(TotalBonusMalus.total) + "," + String.valueOf(TotalBonusMalus.count);
        context.write(new Text("moyenne"), new Text(myValue1));
        context.write(new Text(myKey), new Text(myValue));
        /*

        //construire la valeur qui sera passer au reducer
        String myValue = Columns[1].split(" ")[1].replaceAll("[^a-zA-Z0-9]", "") + "," + bonusMalus + "," + Columns[3] + "," + Columns[4];
        //construire la valeur pour le total des BonnusMalus
        String myValue1 = String.valueOf(TotalBonusMalus.total) + "," + String.valueOf(TotalBonusMalus.count);
        //ecrire les valeurs obtenu de total des BonnusMalus
        context.write(new Text("moyenne"), new Text(myValue1));
        // ecrire la valaur construite dans cette format : key = marque , value le reste de la ligne
        context.write(new Text(myKey), new Text(myValue));
         */

    }

    /*
    private static void logPrint(String line) {
        if (!node_was_initialized) {
            try {
                console_log = new PrintStream(new FileOutputStream("/tmp/my_mapred_log.txt", true));
            } catch (FileNotFoundException e) {
                return;
            }
            node_was_initialized = true;
        }
        console_log.println(line);
    }

     */

    private static void logPrint(String line) {
        LOGGER.info(line);
    }
    // Custom method to replace accented characters with their ASCII equivalents
    private static String replaceAccents(String input) {
        String output = Normalizer.normalize(input, Normalizer.Form.NFD);
        output = output.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return output;
    }

}
