#!/bin/sh

echo "Waiting for MySQL to be ready..."

until mysql -h127.0.0.1 -u$DATASOURCE_USERNAME -p$DATASOURCE_PASSWORD -e "SELECT 1" > /dev/null 2>&1; do
  echo "MySQL not ready, waiting..."
  sleep 2
done

echo "MySQL is ready. Starting Spring Boot app..."
exec java -jar nexapay-bank-backend-0.0.1-SNAPSHOT.jar