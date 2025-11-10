FROM mcr.microsoft.com/openjdk/jdk:21-ubuntu AS build

WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN chmod -R 777 ./mvnw
RUN ./mvnw install -DskipTests

RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM mcr.microsoft.com/openjdk/jdk:21-ubuntu

VOLUME /tmp

ARG DEPENDENCY=/workspace/app/target/dependency

COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

ENTRYPOINT ["java", "-cp", "app:app/lib/*", "br.com.fiap.Mottracker.MottrackerApplication"]
