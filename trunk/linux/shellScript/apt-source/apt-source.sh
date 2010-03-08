#!/bin/sh
##添加软件源及密钥

## wakoopa
sudo sh -c "echo deb http://apt.wakoopa.com all main > /etc/apt/sources.list.d/wakoopa.list"
sudo wget -O - http://apt.wakoopa.com/pubkey.txt | sudo apt-key add -

##virtualbox
sudo sh -c "echo deb http://download.virtualbox.org/virtualbox/debian karmic non-free > /etc/apt/sources.list.d/virtualbox.list"
sudo wget -q http://download.virtualbox.org/virtualbox/debian/sun_vbox.asc -O- | sudo apt-key add -

##opera
sudo sh -c "echo deb http://deb.opera.com/opera/ stable non-free > /etc/apt/sources.list.d/opera.list"
sudo wget -O - http://deb.opera.com/archive.key | sudo apt-key add -

##google
sudo sh -c "echo deb http://dl.google.com/linux/deb/ stable non-free main > /etc/apt/sources.list.d/google.list"
sudo wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add -

##backintime
sudo sh -c "echo deb http://le-web.org/repository stable main > /etc/apt/sources.list.d/backintime-gnome.list"
sudo wget -O - http://le-web.org/repository/le-web.key | sudo apt-key add -

##tor
sudo sh -c "echo deb http://mirror.netcologne.de/torproject.org karmic main > /etc/apt/sources.list.d/tor.list"
#sudo sh -c "echo deb-src http://mirror.noreply.org/pub/tor karmic main >> /etc/apt/sources.list.d/tor.list"
gpg --keyserver keys.gnupg.net --recv 886DDD89
gpg --export A3C4F0F979CAA22CDBA8F512EE8CBC9E886DDD89 | sudo apt-key add -


