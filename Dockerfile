FROM 3.6.3-openjdk-14-slim as builder

WORKDIR /app

COPY pom.xml /app/pom.xml

RUN mvn dependency:go-offline -B

COPY . /app

RUN mvn clean install -DskipTests

FROM 3.6.3-openjdk-14-slim as runner

COPY --from=builder /app/target/cardgame-0.0.1-SNAPSHOT.jar /app/

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/cardgame-0.0.1-SNAPSHOT.jar"]
