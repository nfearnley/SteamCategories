===============
SteamCategories
===============

Description
===========

This is a java program that makes it easier to put steam apps into categories. It loads the list of apps you own from your steam profile on the steamcommunity website, and accesses your categories from the config file in your steam userdata directory. 

Instructions
============

1.  Download the latest binary from github (https://github.com/nfearnley/SteamCategories/downloads)

2.  Unzip the binary into a folder

3.  Run the SteamCategories.jar file

4.  Click File -> Open

5.  Enter the SteamID used in the url of your steam community profile

6.  Select your localconfig.vdf file

    The app will load:

    - a list of apps you own from your steam profile

    - the category settings for those apps from your localconfig.vdf file

7.  Select the apps you wish to set the category of

    - Use SHIFT to select a range of apps

    - Use CTRL to add a apps to the current selection

    - Use CTRL to add a range of apps to the current selection

8.  Choose what you want to do:

    - Remove the categories from these apps

    - Add the apps to a new category

    - Add the apps to an existing selected category

9.  When you are done editing your categories, click File -> Save

10. Click File -> Quit

11. To refresh your steam library, restart steam

File Paths
==========

Steam categories configuration file:

 [steampath]\userdata\[id]\config\localconfig.vdf

 where:

  [steampath] is your steam directory

  [id] is a an id number

Steam profile apps list:

 http://steamcommunity.com/id/[steamid]/games?xml=1

 where:

  [steamid] is the steam id used to access your profile on the steam community website


Libraries
=========

Internal
--------

Uses internal vdfNode library to open vdf configuration files

Uses internal SteamCategories library to open localconfig.vdf files, and load owned apps from steam community website

External
--------

Uses apache commons library

Warning
=======

I have not personally run into any issues of corrupt data, but I recommended backing up your localconfig.vdf file beforehand, just to be safe.