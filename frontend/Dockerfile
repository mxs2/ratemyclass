# Frontend Dockerfile
# Build stage
FROM node:18-alpine AS builder

WORKDIR /app

# Copy package files
COPY frontend/package*.json ./

# Install all dependencies (including dev dependencies needed for build)
RUN npm ci

# Copy source code
COPY frontend/ .

# Build application
RUN npm run build

# Production stage
FROM nginx:alpine

# Copy custom nginx configuration
COPY frontend/nginx.conf /etc/nginx/nginx.conf

# Copy built assets
COPY --from=builder /app/dist /usr/share/nginx/html

# Add healthcheck
RUN apk --no-cache add curl
HEALTHCHECK --interval=30s --timeout=3s --start-period=30s --retries=3 \
  CMD curl -f http://localhost || exit 1

# Expose port
EXPOSE 80

# Start nginx
CMD ["nginx", "-g", "daemon off;"]