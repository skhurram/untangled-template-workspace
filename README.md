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
(stop) ; this will fail (which is ok) if you'd had a failing refresh, but see WARNING below
(refresh)
(go)
```

WARNING: If you `(refresh)` without stopping the server you'll lose the var holding your server, and won't be able
to stop the server (which leaves the port in use). That will cause you to need to restart your REPL.

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
