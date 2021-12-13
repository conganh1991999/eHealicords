#! /bin/bash

export DOCKER_BUILDKIT=1;

read -p "Image version:" VERSION

docker build -t nguyenconganh1/ehealicords:$VERSION .
docker push nguyenconganh1/ehealicords:$VERSION
