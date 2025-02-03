# Event Registration System

This is a Spring Boot application for an Event Registration System. The application allows users to register for an event by providing their details and uploading a photo. The application stores the registration details in a PostgreSQL database and saves the uploaded photos in a specified directory.

## Prerequisites

- JDK 1.8
- PostgreSQL
- pgAdmin4 (optional, for database management)

## Instructions to Run the Application

### 1. Clone the Repository
```sh
git clone https://github.com/yourusername/event-registration-system.git
cd event-registration-system
```

### 2. Set Up the Database
- Open pgAdmin4 or your preferred PostgreSQL client.
- Create a new database or use an existing one.
- Create a table named `visitors` with the following columns:

```sql
CREATE TABLE visitors (
    id SERIAL PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(20) UNIQUE NOT NULL,
    photo_path VARCHAR(500)
);
```

### 3. Configure the Database Connection
- Open the `application.properties` file.
- Update the following properties with your database details:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/your_database_name
spring.datasource.username=your_database_username
spring.datasource.password=your_database_password
```

### 4. Run the Application
- Open a terminal and navigate to the project directory.
- Run the application using Maven:

```sh
./mvnw spring-boot:run
```

### 5. Access the Application
- Open a web browser and navigate to `http://localhost:8080/eventRegistration.html`.

## API Details

### Register Visitor

- **URL:** `/api/eventRegistrationSystem/register`
- **Method:** `POST`
- **Content-Type:** `multipart/form-data`
- **Parameters:**
  - `fullName` (String): The full name of the visitor.
  - `email` (String): The email address of the visitor.
  - `phone` (String): The phone number of the visitor.
  - `photo` (File): The photo of the visitor.
- **Response:**
  - `200 OK`: Returns the visitor ID if the registration is successful.
  - `400 Bad Request`: Returns an error message if the registration fails.

#### Example Request

Using Postman or any other API client, send a `POST` request to:
```sh
http://localhost:8080/api/eventRegistrationSystem/register
```
with the following form-data:

- `fullName`: John Doe
- `email`: johndoe@example.com
- `phone`: 1234567890
- `photo`: [Select a file]

### Download Badge

- **URL:** `/api/eventRegistrationSystem/badge/{visitorId}`
- **Method:** `GET`
- **Response:**
  - `200 OK`: Returns a PDF file containing the badge for the visitor.
  - `404 Not Found`: Returns an error message if the visitor ID is not found.

#### Example Request

Using a web browser or any API client, send a `GET` request to:
```sh
http://localhost:8080/api/eventRegistrationSystem/badge/{visitorId}
```
where `{visitorId}` is the ID of the registered visitor.

## Directory Structure

```
 event-registration-system/
 ├── src/main/java/com/example/eventRegistrationSystem/  # Java source code
 ├── src/main/resources/  # Application properties and HTML templates
 ├── upload/  # Directory where uploaded photos are stored
 ├── pom.xml  # Maven project configuration
 ├── application.properties  # Database configuration file
 ├── README.md  # Documentation
```

## Additional Information

- Ensure that the `uploads` directory exists in the project root or is created by the application.
- The application uses iText for generating PDF badges for registered visitors.

## Troubleshooting

- Ensure that the PostgreSQL server is running and accessible.
- Verify the database connection details in the `application.properties` file.
- Check the application logs for any errors or warnings.

---

This README file provides detailed instructions on how to set up, configure, and run the Event Registration System application. It also includes API details for registering visitors and downloading badges, as well as troubleshooting tips.
