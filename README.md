# README 

## Description

le script Bash automatise le processus d'exportation des résultats d'un job MapReduce dans Hadoop vers une table Hive. Le script exécute des jobs MapReduce, crée une table Hive et y charge les données.

Prérequis

Hadoop installé et configuré

Sqoop installé et configuré

Hive installé et configuré

Accès à un terminal Bash

### Configuration

Avant d'exécuter le script, assurez-vous de configurer les variables suivantes selon votre environnement :

### Chemins vers Hadoop, Hive : Si ces variables d'environnement ne sont pas déjà définies globalement, décommentez et renseignez les chemins appropriés.

#export HADOOP_HOME=/chemin/vers/hadoop

#export HIVE_HOME=/chemin/vers/hive


### Chemins vers les fichiers JAR : Remplacez les chemins par ceux de vos fichiers JAR MapReduce.

JAR_PATH_1="/chemin/vers/votre/premier/jar" 

JAR_PATH_2="/chemin/vers/votre/deuxieme/jar"

### Paramètres de connexion à Hive : Renseignez l'URL, l'utilisateur et le mot de passe de votre serveur Hive.

HIVE_URL="jdbc:hive2://localhost:10000"

HIVE_USER="votre_utilisateur"

HIVE_PASSWORD="votre_mot_de_passe"

### Nom de la table Hive : Définissez le nom de votre table Hive.

HIVE_TABLE="nom_de_votre_table_hive"

### Chemin HDFS vers le résultat Hadoop : Spécifiez le chemin HDFS où les résultats MapReduce sont stockés.

HADOOP_RESULT_PATH="hdfs:/chemin/vers/resultats"

## Pour lancer le script, utilisez la commande suivante dans votre terminal Bash :

./start.sh


## Notes

Le script supprimera toute table Hive existante portant le même nom avant de créer la nouvelle table. Assurez-vous que cela ne posera pas de problème avec vos données existantes.
Les sections commentées relatives à l'exportation Sqoop peuvent être décommentées si nécessaire, en fonction de vos besoins spécifiques.
