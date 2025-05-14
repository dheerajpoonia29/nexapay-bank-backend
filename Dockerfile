FROM openjdk:17-jdk-slim

ENV APP_HOME=/app
ENV DATASOURCE_USERNAME=root
ENV DATASOURCE_PASSWORD=password

WORKDIR $APP_HOME

COPY target/nexapay-bank-backend-0.0.1-SNAPSHOT.jar .
COPY entrypoint.sh .

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "nexapay-bank-backend-0.0.1-SNAPSHOT.jar"]
#ENTRYPOINT ["./entrypoint.sh"]