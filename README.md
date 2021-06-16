# kolumarket

##PostgreSQL
docker run -p 5434:5432 -d -e POSTGRES_PASSWORD=password -e POSTGRES_USER=postgres -e POSTGRES_DB=kolumarket -v pgdata:/var/lib/postgresql/data postgres

IDEA VM options
-Dspring.profiles.active=pg

## Redis
docker run --restart=always -d --name redis_1 -v /opt/redis/etc/redis.conf:/usr/local/etc/redis/redis.conf -v /opt/redis/data:/data -p 127.0.0.1:6379:6379 redis redis-server /usr/local/etc/redis/redis.conf

docker rm -f redis
docker volume rm redis-data

## RabbitMQ

### Вариант 1
docker run -d --name some-rabbit -p 5672:5672 -p 15672:15672 rabbitmq:3-management
Логин: guest
Пароль: guest


### Вариант 2
docker run -d --hostname my-rabbit --name some-rabbit -p 5672:5672 -p 15672:15672 -e RABBITMQ_DEFAULT_USER=user -e RABBITMQ_DEFAULT_PASS=password rabbitmq:3-management

Логин: user
Пароль: password

### Полезные ссылки
https://blog.bayrell.org/ru/linux/docker/26-zapusk-rabbitmq-v-docker-konteynere.html
https://stackoverflow.com/questions/41089268/rabbitmq-connection-refused-from-docker-container-to-local-host
