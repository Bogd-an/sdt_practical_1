#!/bin/sh
# run with argument path file json body request
echo "___Json sen:___"
cat $*
echo "\n___Json receive:___"
curl -sb -X POST http://localhost:8080/response -d @$* | python3 -m json.tool
