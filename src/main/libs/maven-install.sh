#!/bin/sh

mvn install:install-file -Dfile=babbler-0.7.0.jar -DgroupId=rocks.xmpp -DartifactId=blabber -Dversion=0.7.0 -Dpackaging=jar
