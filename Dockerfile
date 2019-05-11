FROM oracle/graalvm-ce:19.0.0 as build-env
ADD . /
RUN ./mvnw clean package -DskipTests

FROM gcr.io/distroless/base
COPY --from=build-env /target/mesh-alexa-skill-server /mesh-alexa-skill-server
COPY --from=build-env /lib/x86_64-linux-gnu/libz.so.1 /lib/x86_64-linux-gnu/libz.so.1
CMD ["/mesh-alexa-skill-server"]

