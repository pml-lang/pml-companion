#!/bin/bash          

# to run this file:
# start WSL (type 'wsl' in Windows search)
# type:
# cd ~
# /mnt/c/aa/work/PML/Converter/work/jpackage_distro_creator/Linux/create_Linux_package.sh

# NAME="PML to HTML Converter"  # ok, but more error-prone in Linux
NAME="PML_to_HTML_Converter"
VERSION=1.2.0
COPYRIGHT="Christian Neumanns"
DESCRIPTION="Practical Markup Language (PML) to HTML Converter"
# deb and rpm are allowed
PACKAGE_TYPE=rpm

PML_DIR=/mnt/c/aa/work/PML
PMLC_DIR=$PML_DIR/Converter

APP_IMAGE_PARENT_DIR=temp/app_image
APP_IMAGE_DIR=$APP_IMAGE_PARENT_DIR/$NAME
# echo $APP_IMAGE_DIR

if [ -d "$APP_IMAGE_DIR" ]; then
    rm -rf "$APP_IMAGE_DIR"
fi

# echo $PMLC_DIR/build/lib

# Create application image
jpackage \
    --name "$NAME" \
    --module-path "$PMLC_DIR/build/lib:/mnt/c/aa/programs/javafx-jmods-15.0.1" \
    --module dev.pml.converter/dev.pml.converter.se_start \
    --type app-image \
    --dest "$APP_IMAGE_PARENT_DIR" \
    --app-version "$VERSION" \
    --copyright "$COPYRIGHT" \
    --description "$DESCRIPTION" \
    --icon "$PML_DIR/logo/PML_plane_alone_96_96.png" \
    --add-launcher "pmlc=$PMLC_DIR/work/jpackage_distro_creator/Linux/pmlc_Linux.properties"
#    --verbose
#    --temp ttt
#    --resource-dir work/resources \
#    --input ... is copied to app/

# read -p "Press Enter to continue... "

# Copy resource files
cp -R $PMLC_DIR/work/resources/* "$APP_IMAGE_DIR/lib/runtime"
cp -R "$PMLC_DIR/build/plib" "$APP_IMAGE_DIR/lib/runtime/plib"

# cp "$PML_DIR/logo/PML_plane_alone_big.png" "$APP_IMAGE_DIR/lib/$NAME.png"
# cp "$PML_DIR/logo/PML_plane_alone_96_96.png" "$APP_IMAGE_DIR/lib/pmlc.png"

# rm -r ./temp/distribution/*
rm -r temp/distribution/*

# Create distribution
jpackage \
    --name "$NAME" \
    --app-image "$APP_IMAGE_DIR" \
    --type "$PACKAGE_TYPE" \
    --dest temp/distribution \
    --app-version "$VERSION" \
    --copyright "$COPYRIGHT" \
    --license-file "$PMLC_DIR/work/resources/LICENSE.txt" \
    --description "$DESCRIPTION" \
    --linux-shortcut \
    --linux-menu-group "Practical Markup Language (PML)" \

# produces error when app is run in WSL (because no GUI)
#    --linux-menu-group PML \
#    --linux-shortcut \

#    --verbose
#    --temp ttt

cp -R temp/distribution/* "$PMLC_DIR/jpackage_distro/Linux"
cp -R temp/distribution/* /mnt/c/aa/work/tools/shared_VM_NOBMI/PML

echo -
echo Will copy to local website dir.
read -p "Press Enter to continue... "
cp $PMLC_DIR/jpackage_distro/Linux/* "$PML_DIR/website/public/downloads/linux"

# doesn't work for Q:
# echo -
# echo Will copy to public website, Q: must be open
# read -p "Press Enter to continue... "
# cp $PMLC_DIR/distribution/Linux/* /mnt/q/public_html/practical-programming_org/pml/downloads/linux

echo -
# echo $PMLC_DIR/distribution/Linux/* must be copied manually to public website directory Q:/public_html/practical-programming_org/pml/downloads/linux
echo Note: jpackage_distro/Linux/ must be copied manually to public website directory in drive Q
read -p "Press Enter to continue... "

echo -
echo To install the package type a command similar to
echo sudo apt install ~/temp/distribution/pml-to-html-converter_1.2.0-1_amd64.deb
echo sudo apt install /mnt/c/aa/work/tools/shared_VM_NOBMI/PML/pml-to-html-converter_1.2.0-1_amd64.deb

echo -
echo To try out, create example.pml, and type
echo "/opt/$NAME/bin/pmlc" example.pml

echo -
echo To remove the package:
echo sudo apt-get remove "$NAME"
