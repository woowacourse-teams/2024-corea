#!/bin/bash

# Move to the backend directory and run the Gradle build
cd .. & cd backend

./gradlew build

# Check if the build was successful
if [ $? -ne 0 ]; then
  echo "Gradle build failed. Exiting."
  exit 1
fi

# Move back to the project root directory
cd ..

# Start Docker Compose
docker-compose -f develop-docker-compose.yml up --build
