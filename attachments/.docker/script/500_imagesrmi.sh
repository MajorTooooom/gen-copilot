#!/bin/bash

docker rmi $(docker images --filter "reference=com/dororo/future/gencopilot/*:*" -q)
docker images