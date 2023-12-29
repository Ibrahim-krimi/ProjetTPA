vagrant up


vagrant ssh


start-yarn.sh


start-dfs.sh


cd /vagrant

cd projetTpa/dataSourceHDFS*


java -jar $KVHOME/lib/sql.jar -helper-hosts localhost:5000 -store kvstore



 nohup: ignoring input and redirecting stderr to stdout
nohup hiveserver2 > /dev/null &

[vagrant@oracle-21c-vagrant dataSourceHDFS]$ nohup hiveserver2 > /dev/null &


beeline


**** verif ****

select * from CO2_HDFS_EXT;

 cd target

 hadoop fs -rm -r /result

 hadoop jar TpaMapReduce-1.0.jar  org.example.Main CO2.csv result

 hadoop jar TpaMapReduce-1.0.jar  org.example.TpaMapReduce catalogue.csv /result/part-r-00000/myres

***Probleme java  ****
sudo update-alternatives --config java
CHOISIR 1.8






