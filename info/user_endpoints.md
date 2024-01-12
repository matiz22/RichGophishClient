# User Configs Routes

This Ktor application defines several endpoints related to user management.


## Notice
Id's are a numbers.

## User By ID
Endpoint: ```POST /user/userById```
This endpoint is used to log in a user by their ID. It expects a JSON payload containing id and password in the request body.

Request:
```json
{
  "id": <user_id>,
  "password": "<user_password>"
}
```
Responses:
* 200 OK: Returns the user details upon successful login.
* 400 Bad Request: If the user with the provided ID and password does not exist.

## User By Email
Endpoint: ```POST /user/userByEmail```
This endpoint is used to log in a user by their email. It expects a JSON payload containing email and password in the request body.

Request:
```json
{
  "email": "<user_email>",
  "password": "<user_password>"
}
```
Responses:
* 200 OK: Returns the user details upon successful login.
* 400 Bad Request: If the user with the provided email and password does not exist.

## Create User
Endpoint: ```POST /user/createUser```
This endpoint is used to create a new user. It expects a JSON payload containing email and password in the request body.

Request:
```json
{
  "email": "<user_email>",
  "password": "<user_password>"
}
```
Responses:
* 200 OK: Returns the newly created user details upon successful user creation.
* 400 Bad Request: If a user with the provided email already exists.
  500 Internal Server Error: If something goes wrong during user creation.

## Delete User
Endpoint: ```DELETE /user/deleteUser```
This endpoint is used to delete a user by their ID. It expects a JSON payload containing the id of the user to be deleted.

Request:
```json
{
"id": <user_id>
}
```
Responses:
* 200 OK: Returns a message indicating successful deletion.
* 500 Internal Server Error: If something goes wrong during user deletion.
