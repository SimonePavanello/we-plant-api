# Postgres docker container
## Build docker image

```
docker build -t nino-postgresql .
```


   19  apt-get -y install unzip
   20  apt-get update
   21  apt-get -y install unzip
   22  apt-get -y install curl
   23  apt-get -y install python-pip
   24  curl "https://s3.amazonaws.com/aws-cli/awscli-bundle.zip" -o "awscli-bundle.zip"
   25  unzip awscli-bundle.zip
   26  ./awscli-bundle/install -b ~/bin/aws
   27  aws s3 ls
   28  aws
   29  /usr/local/bin/aws --version
   30  ./awscli-bundle/install -i /usr/local/aws -b /usr/local/bin/aws
   31  /usr/local/bin/aws --version
