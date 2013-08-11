SU2013 - Mobile Computing - Team 1
==================================

      ______
     |/     |
     |      O  
     |    --|--
     |      |
     |     / \
    _|_
    

Authors
-------

Aiswrya Bhaskaran

Gaëtan Chevalley

Aquila Khanam

Kevin Jaquier

Application
-----------

Hangman mobile app, including a RESTful Java web server and an Android client.

Notes
-----

* The Web server is using the Servlet technology. 
* The Android client is target the versions 11 to 17 of the Android SDK. It's been tested using an Android 4.1.2 system.

How to run
----------

### Server

A running server is available at http://cs13.cs.sjsu.edu:8080/team1.

### Android client

* Make sure the server 
* Open the Android project in `android/Hangman` with the Eclipse IDE provided with the Android Development Toolkit.
* Run the project.

How to update
-------------

### Server

* Go in your server directory with a command prompt. (Via git, it's called team1)
* Update the class files in `./WEB-INF/classes`.
* Run `jar cvfM team1.war WEB-INF`, it will build a new war with the updated classes.
* Upload it online with `scp -P 62222 team1.war team1@cs13.cs.sjsu.edu:/opt/domains/domain1/autodeploy`
* Verify your identity with the received password and it's done.
* You can now reach the server updated at http://cs13.cs.sjsu.edu:8080/team1 .
