# Tweet Generator API

A Spring Boot application for generating tweets using the Groq API.

## Environment Setup

### Groq API Key

This application requires a Groq API key to function properly. You can set it up in one of the following ways:

1. **Environment Variable**:
   Set the `GROQ_API_KEY` environment variable in your system.

   ```bash
   # For Linux/Mac
   export GROQ_API_KEY=your_groq_api_key_here

   # For Windows (Command Prompt)
   set GROQ_API_KEY=your_groq_api_key_here

   # For Windows (PowerShell)
   $env:GROQ_API_KEY="your_groq_api_key_here"
   ```

2. **Application Properties**:
   Create a file named `application-local.properties` in the `src/main/resources` directory with the following content:

   ```properties
   groq.api.key=your_groq_api_key_here
   ```

   Then run the application with the `local` profile:

   ```bash
   ./mvnw spring-boot:run -Dspring.profiles.active=local
   ```

3. **Environment File (Recommended)**:
   Copy the `.env.template` file to `.env` in the root directory of the project and fill in your API key:

   ```
   # Copy the template
   cp .env.template .env

   # Edit the .env file with your editor
   # Set GROQ_API_KEY=your_actual_api_key_here
   ```

   The application will automatically load variables from this file on startup.

## Running the Application

```bash
./mvnw spring-boot:run
```

## Running Tests

```bash
./mvnw test
```
