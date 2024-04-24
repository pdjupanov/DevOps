
In this project, it was important for me to learn the basics of popular services, to create a stack of servers, create a connection between back-end & front-end.

First, I run every command manually to understand and see how services run and how to configure them before automating them.

PROVISIONING
Services
1. Nginx => Web Service
2. Tomcat => Application Server
3. RabbitMQ => Broker/Queuing Agent
4. Memcache => DB Caching
5. MySQL => SQL Database

The setup was created in the mentioned order below:
MySQL (Database SVC)
Memcache (DB Caching SVC)
RabbitMQ (Broker/Queue SVC)
Tomcat (Application SVC)
Nginx (Web SVC)


I was using GIT Bash for CLI.
For IDE I use Visual Studio Code


Prerequisite:
1. Installing Virtual Box
2. Installing Vagrant
  2.1 Installing plugins for Vagrant

3. Configure the Vargrant file


Vagrant.configure("2") do |config|
  config.hostmanager.enabled = true 
  config.hostmanager.manage_host = true
  
### DB vm  ####
  config.vm.define "db01" do |db01|
    db01.vm.box = "eurolinux-vagrant/centos-stream-9"
    db01.vm.hostname = "db01"
    db01.vm.network "private_network", ip: "192.168.XX.XX"
    db01.vm.provider "virtualbox" do |vb|
     vb.memory = "1248"
   end
      
  
### Memcache vm  #### 
  config.vm.define "mc01" do |mc01|
    mc01.vm.box = "eurolinux-vagrant/centos-stream-9"
    mc01.vm.hostname = "mc01"
    mc01.vm.network "private_network", ip: "192.168.XX.XX"
    mc01.vm.provider "virtualbox" do |vb|
     vb.memory = "1248"
   end
   
  
### RabbitMQ vm  ####
  config.vm.define "rmq01" do |rmq01|
    rmq01.vm.box = "eurolinux-vagrant/centos-stream-9"
  rmq01.vm.hostname = "rmq01"
    rmq01.vm.network "private_network", ip: "192.168.XX.XX"
    rmq01.vm.provider "virtualbox" do |vb|
     vb.memory = "1248" 
  end
  
### tomcat vm ###
   config.vm.define "app01" do |app01|
    app01.vm.box = "eurolinux-vagrant/centos-stream-9"
    app01.vm.hostname = "app01"
    app01.vm.network "private_network", ip: "192.168.XX.XX"
    app01.vm.provision "shell", path: "tomcat.sh"  
    app01.vm.provider "virtualbox" do |vb|
     vb.memory = "800"
   end
   
### Nginx VM ###
  config.vm.define "web01" do |web01|
    web01.vm.box = "ubuntu/jammy64"
    web01.vm.hostname = "web01"
  web01.vm.network "private_network", ip: "192.168.XX.XX"
  web01.vm.network "public_network"
  web01.vm.provider "virtualbox" do |vb|
     vb.gui = true
     vb.memory = "800"
end

end

