#!/bin/bash


./target/mesh-alexa-skill-server  -Djavax.net.ssl.trustStore=/opt/jvm/graalvm/jre/lib/security/cacerts -Djavax.net.ssl.trustAnchors=/opt/jvm/graalvm/jre/lib/security/cacerts  -Djava.library.path=/opt/jvm/graalvm/jre/lib/amd64/
