# README MapReduce
>HADOOP MAP REDUCE


# Sujet :
Toutes les taches réalisées et effectuées au sein de ce projet sont réalisés par  <br />

| **Nom / mail**                                |  **Groupe**                |
|-----------------------------------------------|----------------------------|
| BERRIRI Yassine / berririyassine@gmail.com     | Gr3                        |
| BOUCHEFFA Badis / boucheffa_badis@outlook.com | Gr3                        |
| KRIMI Ibrahim / ibrahimkrimi2@gmail.com        | Gr3                        |
| LARABI Marouane / larabimarouane@gmail.com     | Gr3                        |
| ZOUBID Dounia / dounia.zoubid2000@gmail.com   | Gr3                        |



## 1. Prise en main (script pour executer les jobs)


Nous avons mis en place un script Bash qui a pour but d'automatiser le processus d'exportation des résultats d'un job MapReduce dans Hadoop vers une table Hive. 
Le script exécute des jobs MapReduce, crée une table Hive et y charge les données.

Prérequis:<br />
• Hadoop installé et configuré

• Sqoop installé et configuré

• Hive installé et configuré

• Accès à un terminal Bash

### Configuration

Avant d'exécuter le script, assurez-vous de configurer les variables suivantes selon votre environnement :

### Chemins vers Hadoop, Hive : Si ces variables d'environnement ne sont pas déjà définies globalement, décommentez et renseignez les chemins appropriés.


```shell
#export HADOOP_HOME=/chemin/vers/hadoop

#export HIVE_HOME=/chemin/vers/hive

```
### Chemins vers les fichiers JAR : Remplacez les chemins par ceux de vos fichiers JAR MapReduce.
```shell
JAR_PATH_1="/chemin/vers/votre/premier/jar" 

JAR_PATH_2="/chemin/vers/votre/deuxieme/jar"


```

### Paramètres de connexion à Hive : Renseignez l'URL, l'utilisateur et le mot de passe de votre serveur Hive.
```shell
HIVE_URL="jdbc:hive2://localhost:10000"

HIVE_USER="votre_utilisateur"

HIVE_PASSWORD="votre_mot_de_passe"
```


### Nom de la table Hive : Définissez le nom de votre table Hive.
```shell
HIVE_TABLE="nom_de_votre_table_hive"

```

### Chemin HDFS vers le résultat Hadoop : Spécifiez le chemin HDFS où les résultats MapReduce sont stockés.
```shell
HADOOP_RESULT_PATH="hdfs:/chemin/vers/resultats"

```

## Pour lancer le script, utilisez la commande suivante dans votre terminal Bash :
```shell
./start.sh

```


## Notes

Le script supprimera toute table Hive existante portant le même nom avant de créer la nouvelle table. Assurez-vous que cela ne posera pas de problème avec vos données existantes.
Les sections commentées relatives à l'exportation Sqoop peuvent être décommentées si nécessaire, en fonction de vos besoins spécifiques.
## 2. Explication des scripts utilisés

Cette section va exposer les procédures suivies pour traiter le fichier CO2.csv et le fusionner avec la table du catalogue. 

Nous avons mis en œuvre deux jobs MapReduce distincts pour parvenir à notre objectif :

  - Automobile-2.0. (Data Munging).
  - AutomobileMultupleInput-2.0. (joining two data set).


### 2.1. Premiere job (Automobile-2.0)

Le job a pour responsabilité de purifier les données extraites du fichier CO2.csv, de pallier les lacunes informationnelles et de procéder au calcul de la moyenne générale

Nettoyage: <br />
![Premier job (Automobile-2 0)](https://github.com/Ibrahim-krimi/ProjetTPA/assets/104140096/456d8096-4d5d-4065-80b0-076e309b0409)
![Simulation job1-MapReduce drawio](https://github.com/Ibrahim-krimi/ProjetTPA/assets/104140096/37b9172a-6adc-4209-8d06-478ea3256f2f)

![Resultat_premier_Job](https://github.com/Ibrahim-krimi/ProjetTPA/assets/104140096/b0fd4808-6edd-4ea2-bff8-f8cd24690326)

![Deuxième job (Automobile-2 0) drawio](https://github.com/Ibrahim-krimi/ProjetTPA/assets/104140096/16a147a0-0d8f-4e45-b670-c16afef62fc8)
![Simulation job2 MapReduce drawio](https://github.com/Ibrahim-krimi/ProjetTPA/assets/104140096/06efa59f-5dd4-4e7b-8591-e01105f5cc32)
![Resultat_deuxième_Job](https://github.com/Ibrahim-krimi/ProjetTPA/assets/104140096/dade6e50-619e-4409-8254-257dd77244b4)



