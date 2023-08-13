FROM openjdk:11 AS builder

COPY . /tmp
WORKDIR /tmp

RUN ./gradlew build

FROM openjdk:11
COPY --from=builder /tmp/build/libs/*-SNAPSHOT.jar ./app.jar

CMD ["java", "-jar", "app.jar"]
