FROM node:20

WORKDIR /app/frontend

COPY ./package.json ./

RUN npm install

COPY ./ ./

EXPOSE 3000

ENTRYPOINT ["npm", "run", "dev"]
