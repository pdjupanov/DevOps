#!/bin/bash

sudo yum install wget unzip httpd -y > /dev/null

sudo systemctl start httpd
systemctl enable httpd

mkdir -p /tmp/websites
cd /tmp/websites

wget https://www.tooplate.com/zip-templates/2128_tween_agency.zip > /dev/null
unzip 2128_tween_agency.zip > /dev/null
sudo cp -r 2128_tween_agency/* /var/www/html/

systemctl restart httpd

rm -rf /tmp/websites

sudo systemctl status httpd
ls /var/www/html
