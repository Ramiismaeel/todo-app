# Todo app

![Java](https://img.shields.io/badge/java-25-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-4-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![JUnit 6](https://img.shields.io/badge/JUnit-6-25A162.svg?style=for-the-badge&logo=spring&logoColor=white)



### Setup: Download frontend
Today we will focus on the ToDo backend. Follow the steps to complete the task.

#### Download the frontend
Create a Spring Boot project in Intellij and add the necessary dependencies directly (e.g., Spring Web, Spring Data, Lombok, …)
Copy the contents of frontend.zip into the following directory: src/main/resources/static



Start the backend -> the frontend should now be visible at localhost:8080!
### Research: Analyze accesses
Use the developer tools in your browser (the “Network” tab) to analyze the accesses of the frontend. Which endpoints are being addressed (URLs and HTTP methods)? What data is being sent to the server?

### Coding: Develop backend
Implement the HTTP endpoints that your frontend needs. Go through the endpoints step by step and also write tests for your methods.

If you are unsure on which endpoint to program the next steps are containg hints

#### Hint: 1. GET
Provide a GET Endpoint at /api/todo, this endpoint should return all saved todos in a list.

#### Hint: 2. POST
Provide a POST Endpoint at /api/todo, this endpoint should accept a body with a todo-dto which contains the attributes: description and status. The description should be a String and the status should be an enum or a String.

#### Hint: 3. GET By Id
Provide a GET Endpoint at /api/todo/{id}, this endpoint should return a specific todo depending on the id provided as a Path-Variable.

#### Hint: 4. PUT
Provide a PUT Endpoint at /api/todo/{id}, this endpoint should accept a body with a todo-dto and updates a already existing to depending on the Id given on the Path-Variable.

#### Hint: 5. DELETE
Provide a DELETE Endpoint at /api/todo/{id}, this endpoint should delete an existing depending on the Id given in the Path-Variable.

#### Bonus: ENUM
If your Order.status is a String, refactor you code and use an OrderStatus enum.

#### Bonus: REST
Research which Statuscode the POST Request should return when programming a REST API. And implement it!

#### BONUS: REST
Research which Statuscode the DELETE Request should return when programming a REST API. And implement it!

#### Bonus: Monitoring
Add a current dependency to JavaMelody to your backend. Your backend should now display a dashboard at /monitoring that provides information about the performance of your backend.

Click around in the frontend a few times and check the metrics of your backend.

Can you imagine a situation where monitoring your backend could be helpful?

#### Bonus: Undo Endpoint
Implement an HTTP endpoint in the backend at /undo that undoes the last change.

No adjustments are necessary in the frontend. Simply call the endpoint via Postman and check if the last change was undone.

Write good integration tests for this endpoint.

It should also be possible to call the endpoint multiple times to undo multiple changes.

#### Bonus: Redo Endpoint
Now, also write a /redo endpoint to undo a previous undo.
