FROM java:8-alpine
MAINTAINER Your Name <you@example.com>

ADD target/uberjar/testapi.jar /testapi/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/testapi/app.jar"]
