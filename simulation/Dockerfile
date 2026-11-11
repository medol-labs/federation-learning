FROM node:22-bookworm-slim
WORKDIR /app
COPY . .
ENV PORT=3199
ENV HOST=0.0.0.0
EXPOSE 3199
CMD ["node", "src/server.js"]
