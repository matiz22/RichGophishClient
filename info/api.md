
# RichGophishApi Documentation

Welcome to the documentation for the RichGophishApi! This API is designed to be secure and requires an API key for access.


## Authentication

To access the API, include the API key in the `X-Api-Key` header. The API key is retrieved from the system environment. If not found, it defaults to 'test'. Ensure that the API key is set either in the environment or as a fallback in your application.

### Setting API Key in Environment

Make sure to set the API key in the system environment. If not set, it will default to 'test'.
```bash
export API_KEY=your_actual_api_key
```

### .env File

The API key can also be included in the .env file, which is used by the application. Add the following line to your .env file:

```env
API_KEY=your_actual_api_key
```

### Docker Compose

Value is included in docker-compose.yml

```yaml
version: '3.8'
services:
  server:
    build:
      context: ../../../../Downloads
      dockerfile: Dockerfile
    ports:
      - "80:8080"
    environment:
      - PORT=80
      - API_KEY=${API_KEY}
```

## Deployment Workflow
This API is integrated with GitHub Actions to automate the deployment process.

### Workflow Details

The GitHub Actions workflow triggers on each push to the `/server` directory. It automates the deployment to Docker Hub and subsequently deploys the updated application on your Azure server.

### Docker Hub
The Docker image is pushed to Docker Hub, making it easily accessible for deployment. Ensure that your Docker Hub credentials are configured in the GitHub repository secrets.

### Azure Server
The GitHub Actions workflow also deploys the updated Docker image to your Azure server. Make sure to configure the necessary Azure credentials in your GitHub repository secrets.

### Testing
Feel free to clone this repository and test the API with your Azure or Docker credentials. Ensure that the API key is correctly set in your environment or in the .env file.
Example deploy [here](https://richgophishapi.azurewebsites.net/). Sometimes it goes off when azure is updating App Service .
