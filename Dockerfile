FROM store/oracle/jdk:11

RUN yum install -y nc

WORKDIR /app

MAINTAINER pm@miu.edu

COPY ./.mvn ./.mvn
COPY ./src ./src
COPY ./mvnw ./mvnw
COPY ./mvnw.cmd ./mvnw.cmd
COPY ./pom.xml ./pom.xml

RUN chmod -R 0777 .

RUN mkdir -p ~/.m2/repository/com/pm/ecommerce/entities/1.0-SNAPSHOT

RUN cp ./src/main/resources/pm-entities.jar ~/.m2/repository/com/pm/ecommerce/entities/1.0-SNAPSHOT/entities-1.0-SNAPSHOT.jar

RUN ./mvnw install -DskipTests

RUN cp ./target/*.jar ./service.jar

ENTRYPOINT ["java","-jar","./service.jar"]