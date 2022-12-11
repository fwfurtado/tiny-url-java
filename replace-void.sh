#!/bin/bash

echo "Replacing void with int"
sed -i 's/<Void>/<\?>/g' $1
