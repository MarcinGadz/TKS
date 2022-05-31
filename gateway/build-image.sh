#!/bin/bash

mvn package -DskipTests=true
mkdir target/dependency
cd target/dependency
jar -xf ../*dependencies.jar
cd ../.. && docker build -t tks/gateway .
