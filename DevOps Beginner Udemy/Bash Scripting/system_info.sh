#!/bin/bash

echo "This script prints system info"
echo

#system uptime
echo "*********************************"
echo "The uptime of the system is: "
uptime
echo

#memory utilization
echo "*********************************"
echo "Memory Utilization: "
free -m
echo

#disk utilization
echo "*********************************"
echo "Disk Utilization: "
df -h

# after that we need to make the file executable with chmod +x [script name]
