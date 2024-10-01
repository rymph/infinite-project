FROM node:latest AS build-stage
WORKDIR /usr/src/front

COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

FROM nginx:latest AS production-stage
COPY ./nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build-stage /usr/src/front/dist /usr/share/nginx/html
EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]