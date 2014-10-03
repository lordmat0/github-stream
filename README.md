Github-stream
============

This project is a Java web server that connects to GitHub's API that provides a nice front end for seeing commits. It will auto check for new commits every so often (depends on number of branches). You can also scroll down and fetch more commits, when they have been retrieved once they're cached.

It doesn't use a database to hold data however this may change.



Planned features
===============
* Graphs



Settings that need to be changed
===========

project.properties contains settings that can be changed in the project. It can be located at src/main/resources


Currently there are three different settings to be changed
* AUTH_TOKEN  - This is your github auth token
* REPO_NAME   - This is the repository to get commits form
* REPO_OWNER  - This is the owner of the repository 

The AUTH_TOKEN set is a dummy account I have set up. GitHub has a rate_limit of 5000 (how many times it can query in a hour) so it's wise to set up your own AUTH_TOKEN.




Note
=========
This has only been tested on Glassfish 4.1 but I don't see why other servers wouldn't work.

If it helps you learn about Java Web servers let me know :)
