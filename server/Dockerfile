# Use a lightweight version of the OpenJDK image
FROM openjdk:17

# Copy the fat JAR from the build folder to the container
COPY /build/libs/server-all.jar server.jar

# Command to run the JAR file
CMD ["java", "-cp", "/server.jar", "com.matiz22.richgophishclient.ApplicationKt"]

# Expose application port, replace 8080 with your actual port if different
EXPOSE 80
