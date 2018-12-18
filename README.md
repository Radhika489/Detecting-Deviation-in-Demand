# Detecting-Deviation-in-Demand
This project was aim to find sudden spike in demand for electricity power.
Set of IoT devices who measures Current and Voltage in different power meters and fed that in streaming data with every 15 minutes.
*I have prepared a csv file on my own having columns: User, Time Slot, Day1, Day2 and so on*
After every 15 minutes power value from each power meter in locality is streamed through Kafka. It will be a new performance indicatorin which streaming data through KAFKA is feeded to Spark for processing. As structure streaming can support variety of formats like file, csv , json and kafka. So stream data passed into dataframe and schema is defined for the parsed data and after that standard deviation of the input value(by considering last 40 values of the power) is computed and it will be compared to the current value of power.
Final performance indicator is made in streaming format and fed into KAFKA. If the differnece of current value and standard devaiation(of last 40 values) is positive then output will be 1 else it would be 0.

csv is converted into json format and then sent to kafka topic. 

Firstly, zookeeper server will be started. As my operating system is Windows so for that I followed the following steps:
1) Copy the file “zoo_sample.cfg” (in C:\zookeeper-3.4.13\conf) and rename it to “zoo.cfg”.
Find & edit dataDir=/tmp/zookeeper to :\zookeeper-3.4.13\data using any text editor like notepad or word. (change the zookeeper version)
Add zookeeper in System Environment Variables.
Add in System Variables ZOOKEEPER_HOME = C:\zookeeper-3.4.13
Edit System Variable named “Path” and append this in the last ;%ZOOKEEPER_HOME%\bin;
Open command prompt and either 
type zkserver 
or
type C:\kafka_2.1.0>.\bin\windows\zookeeper-server-start config\zookeeper.properties

both will start the zookeeper on the defualt port which is 2181. One can change the default port in zoo.cfg file.

then start kafka server for that:
1) Go to config folder in Apache Kafka and edit “server.properties” using any text editor.
Find log.dirs and replace the default path i.e. “=/tmp/kafka-logs” to “=C:\kafka_2.1.0\Kafka-logs” (before this create the folder with name Kafka-logs at the given locaion)(change your version number).
Open command prompt and go to Apache Kafka directory and run following command.

C:\kafka_2.1.0>.\bin\windows\kafka-server-start config\server.properties

Create topic in kafka using command
C:\kafka_2.1.0>.\bin\windows\kafka-topics --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic stream-topic

Send the file as a message to the topic "stream-topic"
C:\kafka_2.1.0>.\bin\windows\kafka-console-producer --bootstrap-server localhost:9092 --topic stream-topic < 
C:\Users\diamond\Downloads\data.json

Now file will be processed using structure streaming




















