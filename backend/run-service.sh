#!/bin/bash

export DATABASE_URL="postgresql://iobserve:iobserve-ui@localhost:5432/iobserve-ui"

if mvn compile ; then
	mvn jetty:run
else
	echo "Fail"
fi

# end
