SportLeagueSystem
========================

What is it?
-----------

System pre vedenie sportovej ligy. Webova aplikacia sluzi pre jednotlivcov, ktory hladaju spoluhracov pre (tenis, squash, ping pong...). Po odohrani zapasov mozu zadavat vysledky zapasov. Tie sa vyhodnocuju a v tabulke mozno vidiet aktualne poradie hracov. System bude pridelovat hracom ich superov, s ktorymi sa potom oni dohodnu na case a mieste konania hry. Sezona moze trvat tyzden/mesiac a hraci dostavaju kazdy den/tyzden noveho supera s ktorym maju odohrat zapas. Definovane su 3 role: hraci, spravca pre jednotlive sporty (riadi v danej sportovej lige...) a admin (uprava nastaveni systemu, pridanie novej ligy a vsetko ostatne).


About techologies
----------------- 

This is a compliant Java EE 6 application using JSF 2.0, CDI 1.0, EJB 3.1, JPA 2.0, Bean Validation 1.0, log4j, RESTEasy and Picketbox JAAS.


System requirements
-------------------

- JDK 1.6 or higher
- Maven 3.0 or higher
- JBoss AS 7.1.1
- MySQL Community Server 


Setting up the database
-----------------------

1. Install MySQL Community Server, set username/password as Admin/Admin
2. Install the MySQL connector
	- Download the MySQL Driver and module.xml from <http://www.edisk.cz/stahni/04703/driver.zip_829.07KB.html>
	- Extract the archive and copy it into %JBOSS_HOME%\modules\com\mysql\main The file structure should be as follows:	
		
			%JBOSS_HOME%\modules\com\mysql\main\module.xml
			%JBOSS_HOME%\modules\com\mysql\main\mysql-connector-java-5.1.25-bin.jar

3. Add a MySQL driver entry to standalone-ha.xml
	- Add the following to the drivers list (between the <drivers> and </drivers> tag):
			
			<driver name="mysql" module="com.mysql">
				<xa-datasource-class>com.mysql.jdbc.Driver</xa-datasource-class>
			</driver>


Setting up the security domain
------------------------------

Add the following security domain to standalone-ha.xml:

	<security-domain name="sport" cache-type="default">
	       <authentication>
			<login-module code="Remoting" flag="optional">
				<module-option name="password-stacking" value="useFirstPass"/>
			</login-module>                        
			<login-module code="Database" flag="required">
				<module-option name="dsJndiName" value="java:jboss/datasources/SportLeagueSystemDS"/>
				<module-option name="principalsQuery" value="SELECT password FROM Principal WHERE loginname = ?"/>
				<module-option name="rolesQuery" value="SELECT role, 'Roles' FROM Principal WHERE loginname = ?"/>
				<module-option name="password-stacking" value="useFirstPass"/>
				<module-option name="hashAlgorithm" value="MD5"/>
				<module-option name="hashEncoding" value="hex"/>
			</login-module>
		</authentication>
	</security-domain>
		
		
Deploying the application
-------------------------

_NOTE: The following build command assumes you have configured your Maven user settings._

1. **Start the JBoss AS**

	Run the following commands:
		
		For Linux:   JBOSS_HOME/bin/standalone.sh
		For Windows: JBOSS_HOME\bin\standalone.bat

2. **Build and deploy the project**
	
	Type this command into the command line to build and deploy the archive:

		mvn clean package jboss-as:deploy

	This will deploy target/SportLeagueSystem.war to the running instance of the server.

3. **Access the application**
	
	The Applicattion can be accessed at the following URL: <http://localhost:8080/SportLeagueSystem/>

4. **Undeploy the application**
	
	To undeploy, simply type this command into the command line:

		mvn jboss-as:undeploy	

5. **Run Arquillian tests**
	
	To run the tests, type the following command:

		mvn clean test -Parq-jbossas-remote


RESTful Web Services
--------------------

As well as web interface, authorized users can use REST to browse sports, leagues, users and matches.
Responses from REST Services can be displayed in either XML or JSON format by adding ".json" or ".xml"
at the end of url (before query string). Default content type is XML because it is more user-friendly.
Services are registered at respective urls (square brackets delimit optional parameters):

	http://localhost:8080/SportLeagueSystem/rest/sports[?name=<sport_name>]
	http://localhost:8080/SportLeagueSystem/rest/sports/<sport_id>
	
	For administrators only:
	http://localhost:8080/SportLeagueSystem/rest/users[?name=<user_name>]
	http://localhost:8080/SportLeagueSystem/rest/users/<user_id>
	
	http://localhost:8080/SportLeagueSystem/rest/leagues[?sport=<sport_name>]
	http://localhost:8080/SportLeagueSystem/rest/leagues/<league_id>
	http://localhost:8080/SportLeagueSystem/rest/leagues/<league_id>/players
	http://localhost:8080/SportLeagueSystem/rest/leagues/<league_id>/matches
	http://localhost:8080/SportLeagueSystem/rest/leagues/<league_id>/evaluate
	
	For league supervisors only:
	http://localhost:8080/SportLeagueSystem/rest/leagues/<league_id>/matches/generate
	
	http://localhost:8080/SportLeagueSystem/rest/leagues/matches
	http://localhost:8080/SportLeagueSystem/rest/leagues/matches/<match_id>


Running the application on two nodes
------------------------------------

To run the application on two different nodes, complete the following steps:

1. Make two physical copies of the JBoss AS
2. Copy the .war archive into the JBOSS_HOME/standalone/deployment folder of both servers
3. Complete the environmental setup for both servers
4. Run the servers with the following arguments:
		
		-c standalone-ha.xml
		-c standalone-ha.xml -Djboss.node.name=Node2 -Djboss.socket.binding.port-offset=100

		
OpenShift
---------

Application is deployed on Openshift with URL: <http://sportleague-jbosstle.rhcloud.com/>

