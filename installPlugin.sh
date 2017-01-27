#!/bin/bash
# Arg 1 is the URL of the Jenkins installation
# Arg 2 is the name of the plugin to install
# the Jenkins cli jar is required (comes with the Jenkins WAR)
#

if [ "$#" -ne 2 ]
then
  echo "Usage: ./installPlugin.sh <jenkins URL> <plugin name>"
  exit 1
fi
java -jar jenkins-cli.jar -s $1 install-plugin $2
