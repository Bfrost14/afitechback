FROM nginx:1.13.12-alpine
WORKDIR /usr/src/app

COPY ./myafifront/dist/fuse /usr/share/nginx/html
COPY ./myafifront/nginx.conf /etc/nginx/conf.d/default.template

