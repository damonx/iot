FROM dockering/oracle-java8
COPY ./callback.war /fpausercentre/
WORKDIR /fpausercentre/
EXPOSE 8080
CMD ["java", "-server", "-jar", "callback.war"]
