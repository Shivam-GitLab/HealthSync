# HealthSync

A modern microservices-based health and wellness management platform built with Spring Boot and Spring Cloud. HealthSync provides a comprehensive ecosystem for managing user health data, tracking activities, and leveraging AI-powered insights.

## 🏗️ Architecture Overview

HealthSync is built on a cloud-native microservices architecture with the following components:

```
┌─────────────────────────────────────────────────────────┐
│                    API Gateway                          │
│           (Spring Cloud Gateway with WebFlux)           │
└──────────┬──────────────────────────────────────────────┘
           │
    ┌──────┴──────┬──────────────┬──────────────┐
    │             │              │              │
┌───▼─┐      ┌────▼───┐   ┌─────▼────┐   ┌────▼────┐
│User │      │Activity│   │   AI     │   │ Eureka  │
│Service    │Service  │   │ Service  │   │ Server  │
└─────┘      └────────┘   └──────────┘   └─────────┘
    │             │              │
    └─────────────┴──────────────┘
              │
      ┌───────▼────────┐
      │ Config Server  │
      └────────────────┘
```

<img width="1129" height="634" alt="image" src="https://github.com/user-attachments/assets/36c67a02-8a49-427f-bdd7-9cb4d0be1b3a" />


## 🚀 Services

### 1. **API Gateway** (`api-gateway`)
- **Purpose**: Single entry point for all client requests
- **Framework**: Spring Cloud Gateway
- **Features**:
  - Request routing to appropriate microservices
  - Load balancing using Netflix Eureka
  - Request/response filtering and transformation
- **Port**: 8080 (configurable)
- **Dependencies**:
  - Spring Cloud Gateway
  - Eureka Client
  - Spring Cloud Load Balancer
  - Config Server Client

### 2. **User Service** (`user-service`)
- **Purpose**: Manages user accounts, authentication, and user profile data
- **Framework**: Spring Boot MVC
- **Database**: PostgreSQL (relational database)
- **Features**:
  - User registration and account management
  - Profile management
  - User validation and error handling
- **Port**: 8081 (configurable)
- **Dependencies**:
  - Spring Data JPA
  - PostgreSQL Driver
  - Spring Boot Validation
  - Eureka Client
  - Actuator for monitoring

### 3. **Activity Service** (`activity-service`)
- **Purpose**: Tracks and manages user health activities and fitness data
- **Framework**: Spring Boot WebFlux (reactive)
- **Database**: MongoDB (document-based)
- **Features**:
  - Activity logging and tracking
  - Fitness metrics management
  - Real-time data processing with Kafka
  - OpenAPI/Swagger documentation
- **Port**: 8082 (configurable)
- **Dependencies**:
  - Spring Data MongoDB
  - Spring WebFlux
  - Apache Kafka
  - Eureka Client
  - SpringDoc OpenAPI (Swagger)
  - Actuator for monitoring

### 4. **AI Service** (`ai-service`)
- **Purpose**: Provides AI-powered insights and recommendations
- **Framework**: Spring Boot WebFlux (reactive)
- **Database**: MongoDB
- **Features**:
  - Health insights and analysis
  - Personalized recommendations
  - Event-driven processing via Kafka
  - Reactive programming for high throughput
- **Port**: 8083 (configurable)
- **Dependencies**:
  - Spring Data MongoDB
  - Spring WebFlux
  - Apache Kafka
  - Eureka Client
  - Actuator for monitoring

### 5. **Eureka Server** (`eureka-server`)
- **Purpose**: Service registry and discovery
- **Framework**: Spring Cloud Netflix Eureka
- **Features**:
  - Dynamic service registration
  - Service discovery
  - Health checks
  - Load balancer integration
- **Port**: 8761
- **Dependencies**:
  - Spring Cloud Netflix Eureka Server

### 6. **Config Server** (`config-server`)
- **Purpose**: Centralized configuration management
- **Framework**: Spring Cloud Config Server
- **Features**:
  - Externalized configuration
  - Environment-specific profiles
  - Dynamic property updates
- **Port**: 8888
- **Dependencies**:
  - Spring Cloud Config Server

## 🛠️ Technology Stack

### Core Framework
- **Java**: 21+
- **Spring Boot**: 4.1.x
- **Spring Cloud**: 2025.x

### Data Persistence
- **PostgreSQL**: User service data
- **MongoDB**: Activity and AI service data
- **Kafka**: Event streaming and message broker

### Cloud & Messaging
- **Netflix Eureka**: Service registration and discovery
- **Spring Cloud Gateway**: API routing
- **Apache Kafka**: Event-driven architecture
- **Spring Cloud Config**: Configuration management

### Development Tools
- **Maven**: Build automation
- **Lombok**: Reducing boilerplate code
- **Project Reactor**: Reactive programming

## 📋 Prerequisites

- Java 21 or higher
- Maven 3.6 or higher
- Docker (optional, for running databases)
- PostgreSQL 12+ (for User Service)
- MongoDB 4.0+ (for Activity and AI Services)
- Kafka 2.8+ (for event streaming)

## 🚀 Getting Started

### 1. Clone the Repository
```bash
git clone https://github.com/Shivam-GitLab/HealthSync.git
cd HealthSync
```

### 2. Build the Project
```bash
mvn clean install
```

### 3. Start Eureka Server
```bash
cd eureka-server
mvn spring-boot:run
```
Access at: `http://localhost:8761`

### 4. Start Config Server
```bash
cd config-server
mvn spring-boot:run
```

### 5. Start Core Services
In separate terminals:
```bash
# User Service
cd user-service
mvn spring-boot:run

# Activity Service
cd activity-service
mvn spring-boot:run

# AI Service
cd ai-service
mvn spring-boot:run
```

### 6. Start API Gateway
```bash
cd api-gateway
mvn spring-boot:run
```

The gateway will be available at `http://localhost:8080`

## 📚 API Endpoints

All requests should be routed through the API Gateway:

- **User Service**: `/users/*`
- **Activity Service**: `/activities/*`
- **AI Service**: `/ai/*`

For detailed API documentation, check the OpenAPI/Swagger specs in each service.

## 🔧 Configuration

Each microservice has its own configuration:
- `application.properties` or `application.yml` in the resource folder
- Central configuration via Config Server

Example environment variables:
```bash
EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://localhost:8761/eureka
SPRING_DATA_MONGODB_URI=mongodb://localhost:27017/healthsync
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/healthsync_user
```

## 📊 Monitoring & Health Checks

Each service includes Spring Boot Actuator for monitoring:
- Health endpoint: `/actuator/health`
- Metrics endpoint: `/actuator/metrics`
- Service-specific endpoints: `/actuator/*`

## 🏢 Project Structure

```
HealthSync/
├── api-gateway/              # API Gateway service
├── user-service/             # User management service
├── activity-service/         # Activity tracking service
├── ai-service/               # AI insights service
├── eureka-server/            # Service registry
├── config-server/            # Configuration server
└── README.md                 # Project documentation
```

## 🤝 Contributing

Contributions are welcome! Please follow these guidelines:
1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is open source and available under the MIT License.

## 👤 Author

**Shivam-GitLab**

## 📞 Support

For issues, questions, or suggestions, please open an issue on the GitHub repository.

## 🚀 Future Enhancements

- [ ] Distributed tracing with Spring Cloud Sleuth
- [ ] Circuit breaker pattern with Resilience4j
- [ ] Enhanced security with OAuth2/JWT
- [ ] Kubernetes deployment configurations
- [ ] Performance optimization and caching strategies
- [ ] Mobile app integration
- [ ] Advanced analytics dashboard

---

**Last Updated**: 2026-08-31

For the latest updates, visit: [HealthSync Repository](https://github.com/Shivam-GitLab/HealthSync)
