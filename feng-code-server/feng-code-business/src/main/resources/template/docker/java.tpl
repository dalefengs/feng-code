FROM openjdk:8-jdk-alpine

COPY java /app/java

RUN mkdir /app/java/out

RUN javac -cp /app/java/fastjson-1.2.83.jar -d /app/java/out /app/java/*.java

CMD ["java", "-cp", "/app/java/out:/app/java/fastjson-1.2.83.jar", "TestSolution"]
