FROM gradle:7-jdk8 as builder
WORKDIR /app
COPY . .
RUN ./gradlew assemble --stacktrace

FROM openjdk
WORKDIR /app
EXPOSE 8080
COPY --from=builder /app/build/libs/expensesdashboard-0.0.1.jar .
CMD java -jar expensesdashboard-0.0.1.jar