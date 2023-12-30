#!/bin/bash

# Définition des variables d'environnement pour Hadoop et Sqoop
#export HADOOP_HOME=/chemin/vers/hadoop
#export SQOOP_HOME=/chemin/vers/sqoop
#export HIVE_HOME=/chemin/vers/hive
#export PATH=$PATH:$HADOOP_HOME/bin:$SQOOP_HOME/bin:$HIVE_HOME/bin

# Chemins vers les fichiers JAR
JAR_PATH_1="/vagrant/TPA/Ressources/projetTpa/dataSourceHDFS/mapReduceTpa/target/TpaMapReduce-1.0.jar"
JAR_PATH_2="/vagrant/TPA/Ressources/projetTpa/dataSourceHDFS/mapReduceMultipleInputTpa/target/TpaMapReduce-1.0.jar"

# Suppression des résultats précédents dans HDFS si ils existent
hadoop fs -rm -r /result
hadoop fs -rm -r /myres

# Exécution du programme MapReduce pour le premier fichier JAR
hadoop jar $JAR_PATH_1 org.example.Main CO2.csv /result

# Exécution du programme MapReduce pour le deuxième fichier JAR
hadoop jar $JAR_PATH_2 org.example.Main Catalogue.csv /result/part-r-00000 /myres

# Vérifiez que les jobs MapReduce ont réussi
if [ $? -ne 0 ]; then
    echo "Les jobs MapReduce ont échoué."
    exit 1
fi

# Paramètres de connexion à Hive
HIVE_URL="jdbc:hive2://localhost:10000"
HIVE_USER="oracle"
HIVE_PASSWORD="test" # Mettez le mot de passe si nécessaire

# Nom de la table Hive
HIVE_TABLE="Catalogue_physique"

# Chemin HDFS vers le résultat Hadoop
HADOOP_RESULT_PATH="hdfs:/myres"

# Suppression de la table Hive si elle existe
beeline -u $HIVE_URL -n $HIVE_USER -p $HIVE_PASSWORD -e "DROP TABLE IF EXISTS $HIVE_TABLE;"

# Création de la table Hive
beeline -u $HIVE_URL -n $HIVE_USER -p $HIVE_PASSWORD -e "
CREATE TABLE IF NOT EXISTS $HIVE_TABLE (
    marque STRING,
    nom STRING,
    puissance INT,
    longueur STRING,
    nbPlaces INT,
    nbPortes INT,
    couleur STRING,
    occasion BOOLEAN,
    prix FLOAT,
    bonusMalus FLOAT,
    rejetsCO2 FLOAT,
    coutEnergie FLOAT
)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
STORED AS TEXTFILE
LOCATION '$HADOOP_RESULT_PATH';
"

# Vérification de la création de la table Hive
if [ $? -ne 0 ]; then
    echo "La création de la table Hive a échoué."
    exit 1
fi

echo "Création de la table Hive réussie."


# Exportation des résultats vers Hive avec Sqoop
#sqoop export --connect $HIVE_URL \
#             --table $HIVE_TABLE \
#             --export-dir $HADOOP_RESULT_PATH \
#             --input-fields-terminated-by ',' \
#             --username $HIVE_USER \
#             --password $HIVE_PASSWORD

# Vérification de l'exportation Sqoop
#if [ $? -ne 0 ]; then
#    echo "L'exportation Sqoop a échoué."
#    exit 1
#fi

#echo "Exportation Sqoop réussie."
