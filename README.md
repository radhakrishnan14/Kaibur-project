TASK1:JAVA BACKEND AND REST API END POINT
This is the first task as part of the project submission.


TASK OVERVIEW
Developed a Java REST API for managing and running "task" objects, representing shell commands to be executed in a Kubernetes pod. All data is persisted in MongoDB. The API supports searching, creating, deleting, and executing tasks.

FEATURES
CRUD operations for tasks
Execution tracking (start/end time, output)
Secure command validation
MongoDB storage


DATA MODEL
Task Object:

id (String): Task ID
name (String): Task name
owner (String): Task owner
command (String): Shell command
taskExecutions (List): Array of execution objects
TaskExecution Object:

startTime (Date): Execution start
endTime (Date): Execution end
output (String): Command output

Example JSON: 
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

API Endpoints
Method	Endpoint	Description
GET	/tasks	Return all tasks, or one by ID if ID is provided
PUT	/tasks	Create new task (JSON body, validates command)
DELETE	/tasks/{id}	Delete task by ID
GET	/tasks/search?name={string}	Find tasks by name substring
PUT	/tasks/{id}/execution	Execute shell command for task and record execution
