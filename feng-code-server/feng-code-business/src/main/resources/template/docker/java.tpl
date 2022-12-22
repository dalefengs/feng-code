FROM openjdk:11-jdk

COPY java /app/java

RUN mkdir /app/java/out

CMD ["sh", "-c", "javac -Xdiags:verbose -cp /app/java/fastjson-1.2.83.jar -d /app/java/out /app/java/*.java && java -cp /app/java/out:/app/java/fastjson-1.2.83.jar TestSolution"]