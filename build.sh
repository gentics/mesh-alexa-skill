#!/bin/bash

if [ -z "$GRAALVM_HOME" ] ; then
  echo "Please set GRAALVM_HOME to point to your graalvm installation"
  exit
fi

PROJECT_DIR="`dirname \"$0\"`"

cd $PROJECT_DIR

if [ "$1" == "native" ] ; then
  ./mvnw clean package -DskipTests -Pnative
  docker build -f Dockerfile.native -t gentics/mesh-alexa-skill-demo:native .
else
  ./mvnw clean package -DskipTests -Pshaded
  docker build -t gentics/mesh-alexa-skill-demo:latest .
fi
