
# Task 1: Java Backend and REST API Example

**This is the first task in my submission.**

---

## Task Overview

Developed a Java REST API for managing and running "task" objects, representing shell commands to be executed in a Kubernetes pod. All data is persisted in MongoDB. The API supports searching, creating, deleting, and executing tasks.

---

## Features

- CRUD operations for tasks
- Execution tracking (start/end time, output)
- Secure command validation
- MongoDB storage

---

## Data Model

**Task Object**:  
- `id` (String): Task ID  
- `name` (String): Task name  
- `owner` (String): Task owner  
- `command` (String): Shell command  
- `taskExecutions` (List): Array of execution objects  

**TaskExecution Object**:  
- `startTime` (Date): Execution start
- `endTime` (Date): Execution end
- `output` (String): Command output  

*Example JSON:*

{
  "id": "123",
  "name": "Print Hello",
  "owner": "John Smith",
  "command": "echo Hello World again!",
  "taskExecutions": [
    {
      "startTime": "2023-04-21 15:51:42.276Z",
      "endTime": "2023-04-21 15:51:43.276Z",
      "output": "Hello World!"
    },
    {
      "startTime": "2023-04-21 15:52:42.276Z",
      "endTime": "2023-04-21 15:52:43.276Z",
      "output": "Hello World again!"
    }
  ]
}


## API Endpoints

| Method | Endpoint                        | Description                                                             |
|--------|----------------------------------|-------------------------------------------------------------------------|
| GET    | `/tasks`                        | Return all tasks, or one by ID if ID is provided                        |
| PUT    | `/tasks`                        | Create new task (JSON body, validates command)                          |
| DELETE | `/tasks/{id}`                   | Delete task by ID                                                       |
| GET    | `/tasks/search?name={string}`   | Find tasks by name substring                                            |
| PUT    | `/tasks/{id}/execution`         | Execute shell command for task and record execution                     |

---

## MongoDB Setup

- All tasks and executions are stored in MongoDB.
- Update connection details in your configuration file.

---

## How to Run

1. Install Java and MongoDB.
2. Configure application with your MongoDB connection.
3. Build with Maven/Gradle.
4. Run the server.
   --cmd
   mvn spring-boot:run
6. Use Postman/curl to test endpoints.

---

## Screenshots

**All screenshots include the current date/time and my name. Replace these paths with your actual screenshot files.**

### Terminal - App Running
<img width="1917" height="1019" alt="image" src="https://github.com/user-attachments/assets/094864d2-10ea-4605-9a93-926eb7ba5b4e" />



### Task Creation (Postman)

<img width="1919" height="1025" alt="image" src="https://github.com/user-attachments/assets/4a2cde9e-38fd-4dfd-8ceb-830d5ddbfb9f" />
<img width="1919" height="1016" alt="image" src="https://github.com/user-attachments/assets/3305d7ba-9663-41ab-aba0-4f91a45655aa" />
<img width="1919" height="1020" alt="image" src="https://github.com/user-attachments/assets/3a452429-4fb4-40f8-85c0-05ef866d8ad8" />
<img width="1919" height="1021" alt="image" src="https://github.com/user-attachments/assets/4627749b-635b-4cd8-9e96-4df11e2d92a2" />
<img width="1919" height="1021" alt="image" src="https://github.com/user-attachments/assets/99b1e07d-89ef-46b8-b591-104ed56ba272" />
<img width="1919" height="1016" alt="image" src="https://github.com/user-attachments/assets/15b5548e-1fcf-4723-8376-7bf9176201f0" />










### MongoDB Database View
<img width="1768" height="1007" alt="image" src="https://github.com/user-attachments/assets/b6514230-979a-4dd8-a887-de0d7c4a724d" />




---


