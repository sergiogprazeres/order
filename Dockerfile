FROM openjdk:17
LABEL authors="s.guerra.prazeres"
COPY target/order.jar order.jar
ENTRYPOINT ["java", "-jar", "/order.jar"]