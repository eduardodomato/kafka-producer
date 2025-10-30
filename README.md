# 🚀 Kafka Producer Service

<div align="center">

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.7-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Kafka](https://img.shields.io/badge/Apache%20Kafka-Latest-black.svg)](https://kafka.apache.org/)
[![Maven](https://img.shields.io/badge/Maven-3.x-blue.svg)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

A robust and production-ready **Spring Boot** application that provides a RESTful API for publishing messages to **Apache Kafka** topics. Built with modern Java technologies and best practices.

</div>

---

## 📋 Table of Contents

- [Overview](#-overview)
- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Prerequisites](#-prerequisites)
- [Getting Started](#-getting-started)
  - [Installation](#installation)
  - [Configuration](#configuration)
  - [Running the Application](#running-the-application)
- [API Documentation](#-api-documentation)
- [Architecture](#-architecture)
- [Project Structure](#-project-structure)
- [Monitoring & Health Checks](#-monitoring--health-checks)
- [Version History](#-version-history)
- [Contributing](#-contributing)
- [License](#-license)

---

## 🎯 Overview

This Kafka Producer Service is designed to be a lightweight, scalable microservice that enables applications to publish messages to Kafka topics through a simple REST API. It's built with Spring Boot 3.5.7 and leverages Spring Kafka for seamless integration with Apache Kafka clusters.

### Key Capabilities

- ✅ **RESTful API** for message publishing
- ✅ **Async message publishing** with CompletableFuture
- ✅ **Automatic topic creation** configuration
- ✅ **Comprehensive logging** for debugging and monitoring
- ✅ **Health checks** via Spring Boot Actuator
- ✅ **Production-ready** error handling

---

## ✨ Features

- 🔄 **Asynchronous Publishing**: Messages are published asynchronously using CompletableFuture for better performance
- 📝 **Structured Logging**: Comprehensive logging with SLF4J for tracking message publishing operations
- 🎛️ **Configurable Topics**: Easy configuration of Kafka topics through application properties
- 🏥 **Health Monitoring**: Built-in actuator endpoints for health checks and metrics
- ⚡ **High Performance**: Optimized for high-throughput message publishing
- 🛡️ **Error Handling**: Robust error handling with proper exception management
- 🔧 **Easy Integration**: Simple REST API that can be easily integrated with any application

---

## 🛠️ Tech Stack

- **Language**: Java 21
- **Framework**: Spring Boot 3.5.7
- **Messaging**: Apache Kafka (Spring Kafka)
- **Build Tool**: Apache Maven
- **Utilities**: Lombok
- **Monitoring**: Spring Boot Actuator
- **Testing**: JUnit, Spring Kafka Test

---

## 📦 Prerequisites

Before you begin, ensure you have the following installed:

- **Java Development Kit (JDK)**: Version 21 or higher
- **Apache Maven**: Version 3.6.x or higher
- **Apache Kafka**: Version 2.8.0 or higher (with Zookeeper or KRaft mode)
- **Docker & Docker Compose** (recommended): For running Kafka in a containerized environment

### Quick Kafka Setup with Docker Compose

If you don't have Kafka installed locally, you can quickly set it up using Docker Compose. The project includes a ready-to-use `docker-compose.yml` file that sets up Zookeeper and Kafka for local development.

#### Starting Kafka Services

```bash
# Start Zookeeper and Kafka in detached mode
docker-compose up -d

# View logs to verify services are starting
docker-compose logs -f
```

This will start:
- **Zookeeper** on port `2181`
- **Kafka** on port `9092` (accessible at `localhost:9092`)

#### Verifying Services are Running

```bash
# Check service status
docker-compose ps

# Check Kafka health
docker-compose exec kafka kafka-broker-api-versions --bootstrap-server localhost:9092

# View service logs
docker-compose logs kafka
docker-compose logs zookeeper
```

#### Stopping Kafka Services

```bash
# Stop services (keeps data)
docker-compose stop

# Stop and remove containers (keeps data volumes)
docker-compose down

# Stop and remove containers with data volumes (clean slate)
docker-compose down -v
```

#### Managing Kafka Topics

Once Kafka is running, you can manage topics using the Kafka CLI tools:

```bash
# List topics
docker-compose exec kafka kafka-topics --bootstrap-server localhost:9092 --list

# Create a topic
docker-compose exec kafka kafka-topics --bootstrap-server localhost:9092 --create --topic test-topic --partitions 3 --replication-factor 1

# Describe a topic
docker-compose exec kafka kafka-topics --bootstrap-server localhost:9092 --describe --topic test-topic

# Consume messages from a topic
docker-compose exec kafka kafka-console-consumer --bootstrap-server localhost:9092 --topic test-topic --from-beginning
```

#### Manual Kafka Setup (Alternative)

If you prefer to install Kafka manually:
```bash
# Download and extract Kafka
# Start Zookeeper
bin/zookeeper-server-start.sh config/zookeeper.properties

# Start Kafka (in a new terminal)
bin/kafka-server-start.sh config/server.properties
```

---

## 🚀 Getting Started

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd kafka-producer
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

   Or use the Maven wrapper:
   ```bash
   ./mvnw spring-boot:run
   ```

   On Windows:
   ```bash
   mvnw.cmd spring-boot:run
   ```

### Configuration

The application can be configured through `application.yml` or `application.properties`. Key configuration options:

#### `src/main/resources/application.yml`

```yaml
server:
  port: 9191

spring:
  kafka:
    producer:
      bootstrap-servers: localhost:9092
      value-serializer: org.apache.kafka.common.serialization.StringSerializer
      # Add more producer configurations as needed:
      # key-serializer: org.apache.kafka.common.serialization.StringSerializer
      # acks: all
      # retries: 3
```

#### Environment Variables

You can override these settings using environment variables:

```bash
export SPRING_KAFKA_PRODUCER_BOOTSTRAP_SERVERS=kafka-server:9092
export SERVER_PORT=9191
```

### Running the Application

1. **Start Kafka using Docker Compose** (recommended):
   ```bash
   docker-compose up -d
   ```
   
   Or ensure Kafka is running on `localhost:9092` (or update the bootstrap servers in configuration)

2. **Start the application**:
   ```bash
   mvn spring-boot:run
   ```

3. **Verify the application is running**:
   ```bash
   curl http://localhost:9191/actuator/health
   ```

   Expected response:
   ```json
   {
     "status": "UP"
   }
   ```

---

## 📚 API Documentation

### Base URL
```
http://localhost:9191
```

### Endpoints

#### Publish Message

Publishes a message to the configured Kafka topic.

**Endpoint**: `GET /producer/publish/{message}`

**Parameters**:
- `message` (path parameter): The message to publish to Kafka

**Example Request**:
```bash
curl http://localhost:9191/producer/publish/Hello%20Kafka
```

**Example Response** (Success):
```json
{
  "status": "Message published."
}
```

**Response Codes**:
- `200 OK`: Message published successfully
- `500 Internal Server Error`: Error occurred while publishing message

**Example Usage**:
```bash
# Publish a simple message
curl http://localhost:9191/producer/publish/Hello%20World

# Publish a message with special characters (URL encoded)
curl http://localhost:9191/producer/publish/Test%20Message%202024
```

---

## 🏗️ Architecture

```
┌─────────────┐
│   Client    │
│ Application │
└──────┬──────┘
       │ HTTP GET
       ▼
┌─────────────────────┐
│  MessageController  │
│  (REST Controller)  │
└──────┬──────────────┘
       │
       ▼
┌─────────────────────┐
│  MessagePublisher   │
│     (Service)       │
└──────┬──────────────┘
       │
       ▼
┌─────────────────────┐
│   KafkaTemplate     │
│  (Spring Kafka)     │
└──────┬──────────────┘
       │
       ▼
┌─────────────────────┐
│   Apache Kafka      │
│   Topic:            │
│   kafka-passthrough │
└─────────────────────┘
```

### Components

1. **MessageController**: REST controller that handles HTTP requests
2. **MessagePublisher**: Service layer that encapsulates Kafka publishing logic
3. **KafkaProducerConfig**: Configuration class for Kafka topics and producers
4. **KafkaTemplate**: Spring Kafka abstraction for Kafka operations

---

## 📁 Project Structure

```
kafka-producer/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/messaging/kafka/
│   │   │       ├── KafkaApplication.java          # Main application class
│   │   │       ├── config/
│   │   │       │   └── KafkaProducerConfig.java   # Kafka configuration
│   │   │       ├── controller/
│   │   │       │   └── MessageController.java     # REST API endpoints
│   │   │       └── service/
│   │   │           └── MessagePublisher.java      # Message publishing logic
│   │   └── resources/
│   │       ├── application.yml                    # Application configuration
│   │       └── application.properties             # Alternative config
│   └── test/
│       └── java/
│           └── com/messaging/kafka/
│               └── KafkaApplicationTests.java     # Integration tests
│
├── pom.xml                                         # Maven dependencies
├── docker-compose.yml                              # Docker Compose config for Kafka & Zookeeper
├── mvnw                                            # Maven wrapper (Unix)
├── mvnw.cmd                                        # Maven wrapper (Windows)
└── README.md                                       # This file
```

---

## 📊 Monitoring & Health Checks

This application includes Spring Boot Actuator for monitoring and health checks.

### Available Actuator Endpoints

| Endpoint | Description |
|----------|-------------|
| `/actuator/health` | Application health status |
| `/actuator/info` | Application information |
| `/actuator/metrics` | Application metrics |

### Enable Additional Endpoints

To expose more actuator endpoints, add to `application.yml`:

```yaml
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus
```

---

## 📝 Version History

### Version 0.0.1-SNAPSHOT (Current)
- ✨ Initial release
- ✅ Basic Kafka producer implementation
- ✅ REST API for message publishing
- ✅ Asynchronous message publishing
- ✅ Topic auto-creation support
- ✅ Comprehensive logging
- ✅ Spring Boot Actuator integration
- ✅ Error handling and exception management

---

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request. For major changes, please open an issue first to discuss what you would like to change.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### Development Guidelines

- Follow Java coding standards
- Write unit tests for new features
- Update documentation as needed
- Ensure all tests pass before submitting PR

---

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

---

## 🔗 Useful Links

- [Apache Kafka Documentation](https://kafka.apache.org/documentation/)
- [Spring for Apache Kafka](https://docs.spring.io/spring-kafka/docs/current/reference/html/)
- [Spring Boot Reference Guide](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
- [Kafka Producer Best Practices](https://kafka.apache.org/documentation/#producerconfigs)

---

## 💡 Future Enhancements

- [ ] Support for custom message schemas (Avro, JSON Schema)
- [ ] Batch message publishing
- [ ] Message routing based on content
- [ ] Rate limiting and throttling
- [ ] Authentication and authorization
- [ ] OpenAPI/Swagger documentation
- [ ] Message compression options
- [ ] Dead letter queue (DLQ) support

---

<div align="center">

**Built with ❤️ using Spring Boot and Apache Kafka**

For issues, questions, or suggestions, please [open an issue](https://github.com/your-repo/issues).

</div>
