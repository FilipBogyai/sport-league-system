SportLeagueSystem
========================

What is it?
-----------

System pre vedenie sportovej ligy. Webova aplikacia sluzi pre jednotlivcov, ktory hladaju spoluhracov pre (tenis, squash, ping pong..). Po odohrani zapasov mozu zadavat vysledky zapasov. Tie sa vyhodnocuju a v tabulke mozno vidiet aktualne poradie hracov. System bude pridelovat hracom ich superov, s ktorymi sa potom oni dohodnu na case a mieste konania hry. Sezona moze trvat tyzden/mesiac a hraci dostavaju kazdy den/tyzden noveho supera s ktorym maju odohrat zapas. Definovane su 3 role: hraci, spravca pre jednotlive sporty (riadi v danej sportovej lige..) a admin (uprava nastaveni systemu, pridanie novej ligy a vsetko ostatne).


About techologies
----------------- 

This is a compliant Java EE 6 application using JSF 2.0, CDI 1.0, EJB 3.1, JPA 2.0, Bean Validation 1.0, log4j and Picketbox JAAS.


System requirements
-------------------

All you need to build this project is Java 6.0 (Java SDK 1.6) or better, Maven 3.0 or better.

The application this project produces is designed to be run on JBoss Enterprise Application Platform 6 or JBoss AS 7.1. 


Configure Maven
---------------

If you have not yet done so, you must Configure Maven in order to test the application.


Start JBoss Enterprise Application Platform 6 or JBoss AS 7.1
-------------------------

1. Open a command line and navigate to the root of the JBoss server directory.
2. The following shows the command line to start the server with the web profile:

        For Linux:   JBOSS_HOME/bin/standalone.sh
        For Windows: JBOSS_HOME\bin\standalone.bat


Build and Deploy the project
-------------------------

_NOTE: The following build command assumes you have configured your Maven user settings. If you have not, you must include Maven setting arguments on the command line. See [Build and Deploy the Quickstarts](../README.html/#buildanddeploy) for complete instructions and additional options._

1. Make sure you have started the JBoss Server as described above.
2. Open a command line and navigate to the root directory of this quickstart.
3. Type this command to build and deploy the archive:

        mvn clean package jboss-as:deploy

4. This will deploy `target/SportLeagueSystem.war` to the running instance of the server.


Access the application 
---------------------
 
The application will be running at the following URL: <http://localhost:8080/SportLeagueSystem/>.


Undeploy the Archive
--------------------

1. Make sure you have started the JBoss Server as described above.
2. Open a command line and navigate to the root directory of this quickstart.
3. When you are finished testing, type this command to undeploy the archive:

        mvn jboss-as:undeploy


Run the Arquillian tests
----------------------------

This project provides Arquillian tests. By default, these tests are configured to be skipped as Arquillian tests require the use of a container. 

_NOTE: The following commands assume you have configured your Maven user settings. If you have not, you must include Maven setting arguments on the command line. See [Run the Arquillian Tests](../README.html/#arquilliantests) for complete instructions and additional options._

1. Make sure you have started the JBoss Server as described above.
2. Open a command line and navigate to the root directory of this quickstart.
3. Type the following command to run the test goal with the following profile activated:

        mvn clean test -Parq-jbossas-remote 

