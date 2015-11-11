# ITE207-WebCrawler

Project for ITE207: Programming in Java.

Check Documentation directory for abstract

#### Requirements
  - Java 
  - Maven


####  Build Instructions
  1. `$ mvn install`
  2. `$ mvn compile`


#### To run the app
     `$ mvn exec:java -Dexec.args="config.xml"
    
#### Run from jar 
      `java -jar web_crawler/target/web_crawler-1.0-SNAPSHOT-jar-with-dependencies.jar web_crawler/config.xml

`
     
#### config.xml
    - DB Url
    - Number of iterations
    - Seed Urls
    - Thread Pool Configurations
