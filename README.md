# EduMentor - Free Mentorship Platform  

![License](https://img.shields.io/badge/license-MIT-blue.svg)  
![Spring Boot](https://img.shields.io/badge/backend-SpringBoot-green)  
![React](https://img.shields.io/badge/frontend-React-blue)  
![Bootstrap](https://img.shields.io/badge/UI-Bootstrap-purple)  
![PostgreSQL](https://img.shields.io/badge/database-PostgreSQL-orange)  
![Docker](https://img.shields.io/badge/container-Docker-blue)  
![AWS](https://img.shields.io/badge/hosted_on-AWS-yellow)  
![Stripe](https://img.shields.io/badge/payments-Stripe-lightblue)  

EduMentor is a **web application** that connects **mentors** and **mentees** for **free mentorship sessions**. Mentors can **volunteer** to offer guidance, and mentees can **register** to receive mentorship. The platform also supports **donations via Stripe** to help sustain the initiative.  

## ✨ Features  
- **Mentorship Matching**  
  - Mentors can **volunteer** and provide mentorship sessions.  
  - **Admins match mentors** to available slots for sessions. 
  - Mentees register to partake in a session. 
- **Responsive & Beautiful UI**  
  - Built with **React.js** and **Bootstrap**, designed with a fun feel.  
- **Secure Payment Integration**  
  - **Stripe** integration allows users to **donate** and support the platform.  
- **Modern Tech Stack**  
  - **Spring Boot** for backend API development.  
  - **PostgreSQL** as the database.  
  - **Dockerized & Hosted on AWS**.  

---

## 🚀 Installation  

### 1️⃣ Prerequisites  
- **Java 17+** (for Spring Boot backend)  
- **Node.js & npm** (for frontend)  
- **PostgreSQL** (for database)  
- **Docker** (if running in a container)  

### 2️⃣ Clone the Repository  
```sh
git clone https://github.com/Norhan-salem/EduMentor.git
cd EduMentor

```

----------

### 3️⃣ Backend Setup (Spring Boot)

#### Create a PostgreSQL database

Make sure PostgreSQL is running and create a database:

```sql
CREATE DATABASE edumentor_db;

```

#### Set up environment variables

Create an `.env` file in the backend directory (email me @ norhansalem581@gmail.com to get the values) with:

```ini
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/edumentor_db
SPRING_DATASOURCE_USERNAME=db_username
SPRING_DATASOURCE_PASSWORD=db_password
STRIPE_SECRET_KEY=stripe_secret_key

```

#### Run the backend server

```sh
./mvnw spring-boot:run

```

The backend should now be running at **[http://localhost:8080](http://localhost:8080/)**.

----------

### 4️⃣ Frontend Setup (React.js + Bootstrap)

#### Navigate to the frontend directory:

```sh
cd View/edu-mentor

```

#### Install dependencies:

```sh
npm install

```

#### Start the frontend server:

```sh
npm start

```

The frontend should now be accessible at **[http://localhost:3000](http://localhost:3000/)**.

----------

### 5️⃣ Running with Docker

To run the application using Docker, use:

```sh
docker-compose up --build

```

----------

## 📌 Usage

1.  **Mentors sign up** and set availability.
2.  **Mentees sign up** and request sessions.
3.  **Admins match mentors** to mentees and manage session scheduling.
4.  Users can **donate via Stripe** to support the platform.

----------

## 🛠 Contribution

We welcome contributions! Follow these steps:

1.  **Fork the repository**.
2.  **Create a new branch**:
    
    ```sh
    git checkout -b feature-name
    
    ```
    
3.  **Make your changes and commit them**:
    
    ```sh
    git commit -m "Added a new feature"
    
    ```
    
4.  **Push to your fork and submit a pull request**.

----------

## 🙌 Acknowledgments

-   **Spring Boot** for the backend
-   **React.js & Bootstrap** for frontend development
-   **PostgreSQL** for database storage
-   **Docker & AWS** for deployment
-   **Stripe** for payment processing

----------

🚀 **Empower Learning with EduMentor!**
