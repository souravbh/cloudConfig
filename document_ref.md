
**Angular**
```
ng serve –open  
```

##WSO2
https://docs.wso2.com/display/AM210/apidocs/publisher/#configuring-rest-api   
https://docs.wso2.com/display/AM210/apidocs/publisher/#guide . 

•	Admin console: https://localhost:9443/carbon . 
•	Publisher console: https://localhost:9443/publisher . 
•	API store: https://localhost:9443/store . 
admin/admin

**Jenkins:**
docker run -p 7000:8080 -p 50000:50000 -v jenkins_home:/var/jenkins_home jenkins/jenkins . 
Kermit/kermit . 

**Graphana**
```
Stop graphana 
brew services stop grafana 

start graphana 
brew services start grafana  

hit the service
http://localhost:3000/  
admin/admin 
```
**Issues with one cook pit spanning multiple instances** . 

https://groups.google.com/forum/#!topic/camunda-bpm-users/ZyVh6wj91UE . 
https://groups.google.com/forum/#!topic/camunda-bpm-users/HsUkSouuSmg . 
https://groups.google.com/forum/#!topic/camunda-bpm-users/sVUKW6vIdWc . 

**OPENSHIFT**
```
Push the docker image in web console or from docker hub.   
Expose the port in openshift web console . 
oc expose po/gs-spring-boot-docker-1-szhxh --port=8080 . 
status of current project . 
oc status . 
url to hit:  
http://**{url}**.openshiftapps.com/ . 
```

**DOCKER**
```
->shows the running docker processes 
docker ps 

->shows the images available in local 
docker images 

->login to docker hub . 
docker login --username=**username**  
enter password 

->Push docker images to docker hub  
docker push **username**/ camundaworkflowdocker 

->Build spring boot with docker 
./mvnw install dockerfile:build 

->run a docker image 
docker run -p 9000:8080 **username**/camundaworkflowdocker 

->stop a docker process 
docker stop containerID 
->Push to docker hub from mvn 
./mvnw dockerfile:push 
```
**Install Camunda cycle**
https://docs.camunda.org/get-started/cycle/roundtrip-signavio/ . 
-> start camunda . 
./start-camunda.sh . 
http://localhost:8180/cycle . 
Kermit/Kermit . 

**CAMUNDA APIS.**

http://localhost:8080/rest/engine/default/process-definition/ . 
->Rest api Camunda documentation . 
https://docs.camunda.org/manual/7.7/reference/rest/ . 

http://localhost:8080/rest/engine/default/process-definition/TwitterDemoProcess:1:20/submit-form . 
```javascript
{"variables":
    {"initiator" : {"value" : "kermit", "type": "String"},
     "email" : {"value" : "c@c.com", "type": "String"},
    "content" : {"value" : "amigos", "type": "String"}}
}
```
Get the list of ids assigned to a user
http://localhost:8080/rest/engine/default/task?assignee=demo
http://localhost:8080/rest/engine/default/task/54/resolve
```javascript
{"variables":
    {"approved" : {"value" : true, "type": "boolean"}
}
}
```
http://localhost:8080/rest/engine/default/task/54/claim
```javascript
{"userId":"kermit"
}
```
http://localhost:8080/rest/engine/default/task?ass
http://localhost:8080/rest/engine/default/task/54/complete
```javascript
{"variables":
    {"approved" : {"value" : true, "type": "boolean"}
}
}
```
http://localhost:8080/rest/engine/default/task/29/submit-form
```javascript
{"variables":
    {"approved" : {"value" : true, "type": "boolean"},
                  “comments":{"value":"hello there","type":"String"}
}
```

**ELK Tooling**
Pwd:   /**path**/ELKTools/logstash-5.6.0 . 

**LOGStash** . 
->start logstash . 
bin/logstash -f simple-logstash-filter.conf . 
Sample logstash.conf to listen to tcp logging . 
```
input { 
tcp {
        port => 5000
        codec => json_lines
    } }
filter {
  grok {
    match => { "message" => "%{COMBINEDAPACHELOG}" }
  }
  date {
    match => [ "timestamp" , "dd/MMM/yyyy:HH:mm:ss Z" ]
  }
}
output {
  elasticsearch { 
    hosts => ["localhost:9200"] 
    user => "elastic"
    password => "changeme"
    index => "logstash-%{+YYYY.MM.dd}"
  }
  stdout { codec => rubydebug }
}
```

```xml
->Sample appender from spring-boot using logback to logstash
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <appender name="STASH" class="net.logstash.logback.appender.LogstashTcpSocketAppender">
        <destination>localhost:5000</destination>

        <encoder class="net.logstash.logback.encoder.LoggingEventCompositeJsonEncoder">
            <providers>
                <mdc/> <!-- MDC variables on the Thread will be written as JSON fields--> 
                <context/> <!--Outputs entries from logback's context -->                               
                <version/> <!-- Logstash json format version, the @version field in the output-->
                <logLevel/>
                <loggerName/>

                <pattern>
                 <pattern>
                    {  "appName": "elk-testdata", "appVersion": "1.0" } 
                    </pattern>
                </pattern>

                <threadName/>
                <message/>

                <logstashMarkers/> <!-- Useful so we can add extra information for specific log lines as Markers--> 
                <arguments/> <!--or through StructuredArguments-->

                <stackTrace/>
            </providers>
        </encoder>
    </appender>

    <!-- Our logger writes to file, console and sends the data to Logstash -->
    <logger name="com.example.demo" level="DEBUG" additivity="false">
        <appender-ref ref="STASH"/>
    </logger> 
    
     <logger name="org" level="INFO" additivity="false">
        <appender-ref ref="STASH"/>
    </logger> 
    </configuration> 
 ```

**ELASTIC SEARCH**
```
->start elastic search via docker 
docker run -p 9200:9200 -e "http.host=0.0.0.0" -e "transport.host=127.0.0.1" docker.elastic.co/elasticsearch/elasticsearch:5.6.0

make sure elastic search is running.
http://localhost:9200
```

**KIBANA**
```
->start kibana
bin/kibana
->Hit kibana
http://localhost:5601/
username:elastic
password:changeme
```

**ZooKeeper**
```
->Open zookeeper Cli
zkCli
->Start Zookeeper
zKserver start
open zoo keeper cli
go to /usr/local/Cellar/zookeeper/3.4.10/bin
$>zkCli
$>ls /services
$>stat /producer1 
```

**Hortonworks/Hadoop**

https://hortonworks.com/tutorial/sandbox-deployment-and-install-guide/ . 
https://hortonworks.com/tutorials/    
https://hortonworks.com/tutorial/hadoop-tutorial-getting-started-with-hdp/   
```
Start the docker container [modified a port for conflict in my machine]
docker run -v hadoop:/hadoop --name sandbox-hdp --hostname "sandbox.hortonworks.com" --privileged -d   -p 6080:6080   -p 9090:9090   -p 9000:9000   -p 8000:8000   -p 8020:8020   -p 42111:42111   -p 10500:10500   -p 16030:16030   -p 8042:8042   -p 8040:8040   -p 2100:2100   -p 4200:4200   -p 4040:4040   -p 8050:8050   -p 9996:9996   -p 9995:9995   -p 8080:8080   -p 8088:8088   -p 8886:8886   -p 8889:8889   -p 8443:8443   -p 8744:8744   -p 8888:8888   -p 8188:8188   -p 8983:8983   -p 1000:1000   -p 1100:1100   -p 11000:11000   -p 10001:10001   -p 15000:15000   -p 10000:10000   -p 8993:8993   -p 1988:1988   -p 5007:5007   -p 50070:50070   -p 19888:19888   -p 16010:16010   -p 50112:50111   -p 50075:50075   -p 50095:50095   -p 60000:60000   -p 8090:8090   -p 8091:8091   -p 8005:8005   -p 8086:8086   -p 8082:8082   -p 60080:60080   -p 8765:8765   -p 5011:5011   -p 6001:6001   -p 6003:6003   -p 6008:6008   -p 1220:1220   -p 21000:21000   -p 6188:6188   -p 61888:61888   -p 4557:4557   -p 2222:22   -p 9083:9083   sandbox-hdp /usr/sbin/sshd -D

Start the sandbox
docker exec -t sandbox-hdp /etc/init.d/startup_script start

open ambary dashboard
localhost:8888
localhost:8080 raj_ops/raj_ops

```

