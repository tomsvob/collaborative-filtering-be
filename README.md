## Collaborative filtering BE

Backend application for collaborative filtering project. 

User can search and rate films. 
App recommends new films to users based on how they rated other films.
App uses Spearman's ranking algorithm to find similar users and recommend best rated films from these users, that person hasn't ranked yet. 

### Prerequisites

Java JDK 8 and up,
Maven,
Postgresql 9 and up

### Build and run

1. edit db connection in db.properties
2. compile sources with maven (pom.xml) ```mvn clean install```
3. run with ```java -jar target/cfbe-version-label.jar```

### Notes
This application is not meant to be production app but rather technological showcase.

Admin user is predefined with id: 123456789 