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

        if (isHeader) {
            isHeader = false; // Set to false after processing the header line
            return; // Skip further processing for header line
        }
        //séparer les valeur de chaque ligne avec ","
        String valueString = value.toString();
        String[] Columns = valueString.split(",");

        //recuperer la marque de voiture dans chaque ligne et la netoyer.
        //String myKey = Columns[1].split(" ")[0].replaceAll("[^a-zA-Z0-9]", "");
        // String[] words = replaceAccents(Columns[1]).split("\\s+"); // Split the string into words using space as the delimiter
        String[] words = Columns[1].split("\\s+");
        String myKey = "";
        if (words.length > 0 && words != null) {
            myKey = words[0]; // Take the first word as the car make
        }
        // recuperer le premier char dans la colonne 'bonusMalus'
        String originalBonusMalus = Columns[2].replaceAll("[^a-zA-Z0-9]", "");;
        String bonusMalus;
        // Regular expression to extract numeric part (including possible leading -/+)
        //recuperer le numero de la ligne
        int id = Integer.parseInt(Columns[0].replaceAll("[^a-zA-Z0-9]", ""));
        if (id < 57) {
            originalBonusMalus = originalBonusMalus.replaceAll("1", "");
        }
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
      //  TotalBonusMalus.total += bonusMalusValue;

        try {
            TotalBonusMalus.total += bonusMalusValue;
            TotalBonusMalus.totalCO2 += Double.parseDouble(Columns[3].replaceAll("[^\\d.]", ""));
            String cleanedValue = Columns[4].replaceAll("[^\\d.]", ""); // Supprime tous les caractères non numériques
            TotalBonusMalus.totalCoutEnergie += Double.parseDouble(cleanedValue);
        } catch (NumberFormatException e) {
            LOGGER.error("Erreur de formatage de nombre: " + Columns[4], e);
            LOGGER.error("Erreur de formatage de nombre: " + Columns[3], e);
            // Vous pouvez choisir de définir une valeur par défaut ou de gérer l'erreur comme vous le souhaitez
        }

        TotalBonusMalus.count += 1;
      //  logPrint(" tBonusMalusTotal " + TotalBonusMalus.total + " countTotal " + TotalBonusMalus.count);
      //  logPrint("La marque "+ myKey +" BonusMalus " + bonusMalus + " rejetCO2 " + Columns[3] + " cout energie " + Columns[4]);
        //String myValue = Columns[1].split(" ")[1].replaceAll("[^a-zA-Z0-9]", "") + "," + bonusMalus + "," + Columns[3] + "," + Columns[4];
        String myValue =  Columns[1].split("\\s+")[1].replaceAll("[^a-zA-Z0-9]", "") + "," + bonusMalus + "," + Columns[3] + "," + Columns[4];
        String myValue1 = String.valueOf(TotalBonusMalus.total) + "," + String.valueOf(TotalBonusMalus.totalCO2) + "," + String.valueOf(TotalBonusMalus.totalCoutEnergie) + "," + String.valueOf(TotalBonusMalus.count);
        context.write(new Text("FIRST"), new Text(myValue1));


        if (myKey.startsWith("\"")) {
            myKey = myKey.substring(1); // Enlève le premier caractère (le guillemet)
        }

        logPrint("First ==== tBonusMalusTotal " + TotalBonusMalus.total + " countTotal " + TotalBonusMalus.count);
        logPrint(myKey + "BonusMalus " + bonusMalus + " rejetCO2 " + Columns[3] + " cout energie " + Columns[4]);

        context.write(new Text(myKey), new Text(myValue));

    }

    private static void logPrint(String line) {
        LOGGER.info(line);
    }
    // Custom method to replace accented characters with their ASCII equivalents
    private static String replaceAccents(String input)  {
        String output = Normalizer.normalize(input, Normalizer.Form.NFD);
        output = output.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return output;
    }

}
