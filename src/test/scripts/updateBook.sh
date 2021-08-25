#!/bin/bash

curl -X PUT -H \
"Authorization: Basic dXNlcjpwYXNzd29yZA==" -H \
"Content-Type: application/json" --data \
'{"id": "1000003", "title": "The Catcher in the Rye", "author": "J. D. Salinger", "year": "1953"}' \
http://localhost:8080/api/v1/updatebook
