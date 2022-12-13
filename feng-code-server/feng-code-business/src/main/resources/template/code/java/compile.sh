javac -cp lib/fastjson-1.2.83.jar -d ./out ./src/*.java
# Windown 编译使用 ; 分号分隔, Linux 使用 : 分隔
java -cp "./out:lib/fastjson-1.2.83.jar" TestSolution


