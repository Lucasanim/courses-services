## Docker Configuration

### Pull images
1. docker pull mysql:8
2. docker pull postgres:14-alpine

### Build images
1. docker build -t courses . -f ./msvc-courses/Dockerfile
2. docker build -t users . -f ./msvc-users/Dockerfile 

### Create docker network
docker network create spring-msvc

### Create and run  containers
1. docker run -p 8001:8001 -d --rm --name msvc-users --network spring-msvc users
2. docker run -p 8002:8002 -d --rm --name msvc-courses --network spring-msvc courses
3. docker run -p 3307:3306 -d  --name mysql8 --network spring-msvc -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=msvc_users -v data-mysql:/var/lib/mysql mysql:8
4. docker run -p 5532:5432 -d --name postgres14 --network spring-msvc -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=msvc_courses -v data-postgres:/var/lib/postgresql/data postgres:14-alpine