FROM openjdk:8
ADD target/*.jar /work/app.jar
#远程调试
#ENTRYPOINT ["java","-Dspring.profiles.active=aliyun","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005","-jar","/work/app.jar"]
ENTRYPOINT ["java","-Dspring.profiles.active=local","-jar","/work/app.jar"]