#!/bin/bash
function getRealPath(){
  DIR=$1
  echo "$(cd "$DIR"; pwd)"
}

function getPomXML(){
  FILE=$1
  cat $FILE | awk -v ORS='' '{print $0;}'
}

function getTagContents() {
  XML=$1
  TAG=$2
  CONTENT=$XML
  while read TAGPART
  do
    if [ -n "$TAGPART" ]
    then
      CONTENT=$(echo "$CONTENT" | grep -o -E "<$TAGPART[^>]*>.*</$TAGPART>" | awk -v FS="</$TAGPART>" '{print $1;}' | sed 's/^<'$TAGPART'[^>]*>//g')
    fi
  done < <( echo $TAG | awk -v RS='/' '{print $0;}' )
  echo $CONTENT
}

function runMvnDeploy(){
  SUBMODULE_DIR=$1
  GROUP_ID=$2
  ARTIFACT_ID=$3
  VERSION=$4
  PACKAGING=jar
  
  PARENT_DIR=$(dirname $SUBMODULE_DIR)
  INSTALLED_MODULES_DIR=$PARENT_DIR"/modules"
  mkdir -p $INSTALLED_MODULES_DIR
  CURRENT_DIR=$(pwd)
  CACHE_DIR_SUFFIX=$(echo $GROUP_ID | sed 's/\./\//g')"/"$ARTIFACT_ID
  
  # delete caches
  rm -rf ~/.m2/repository/$CACHE_DIR_SUFFIX
  rm -rf $INSTALLED_MODULES_DIR"/"$CACHE_DIR_SUFFIX
  
  cd $SUBMODULE_DIR
  echo "======================= Buiding $GROUP_ID.$ARTIFACT_ID ================="
  mvn clean package
  
  mvn deploy:deploy-file -Durl=file://$INSTALLED_MODULES_DIR -Dfile=target/$ARTIFACT_ID-$VERSION.$PACKAGING -DgroupId=$GROUP_ID -DartifactId=$ARTIFACT_ID -Dpackaging=$PACKAGING -Dversion=$VERSION
  echo "========================================================================"
  cd $CURRENT_DIR
}


SUBMODULE_DIR=$(getRealPath $1)
if [ -d "$SUBMODULE_DIR" ] && [ -e "$SUBMODULE_DIR/.git" ]; then
  GROUP_ID=$(getTagContents "$(getPomXML "$SUBMODULE_DIR/pom.xml")" "/project/groupId")
  ARTIFACT_ID=$(getTagContents "$(getPomXML "$SUBMODULE_DIR/pom.xml")" "/project/artifactId")
  VERSION=$(getTagContents "$(getPomXML "$SUBMODULE_DIR/pom.xml")" "/project/version")
  
  runMvnDeploy "$SUBMODULE_DIR" "$GROUP_ID" "$ARTIFACT_ID" "$VERSION"
else
  echo "$SUBMODULE_DIR does not seem like a valid submodule repository"
fi

exit 0
