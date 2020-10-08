#!/bin/bash

# Run this executable script from /DistributedFileSystemClient base folder.
# i.e Path = ../DistributedFileSystemClient

serverIPAddress=192.168.1.121
input=<AbsolutePath>/DistributedFileSystemClient/inputcommands.txt
output=<AbsolutePath>/DistributedFileSystemClient/output.txt


cd src
javac com/sdsu/*.java
java com.sdsu.Main $serverIPAddress $input | tee $output
