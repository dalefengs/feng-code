FROM openjdk:8-jdk-alpine

COPY ../code/java /app/java

RUN apk add --no-cache javac

RUN javac -cp /app/java/lib/fastjson-1.2.83.jar -d /app/java/out /app/java/src/*.java

CMD ["java", "-cp", "/app/java/out:/app/java/lib/fastjson-1.2.83.jar", "TestSolution"]
