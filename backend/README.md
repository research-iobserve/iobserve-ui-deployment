This server is a backend for the iObserve-UI, it provides a RESTful API for retrieving information about deployments and a Websocket server for subscribing to live updates.

# Requirements

This project is written to be compatible with Java 1.8. It uses the [`Jetty`](http://www.eclipse.org/jetty/) http server (with a Websocket implementation).
The api was tested against [`PostgreSQL`](https://www.postgresql.org/) version `9.4.4`.

# Initializing the Database

To use this project with the given test data we have to create a database and a user within postgresql. We assume that you postgresql server is running<br />
First we need an user with rights to access postgresql. For example the postgres user: <br />
`su - postgres ` <br />
We use the following example paramters: <br />
`DATABASE=iobserve-ui` <br />
`DB_USER=iobserve` <br />
`DB_PASSWORD=iobserve-ui` <br />

And initialise the user and the databse as follows: <br />
`createdb $DATABASE` <br />
`createuser -D -A -R -P $DB_USER` <br />
At the promp enter the chosen password.  


# Running the Server

To establish a Postgres database connection the database credentials are set via an environment variable `DATABASE_URL` in the following format:

`postgresql://<username>:<password>@<host>:<port>/<database name>`

This schema is used to avoid credentials in the git repository and to be compatible with [Heroku deployment](https://devcenter.heroku.com/articles/deploy-a-java-web-application-that-launches-with-jetty-runner).

To start the server, run:

`mvn jetty:run`


Please note that the Heroku deployment uses `jetty-runner` which does not provide Websocket support. <br />

If you set up the database as described above you can also use the provided script `run-service.sh`.

# Populate database with test data

Before you can start the service the first time, send a post request to
the backend to create an software system.

`curl -XPOST http://localhost:9090/v1/systems/createTest/CoCoME`


