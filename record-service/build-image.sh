#!/bin/bash

cd application-runner || exit
mvn package
mkdir target/dependency
cd target/dependency
jar -xf ../*dependencies.jar
cd ../.. && docker build -t tks/recordservice .
