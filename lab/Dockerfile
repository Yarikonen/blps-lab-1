FROM maven:3.8.4-openjdk-17 as builder
WORKDIR /app
ADD pom.xml /app

RUN ["/usr/local/bin/mvn-entrypoint.sh", "mvn", "verify", "clean", "--fail-never"]


COPY . /app/.
RUN mvn -f /app/pom.xml clean package -Dmaven.test.skip=true

FROM quay.io/wildfly/wildfly
COPY --from=builder /app/target/*.war /opt/jboss/wildfly/standalone/deployments/
ENTRYPOINT /opt/jboss/wildfly/bin/standalone.sh -b=0.0.0.0 -bmanagement=0.0.0.0
