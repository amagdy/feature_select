#!/bin/bash
echo "Checking Submodules..."
if [ $(ls -l repos/ | grep -E "^d" | grep -v modules | wc -l) -gt 0 ]
then
  echo "Submodules sources exist."
  if [ $(ls -l repos/modules | grep -E "^d" | wc -l) -gt 0 ]
  then
    echo "Submodules are already built."
  else
    echo "Building all submodules..."
    git submodule foreach ../install_submodules.sh
  fi
else
  echo "Submodules sources do not exist. Pulling them from Server..."
  git submodule update
  . repos/check_submodules_before_build.sh
fi
echo "Success"
exit 0

