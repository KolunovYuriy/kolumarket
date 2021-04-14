# kolumarket 

docker run -p 5434:5432 -d \
-e POSTGRES_PASSWORD=password \
-e POSTGRES_USER=postgres \
-e POSTGRES_DB=kolumarket \
-v pgdata:/var/lib/postgresql/data \
postgres

IDEA VM options
-Dspring.profiles.active=pg