FROM maven AS build

COPY . .

RUN mvn clean clean install -DskipTests

FROM openjdk

LABEL authors="costa"

WORKDIR /api

COPY /src/main/resources/*.xsd ./xsd/

COPY --from=build /target/xmlAPI-0.0.1-SNAPSHOT.jar ./api.jar

EXPOSE 8700

CMD ["java", "-jar", "api.jar"]