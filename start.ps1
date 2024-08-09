docker-compose up --build -d

Start-Sleep -Seconds 2

# Launch integration tests
docker-compose exec ftp-client java -cp "target/test-classes:target/classes:target/dependency/testng-7.4.0.jar:target/dependency/jcommander-1.78.jar:target/dependency/jquery-3.5.1.jar" org.testng.TestNG integration-testng.xml

$passed = docker-compose exec ftp-client cat passed

# Check tests status
if ($passed -eq "false") {
    Write-Output "Integration tests failed. Terminating network..."
    docker-compose down
    Clear-Host
    Write-Output "Integration tests failed. Docker network has been terminated."
    exit 0
}

Clear-Host

Write-Output "Integration tests successfully passed."
Write-Output "Type 'start' and press Enter to initiate the session."

docker attach ftp-client

Clear-Host

docker-compose down

Clear-Host

Write-Output "App closed successfully. Docker network has been terminated."