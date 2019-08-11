#!/bin/bash
set -ex
VERSION="${1}"
DIR=$(pwd)
TMPDIR="/tmp/$(uuidgen)"
mkdir $TMPDIR
cd $TMPDIR

git clone git@github.com:amccurry/consistent-s3.git
cd $TMPDIR/consistent-s3
git checkout repository
git pull
mvn install:install-file \
 -DgroupId=consistent-s3 \
 -DartifactId=consistent-s3 \
 -Dversion=${VERSION} \
 -Dfile=${DIR}/target/consistent-s3-${VERSION}.jar \
 -Dpackaging=jar \
 -DgeneratePom=true \
 -DlocalRepositoryPath=. \
 -DcreateChecksum=true
git add consistent-s3
git commit -a -m "Repo release."
git push
