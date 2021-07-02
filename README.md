# Simple-Shopping RESTfull sample project with Spring-Boot

This repository contains source code example of simple-shopping services.

## About Project Behaviour
* At application startup - Hibernate do DDL related jobs for building database schema
* At application startup - Spring-Data-JDBC do DML related jobs for initializing data in database
* After startup api-docs would be available at http://localhost:8080/swagger-ui
* If you prefer to try HTTP services after you ran the project, then as an option you can also import Postman collection for this project from https://www.getpostman.com/collections/794062d5b1af824d8f8f
  * If more information about how to import a Postman collection is needed, please refer to <a href="https://learning.postman.com/docs/getting-started/importing-and-exporting-data/#importing-data-into-postman">Postman Docs</a>

## How to Run
There are two profiles in this project one named h2 and another one is mysql.
Based on your preference you can select one of these profiles:
* If you do not want to prepare a MySQL instance, but you want to run the project as a developer and not for production purposes , then you can simply run it by:
<code>mvn spring-boot:run</code>. This command automatically activate h2 profile which runs a h2 instance in memory as the database. 
* But if you want a more robust running with a prepared MySQL database, then you can call <code>mvn spring-boot:run -Dspring-boot.run.profiles=mysql</code>.   

## Where to Go After Running
First, you should see Swagger documents on <a href="http://localhost:8080/swagger-ui.html">Your Local</a> to find out everything about running APIs. Then you can choose your preferred HTTP client. But we recommend Postman as we prepared its collection as mentioned above. We suppose this Swagger api-docs is intuitive enough to understand and try out APIs.
<br>But the only thing that you must know here is that you need to call the API of user-registration at first for registering your account, and after that you would be able to call many other API's with your credentials.  

## Accessing h2 Console
If you run simply default or h2 profile, then a h2 console will be available at <a href="http://localhost:8080/h2-console">Your Localhost</a> where you can find graphical user interface for viewing and managing database.

## Security
Spring-Security do his job in this project for access management. There are two Spring-security configs in the project which one of them is for development purposes and would be activated on h2 profile. In which some lower security is preferred for allowing users accessing into h2-console.


##Key points for development contributions:
* Latest release version of Spring-Boot is employed at the time of developing this sample project which is 2.5.2
    * You can upgrade SB if there is a newer release version






<br>
Thanks for your attention<br>
Amir Keshavarz<br>
<a href="keshavarzreza.ir">www.KESHAVARZREZA.ir</a>
