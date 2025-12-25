# Electron + Spring Boot + Python Environment

 This project demonstrates a simple integration of:
 - **Electron** (Frontend Desktop App)
 - **Spring Boot** (Backend API)
 - **Python** (Script execution)

 ## Directory Structure

 - `electron-app/`: Node.js + Electron application.
 - `springboot-backend/`: Java Spring Boot application.
 - `python-scripts/`: Python scripts called by the backend.
 - `Dockerfile` & `docker-compose.yml`: For containerized deployment.

 ## Prerequisites

 - **Docker Desktop** (Recommended)
 - Node.js & npm (for Electron)

 ## How to Run

 ### 1. Start the Spring Boot Backend

 We use Docker Compose to manage the backend. This also mounts the `python-scripts` folder, so you can edit Python code internally without rebuilding!

 ```bash
 # Start the backend (in background)
 docker compose up -d
 
 # View logs
 docker compose logs -f
 
 # Stop the backend
 docker compose down
 ```

 The server will start on `http://localhost:8080`.

 ### 2. Start the Electron App

 Open a new terminal window.

 ```bash
 cd electron-app
 npm start
 ```

 The application window should appear, showing the status of the Spring Boot backend and the Python script execution.

 ## How it Works

 1. **Electron** makes an HTTP request to `http://localhost:8080/api/python`.
 2. **Spring Boot** receives the request and executes the python script.
    - Inside Docker: It executes `/python-scripts/hello.py`.
    - Locally: It executes `../python-scripts/hello.py`.
 3. **Python** prints a JSON string to stdout.
 4. **Spring Boot** captures the output and sends it back to **Electron**.
 5. **Electron** displays the result.
