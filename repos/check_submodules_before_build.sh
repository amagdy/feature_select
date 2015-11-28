#!/bin/bash
REPOS="repos"
MODULES_DIR=$REPOS"/modules"

# create the modules directory if it does not exist
if [ ! -d $MODULES_DIR ]; then mkdir -p $MODULES_DIR ; fi

function submodulesCodeExist {
  OUTPUT=0
  while read SUBMODULE_DIR
  do
    if [ -z "$(ls $SUBMODULE_DIR)" ]; then
      OUTPUT=1
    fi
  done < <(grep path .gitmodules | sed 's/.*= //')
  return $OUTPUT
}

submodulesCodeExist
if [ $? -eq 0 ]
then
  if [ $(ls -l $MODULES_DIR | grep -E "^d" | wc -l) -eq 0 ]
  then
    echo "Building all submodules..."
    git submodule foreach ../install_submodules.sh
  fi
else
  echo "Pulling project submodules..."
  git submodule init
  git submodule sync
  git submodule update
  ./$REPOS/check_submodules_before_build.sh
  echo "Built all submodules successfully."
fi

