# CoderHack Leaderboard

## Project Description

The CoderHack Leaderboard API is a Spring Boot application designed to manage the leaderboard for a single contest on a coding platform. It uses MongoDB for data persistence and handles CRUD operations for user registrations, updates, and retrieval. The application also awards badges based on user scores and ensures valid badge assignment.

## Features

- **User Management**: Register, update, and deregister users.
- **Score Handling**: Update user scores and award badges accordingly.
- **Sorting**: Retrieve users sorted by their scores.
- **Validation**: Ensure valid badge assignments and score updates.
- **Error Handling**: Return appropriate HTTP status codes for errors.

## Technology Stack

- **Spring Boot**: For building the RESTful API.
- **MongoDB**: For data persistence.
- **JUnit**: For testing the API.
- **Postman**: For API documentation and testing.

## Setup Instructions

### Prerequisites

- Java 22
- Gradle
- MongoDB

### Clone the Repository

```bash
https://github.com/Shomik-Mitra/CoderHack-LeaderBoard.git
```

### Key Updates:

- **Postman Collection and Documentation**:
```
 https://www.postman.com/shomik-mitra/workspace/shomik-mitra/documentation/20072503-43dfc420-f044-4851-b3ab-b14e92432c52

```
###  Project Structure

The project follows a standard Spring Boot structure:

- `src/main/java`: Contains source code for the application logic (entities, controllers, services, repositories).
- `src/test/java`: Holds unit tests for the API functionality.
- `resources`: Stores configuration files (application.yaml, etc.).

### API Usage

The API can be tested and interacted with using tools like Postman. Refer to the Postman Collection link for detailed examples of each API endpoint.
