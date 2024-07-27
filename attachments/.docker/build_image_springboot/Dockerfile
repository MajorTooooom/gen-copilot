FROM openjdk:8
ADD gen-copilot.jar app.jar
EXPOSE 8080
EXPOSE 9999
ENTRYPOINT ["sh","-c","java -jar app.jar $PARAMS"]