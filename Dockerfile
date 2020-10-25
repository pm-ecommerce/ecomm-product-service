FROM openjdk

WORKDIR /app

MAINTAINER pm@miu.edu

COPY . .

RUN chmod -R 0777 .

RUN ./mvnw clean install -DskipTests -e

RUN ls ./target

RUN cp ./target/*.jar ./service.jar

ENTRYPOINT ["java","-jar","./service.jar"]