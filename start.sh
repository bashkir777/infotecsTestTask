#!/bin/bash

docker-compose up --build -d

sleep 2

# Launch integration tests
docker-compose exec ftp-client java -cp "target/test-classes:target/classes:target/dependency/testng-7.4.0.jar:target/dependency/jcommander-1.78.jar:target/dependency/jquery-3.5.1.jar" org.testng.TestNG integration-testng.xml

passed=$(docker-compose exec ftp-client cat passed)

# Check tests status
if [ "$passed" == "false" ]; then
    echo "Integration tests failed. Terminating network..."
    docker-compose down
    clear
    echo "Integration tests failed. Docker network has been terminated."
    exit 0
fi

clear

echo "Integration tests successfully passed."
echo "Type 'start' and press Enter to initiate the session."


docker attach ftp-client

clear

docker-compose down

clear 

echo "App has been closed successfully. Docker network has been terminated."
