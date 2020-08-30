FROM openjdk:8-jdk-alpine
EXPOSE 8080
ADD target/eCommerce.jar eCommerce.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=production" ,"-jar" , "eCommerce.jar"]