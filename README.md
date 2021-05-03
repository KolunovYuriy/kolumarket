# kolumarket

docker run -p 5434:5432 -d -e POSTGRES_PASSWORD=password -e POSTGRES_USER=postgres -e POSTGRES_DB=kolumarket -v pgdata:/var/lib/postgresql/data postgres

IDEA VM options
-Dspring.profiles.active=pg



docker run --restart=always -d --name redis_1 -v /opt/redis/etc/redis.conf:/usr/local/etc/redis/redis.conf -v /opt/redis/data:/data -p 127.0.0.1:6379:6379 redis redis-server /usr/local/etc/redis/redis.conf

docker rm -f redis
docker volume rm redis-data