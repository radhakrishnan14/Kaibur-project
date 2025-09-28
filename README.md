Copy, paste, and fill in your screenshot file names. This template is ready for direct use in your repository.```markdown
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
<img width="1919" height="1189" alt="image" src="https://github.com/user-attachments/assets/1007ed02-be60-4693-a203-032be440b00b" />


### Task Creation (Postman)

<img width="1920" height="1200" alt="Screenshot 2025-09-28 215323" src="https://github.com/user-attachments/assets/415824c0-efa8-408d-a398-dcb76d41f72d" />

<img width="1920" height="1200" alt="image" src="https://github.com/user-attachments/assets/ab96d84a-db71-41fd-8c1f-39dbbb36f4ec" />

<img width="1919" height="1199" alt="image" src="https://github.com/user-attachments/assets/481b7dd8-af93-4625-b1db-981788fc4169" />

<img width="1920" height="1200" alt="image" src="https://github.com/user-attachments/assets/3863c517-ace2-451c-995c-cc9d96bf8cc4" />




### MongoDB Data View
<img width="1762" height="1006" alt="image" src="https://github.com/user-attachments/assets/5017c159-3d96-4fb3-8255-c46700fdaaa8" />



---


