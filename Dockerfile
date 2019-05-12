FROM java:openjdk-8-jre-alpine

ADD ./target/mesh-alexa-skill*.jar /server.jar

#"-Djavax.net.ssl.trustStore=/cacerts", "-Djavax.net.ssl.trustAnchors=/cacerts", "-Djava.library.path=/libs/"
CMD ["java", "-jar", "server.jar"]
