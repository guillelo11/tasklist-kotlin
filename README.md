# tasklist-kotlin

This pretends to be a basic and small proyect made just to learn and test a variety of stuff including Spring Boot, Kotin, Angular and Docker. It'll be **WIP** for a while.

## Run it

1. Create the todo-list-ws docker image running `./gradlew docker`
2. Build the frontend `ng build --prod` and create the docker image `docker image build -t todo-angular-front .`
3. Run docker componse `docker-compose up`

### Sample POST request
`curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"description":"xyz"}' \
  http://localhost:8080/api/task`

### Sample GET request 
`curl --request GET http://localhost:8080/api/task`