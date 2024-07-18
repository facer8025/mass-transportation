FROM openjdk:21
ADD target/massive-transport-api-0.0.1-SNAPSHOT.jar mass-trasportation.jar
ENTRYPOINT [ "java", "-jar", "mass-trasportation.jar" ]