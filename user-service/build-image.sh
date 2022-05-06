#!/bin/bash
mvn package
cd application-runner || exit
mkdir target/dependency
cd target/dependency
jar -xf ../*dependencies.jar
cd ../.. && docker build -t tks/userservice .
