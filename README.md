## Docker Configuration

### Pull images
1. docker pull mysql:8
2. docker pull postgres:14-alpine

### Build images
1. docker build -t courses . -f ./msvc-courses/Dockerfile
2. docker build -t users . -f ./msvc-users/Dockerfile 
3. docker build -t auth . -f ./msvc-auth/Dockerfile

### Create docker network
docker network create spring-msvc

### Create and run  containers
1. docker run -p 8001:8001 -d --rm --env-file ./msvc-users/.env --name msvc-users --network spring-msvc users
2. docker run -p 8002:8002 -d --rm --env-file ./msvc-courses/.env --name msvc-courses --network spring-msvc courses
3. docker run -p 3307:3306 -d  --name mysql8 --network spring-msvc -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=msvc_users -v data-mysql:/var/lib/mysql mysql:8
4. docker run -p 5532:5432 -d --name postgres14 --network spring-msvc -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=msvc_courses -v data-postgres:/var/lib/postgresql/data postgres:14-alpine

### Utils containers
1. docker run -it --rm --network spring-msvc mysql:8 bash // access Mysql from console
2. docker run -it --rm --network spring-msvc postgres:14-alpine bash // access Postgres from console

### Start all services with docker-compose
docker compose up -d

### Stop all services with docker-compose
docker-compose down

## Kubernetes Configuration

### Create Kubernetes Role
1. kubectl create clusterrolebinding admin --clusterrole=cluster-admin --serviceaccount=default:default

### Create Secrets
1. kubectl apply -f ./secret.yaml

### Create Config Map
1. kubectl apply -f ./configmap.yaml

### Create Persistent Volumes
1. kubectl apply -f ./mysql-pv.yaml
2. kubectl apply -f ./postgres-pv.yaml

### Create Persistent Volume Claims
1. kubectl apply -f ./mysql-pvc.yaml
2. kubectl apply -f ./postgres-pvc.yaml

### Create Deployment
1. kubectl apply -f ./deployment-mysql.yaml
2. kubectl apply -f ./deployment-users.yaml
3. kubectl apply -f ./deployment-postgres.yaml
4. kubectl apply -f ./deployment-courses.yaml
5. kubectl apply -f ./gateway.yaml
6. kubectl apply -f ./auth.yaml

### Create Internal Services
1. kubectl apply -f ./svc-postgres.yaml
2. kubectl apply -f ./svc-mysql.yaml
3. kubectl apply -f ./svc-users.yaml
4. kubectl apply -f ./svc-courses.yaml

### Generate Public Cluster IP
1. minikube service msvc-users --url
2. minikube service msvc-courses --url
3. minikube service msvc-gateway --url
4. minikube service msvc-auth --url

## Login URLs
1. Get Code:
   1. AUTH-URL/oauth2/authorize?response_type=code&client_id=users-client&scope=read write&redirect_uri=USERS-URL/authorized
   2. AUTH-URL/login  -> with username and password in the body as x-www-form-urlencoded 
2. Get Token From Code:
   AUTH-URL/oauth2/token
Body: x-www-form-urlencoded
- code
- grant_type: authorization_code
- redirect_uri: USERS-URL/authorized
