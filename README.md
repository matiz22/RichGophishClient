
# RichGophishClient

RichGophishClient (not yet 🤥) is a Kotlin Multiplatform (KMM) application designed for all platforms without web support. It features a simple Ktor server facilitating Gophish multi-tenancy. This application enables users to manage multiple credentials within a SQLite database. The shared folder contains model repositories and other components crucial for the Compose Multiplatform app. Additionally, it integrates an AI-powered email generation system via the Ollama API, enhancing email campaign effectiveness and personalization.

## Features

Features
- Multi-Tenancy: RichGophishClient allows for the efficient management of multiple Gophish instances within a single application.
- AI-Powered Email Generation: The integration of the Ollama API enables the generation of personalized and effective emails, enhancing user experience and campaign effectiveness. IN PROGRESS
- Some ui charts from compose library :)
## Installation

To try out RichGophishClient, follow these steps:
- Clone repo
```bash
git clone https://github.com/matiz22/RichGophishClient.git
```
- Navigate to the project directory:
```bash
cd RichGophishClient
```
- Build the server JAR:
```bash
./gradlew :server:build
```
- Set up the server environment:

The server requires an environment variable to pass the API_KEY, which is used for authorization. Ensure you have the API key ready.

Example:
```bash
export API_KEY=your_api_key_here
```
- Ensure access to a Gophish instance:

RichGophishClient requires access to a Gophish instance to display data. Ensure you have access to a Gophish instance and know its URL.

- Configure client properties
Create or modify the local.properties file in the client directory. This file should contain the necessary configurations for the client to connect to the server and the Ollama API.

Example local.properties:
```gradlew
main.api.host=192.168.0.26:8080      # Location of the server JAR
main.api.key=test3                     # API_KEY obtained from environment
ollama.api.host=192.168.0.26:11434     # Location of the Ollama API
```
- Then build composeApp for your machine or use android studio to run. You can also run server through Android studio.

Example
```bash
./gradlew :composeApp:run
```
#### Installation files will be in releases on github in the future when app will be mostly finished
## Tech Stack

### Core Libraries

- [**Decompose:**](https://github.com/arkivanov/Decompose) Provides utilities for composing reusable UI components.
- [**Dotenv Kotlin:**](https://github.com/cdimascio/dotenv-kotlin) Loads environment variables from a file.
- [**Exposed:**](https://github.com/JetBrains/Exposed) ORM library for Kotlin.

### Persistence

- **H2:** Embedded relational database.


### Security

- **Jbcrypt:** Password hashing library.


### Dependency Management

- **Koin** Dependency injection framework for Kotlin.


### Useful Libraries

- [**Libres:**](https://github.com/Skeptick/libres) Collection of utilities for Kotlin projects.
- [**Yshrsmz-BuildKonfig:**](https://github.com/yshrsmz/BuildKonfig) Library for managing project configurations.
