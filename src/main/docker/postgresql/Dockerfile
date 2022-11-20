# The Base Image used to create this Image
FROM postgres:10.4

MAINTAINER Dawit (dawitabate@live.it)

RUN mkdir -p ~/script
RUN mkdir -p ~/.aws

COPY script ~/script
COPY aws ~/.aws

# To tell docker to expose this port
EXPOSE 5432

CMD ["postgres"]
