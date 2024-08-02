# Klinika
Pet prod

Add keystore

//klinika
keytool -genkeypair -alias klinika -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore klinika.p12 -validity 365
keytool -genkeypair -alias klinika -keyalg RSA -keysize 2048 -keystore klinika.jks -validity 365
keytool -importkeystore -srckeystore klinika.jks -destkeystore klinika.jks -deststoretype pkcs12

//add keys 
keytool -genkeypair -alias klinikaKey -keyalg RSA -keysize 2048 -validity 365 -keystore klinika.jks -storepass klinika
