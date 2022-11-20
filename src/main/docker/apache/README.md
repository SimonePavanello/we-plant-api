# Apache docker container
## Build docker image

```
docker build -t httpd-proxy .
```
##Run docker container

```
docker container run --publish 80:80 --name apacheserver -v ~/apache-we-plant/apacheconf/sites:/usr/local/apache2/conf/sites httpd-proxy
```
