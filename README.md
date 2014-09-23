githubstream
============

This project is a Java web server that connects to GitHub's API that provides a nice front end for seeing commits. It doesn't use a database to hold data but this may change.



Planned features
===============
Ability to Switch branches
Nicer interface
Animation



Settings that need to be Changed
===========

project.properties needs to be configured correctly  the owner of the repository and the repository name


AUTH_TOKEN=5e6d63541d1dbd266955c16fed0053e6d56936af
REPO_NAME=githubstream
REPO_OWNER=lordmat0


It would be best to use your own AUTH_TOKEN as it only has a rate_limit of 5000, this AUTH_TOKEN is on a dummy account.



Note
=========
This has only been tested on Glassfish 4.1
Also although not a super useful program, if it helps you learn about Java Web servers let me know :)
