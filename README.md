# myGuild

[![Open in Gitpod](https://gitpod.io/button/open-in-gitpod.svg)](https://gitpod.io/#https://github.com/opendoha/finanting/tree/develop)

[![Known Vulnerabilities](https://snyk.io/test/github/opendoha/finanting/badge.svg)](https://snyk.io/test/github/opendoha/finanting) ![](https://github.com/opendoha/myGuild/workflows/Project%20CI/badge.svg)

## Information

A financial accounting application.

The application is developed in Java (Spring Boot) for the backend and React for the frontend.

To manage the full application we used maven.

A docker-file to generate the development environment is available. The different SQL query are available to prepare the database.

Finally, a vagrant file are available to prepare a VM with docker and the database.

## Requirements (development)

* Java = 11
* Maven >= 3.6.3

## Requirements (run)

* MySQL = 8.0.20

## Build

To build used the command : `mvn package`

## Pull requests

When you create a pull request, a series of actions will launch.
To merge the pull request all actions need to passe.

One of the action is a static check of the code. We used PMD (https://pmd.github.io/), ESlint and Prettier.

To check the code :
- frontend : run the command `npm run lint` and `prettier:check`
- server : run the command `mvn pmd:pmd` and check if the file pmd.html on the folder `target/site` exist.

**All remarks must be taken into account**

Finally, a manual validation of the pull request is necessary.
