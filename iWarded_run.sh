#!/usr/bin/env bash

pushd $(dirname "${0}") > /dev/null
DIR=$(pwd -L)
java -Xmx4096m -jar target/iWarded-1.0.0.jar $*
popd > /dev/null
