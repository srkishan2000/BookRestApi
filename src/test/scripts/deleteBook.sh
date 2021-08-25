#!/bin/bash

id=1000003

if [ -z "$id" ]
  then
    echo "Provide the id of the book to be deleted"
    exit 1
fi

curl -X DELETE -H "Authorization: Basic dXNlcjpwYXNzd29yZA==" \
http://localhost:8080/api/v1/deletebook/$id
