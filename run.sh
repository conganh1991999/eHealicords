#! /bin/bash

read -s -p "MySQL Password: " PASSWORD

read -s "Image version:" VERSION

docker pull nguyenconganh1/ehealicords:$VERSION

docker run --name ehealicords -p 80:8080 \
  -e SPRING_DATASOURCE_URL="jdbc:mysql://localhost:3306/ehealicords_v2?useUnicode=yes&characterEncoding=UTF-8&useSSL=false&&allowPublicKeyRetrieval=true" \
  -e SPRING_DATASOURCE_PASSWORD="$PASSWORD" \
   nguyenconganh1/ehealicords:$VERSION