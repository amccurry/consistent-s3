#!/bin/bash
VERSION="${1}"
cd ./release/
git checkout repository
git pull
mvn install:install-file \
 -DgroupId=consistent-s3 \
 -DartifactId=consistent-s3 \
 -Dversion=${VERSION} \
 -Dfile=../target/consistent-s3-$VERSION.jar \
 -Dpackaging=jar \
 -DgeneratePom=true \
 -DlocalRepositoryPath=. \
 -DcreateChecksum=true
