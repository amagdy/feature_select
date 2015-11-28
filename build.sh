#!/bin/bash
./repos/check_submodules_before_build.sh
echo "============================ Building ===================================="
mvn clean test

