#!/bin/bash

if [ -z "$1" ]
then
    executable="thug-pro-hotkeys.exe"
else
    executable="$1"
fi

mcs -reference:System.Windows.Forms -out:$executable *.cs
