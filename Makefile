# Define variables
APP_NAME=payment-rule-engine-app
DOCKER_IMAGE=$(APP_NAME)
DOCKER_HUB_REPO=arjunr1432/$(APP_NAME)
DOCKER_COMPOSE_FILE=docker-compose.yml
DOCKER_COMPOSE_FILE_DB=docker-compose-db.yml

# Default target
.PHONY: all
all: run

# Target to build the application using Maven
.PHONY: build
build:
	mvn clean install

# Target to package the application
.PHONY: package
package: build
	mvn clean package

# Target to build the Docker image
.PHONY: docker-build
docker-build: package
	docker build -t $(DOCKER_IMAGE) .
	docker tag $(DOCKER_IMAGE) $(DOCKER_HUB_REPO)

# Target to push the Docker image to Docker Hub
.PHONY: docker-push
docker-push: docker-build
	docker push $(DOCKER_HUB_REPO)

# Target to run Docker Compose
.PHONY: docker-compose-up
docker-compose-up:
	docker-compose -f $(DOCKER_COMPOSE_FILE) up -d

# Target to package, build Docker image, push to Docker Hub, and run Docker Compose
.PHONY: run
run: docker-push docker-compose-up

# Target to run Docker Compose with pre defined images
.PHONY: just-run
just-run:
	docker-compose -f $(DOCKER_COMPOSE_FILE) up -d

# Target to run Docker Compose with pre defined images for database
.PHONY: just-db
just-db:
	docker-compose -f $(DOCKER_COMPOSE_FILE_DB) up -d

# Target to stop the running docker containers
.PHONY: stop
stop:
	docker-compose -f $(DOCKER_COMPOSE_FILE) down

# Target to stop the running db only docker containers
.PHONY: stop-db
stop-db:
	docker-compose -f $(DOCKER_COMPOSE_FILE_DB) down