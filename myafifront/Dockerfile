FROM nginx:1.13.12-alpine
WORKDIR /usr/src/app

COPY ./dist/fuse /usr/share/nginx/html
COPY ./nginx.conf /etc/nginx/conf.d/default.template

