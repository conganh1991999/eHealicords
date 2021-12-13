#! /bin/sh

keytool -genkeypair -alias ehealicords -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore ehealicords.p12 -validity 3650
mv ehealicords.p12 ./src/main/resources/
