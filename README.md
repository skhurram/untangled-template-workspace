# Template

This is a Full Stack template with specs, dev cards, and client code.
It contains a mock login/signup screen, top-level tab routing (once logged in), etc.

You must run the server (and use it through the server) for login to work, but ANY username/password are accepted. The
server always approves login.

It is set up to be deployable to Heroku (or anywhere) as a standalone jar.

## Setting up Run Configurations (IntelliJ)

Add a figwheel config:

<img src="docs/img/figwheel.png">

Add a server config:

<img src="docs/img/server.png">

Then run both from IntelliJ.

## Using from other editors

Start a REPL:

```
lein repl
```

and use the `start-figwheel` or `go` functions to start figwheel or the server.

## Using the server

In the server REPL, start the server with:

```
(go)
```

To reload the server code:

```
(reset)
```

IF your compile fails, Recompile after failed compile:

```
(refresh)
(go)
```

If you cannot find `refresh`, try:

```
(tools-ns/refresh)
```

## Full Stack

```
http://localhost:3000/index-dev.html
```

## Dev Cards

```
http://localhost:3449/cards.html
```

## Specs

```
http://localhost:3449/test.html
```

## Continuous Integration Tests

The project is set up to be able to run both the UI and Server tests from a
standard *NIX command-line (untested with Windows, but works with OSX and
Linux). 

The UI tests use node, karma, and doo to accomplish tests.

The Makefile has targets for running the various CI tests modes. You
must install Node and NPM. In OSX, Home Brew can make quick work of that.

## Makefile

There is a GNU `Makefile` in the project that can start various command
line interactions. This file is commented so you can see what targets
are valid.

Example: Run a REPL that is ready to run the Untangled Server:

```
make server
```

# Deploying

Build the standalone Jar with:

```
lein uberjar
```

will build `target/template.jar`. 

The production `prod.edn` file (in src/config) grabs the web PORT from
the environment (as required by Heroku). So, this jar can be run with:

```
export PORT=8080   # the web server port to use
java -Dconfig=config/prod.edn -jar template.jar
```

The `Procfile` gives the correct information to heroku, so if you've 
configured the app (see Heroku docs) you should be able to deploy with
git:

```
git push heroku master
```
