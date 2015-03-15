# MQTT over Websocket with Java Apache ActiveMQ broker. 

	This Proof of Concept aims to demonstrate the feasibility and the state of art, in the open source landscape, 
	of the MQTT protocol over Web Socket using a java broker on the server side.
	MQTT messages payload contain events with a title and a date which will be shown on a calendar. 
	Events are generated both server and client side and sent on the same MQTT topic (called 'events').
	On the server side a scheduled task create events (acting as MQTT message producer) with a random datetime and title (JSON Object) and sent them to the client. 
	A javascript message listener will consume the MQTT message payload (acting as a message consumer) adding it to the calendar widget.
	Furthermore an user can manually create an event with a custom title and datetime using a web form. 
	Once the form is submitted the event is sent to the server (as MQTT message payload), here the MQTT message is simply sent back to client on the MQTT 'events' topic.
	(insert a solution context diagram).
	(insert calendar screenshot).
	
![Alt text](mqtt-screenshot-1.png?raw=true "Optional Title")

##Achievements
* Bi-directional comunication over web socket using MQTT protocol.
* Open source MQTT java broker state of art.
* Java technologies integration and configuration: Spring, ActiveMQ, Spring Integration, SLF4J.

## Ingredient Technologies

### Client Side
* MQTT javascript client (mqtt31.js) over Web Socket.
* Twitter Bootstrap 3.0 for User Interface layout.
* [Adam Shaw full calendar jquery plug-in. 2.0](http://arshaw.com/fullcalendar/)

### Server Side
* Apache ActiveMQ 5.9.1 as MQTT broker.
* Spring Core 4.0 (Task scheduling).
* Spring Integration 4.0 as abstraction layer for MQTT topics and protocol adapting.  Spring Integration uses internally the eclipse PAHO MQTT library.
* Logback logging framework 1.0.13 (SLF4J 3.7.5)
* Apache Tomcat 8 as Web Container.

## Configuration and start-up
Please ensure to have apache maven installed. You can use a machine package manager depending on your OS:

 * macosx - ```brew install maven```
 * ubuntu - ```apt-get install maven```
 * centos - ```yum install maven```
 * windows - ```choco install maven``` (needs chocolatey pre-installed. https://chocolatey.org/ )
 
 Once Maven is properly installed run : 
 ```
 mvn clean tomcat7:run
 ```
	
## Well known bugs
    When entering a custom event , the time is not properly handled in the calendar.

### Deploy in the cloud.
	
	*Openshift - TODO