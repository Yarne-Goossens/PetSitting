# PETSITTING

<p align="center">
  Connecting Pets and Care with Trust and Ease
</p>

<p align="center">
  <img src="https://img.shields.io/github/last-commit/Yarne-Goossens/PetSitting"/>
  <img src="https://img.shields.io/github/languages/top/Yarne-Goossens/PetSitting">
  <img src="https://img.shields.io/github/languages/count/Yarne-Goossens/PetSitting">
</p>

<p align="center">
  Built with the tools and technologies:
</p>

<p align="center">
  <img alt="Spring" src="https://img.shields.io/badge/Spring-000000.svg?style=flat&amp;logo=Spring&amp;logoColor=white">
  <img alt="XML" src="https://img.shields.io/badge/XML-005FAD.svg?style=flat&amp;logo=XML&amp;logoColor=white" class="inline-block mx-1" style="margin: 0px 2px;">
</p>

---

## Table of Contents

* [Overview](#overview)
* [Getting Started](#getting-started)
    * [Prerequisites](#prerequisites)
    * [Installation](#installation)
* [Usage](#usage)
* [Testing](#testing)

## Overview

PetSitting is a comprehensive **backend framework** designed to accelerate the development of pet sitting platforms. Built on **Spring Boot**, it offers a modular architecture with **RESTful APIs**, **security**, and detailed **API documentation**, enabling developers to focus on core business logic.

This project simplifies building secure, scalable pet sitting applications. The core features include:

* **Security & Authentication**: JWT-based security with role management ensures secure user access.
* **Modular Services**: Clear separation of concerns with dedicated services for pets, owners, playdates, and reviews.
* **API Documentation**: Seamless integration with **OpenAPI** for comprehensive API docs.
* **Event-Driven Architecture**: Real-time updates for owner registration, reviews, and ratings.
* **Data Management**: Efficient repositories for managing pets, sitters, and scheduling data.

## Getting Started

### Prerequisites

This project requires the following dependencies:

* **Programming Language**: Java
* **Package Manager**: Maven

### Installation

Build PetSitting from the source and install dependencies:

1.  Clone the repository:
    ```bash
    git clone https://github.com/Yarne-Goossens/PetSitting
    ```
2.  Navigate to the project directory:
    ```bash
    cd PetSitting
    ```
3.  Install the dependencies:

    Using maven:
    ```bash
    mvn install
    ```

## Usage

Using maven:

```bash
mvn spring-boot:run
```

Once the application is running, you can explore and call the API via Swagger UI:

http://localhost:8080/swagger-ui/index.html

## Testing

Petsitting uses the **Mockito framework**. run the test suite with:

Using maven:

```bash
mvn test
```
