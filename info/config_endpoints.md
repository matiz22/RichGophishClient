
# User Configs Routes

This Ktor application defines several endpoints related to user configuration management.

## Create Config
Endpoint: ```POST /configs/post```
This endpoint is used to create a new Gophish configuration. It expects a JSON payload containing the details of the configuration to be created.

Request:
```json
{
  "name": "Config1",
  "userId": 123,
  "url": "https://example.com",
  "apiKey": "your-api-key"
}
```
Responses:
* 201 Created: Returns the details of the newly created configuration upon success.
* 400 Bad Request: If there is an issue with the request payload.


## Edit Config
Endpoint: ```PUT /configs/edit```
This endpoint is used to edit an existing Gophish configuration. It expects a JSON payload containing the details of the configuration to be edited.

Request:
```json
{
  "id": 1,
  "name": "UpdatedConfig",
  "userId": 123,
  "url": "https://updated-example.com",
  "apiKey": "updated-api-key"
}
```
Responses:
* 200 OK: Indicates successful edit of the configuration.
* 304 Not Modified: If there is no change in the configuration or if the edit operation is not successful.

## Get User Configs
Endpoint: ```GET /configs/user```
This endpoint is used to retrieve Gophish configurations associated with a specific user. It expects the user's ID in the request body.

Request:
```json
{
"userId": 123
}
```
Responses:
* 200 OK: Returns the Gophish configurations associated with the specified user.
* 400 Bad Request: If there is an issue with the request payload.

## Delete Config
Endpoint: ```DELETE /configs```
This endpoint is used to delete a Gophish configuration. It expects the ID of the configuration to be deleted in the request body.

Request:
```json
{
"id": 123
}
```
Responses:
* 200 OK: Indicates successful deletion of the configuration.
* 304 Not Modified: If the configuration deletion is not successful.
