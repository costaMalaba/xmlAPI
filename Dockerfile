FROM maven AS build

LABEL authors="costa"

COPY . .

RUN mvn clean package -DskipTests

FROM openjdk

COPY --from=build /target/xmlAPI-0.0.1-SNAPSHOT.jar api.jar

EXPOSE 8700

CMD ["java", "-jar", "api.jar"]