# Junior G 👉 [https://juniorg.site](https://juniorg.site)

A full-stack preschool management web application designed to streamline daily operations for **administrators, teachers, and parents**.

---

## 🛠️ Tech Stack

* **Frontend**: React (Vite), TypeScript, Tailwind CSS, Redux Toolkit, ShadCN
* **Backend**: Java 17, Spring Boot (Microservices), Spring Security (JWT), JPA/Hibernate
* **Database**: MySQL (AWS RDS)
* **Authentication**: JWT-based role-specific login (Admin, Teacher, Parent)
* **DevOps / Cloud**: AWS S3, EC2, CloudFront, Route 53

---

## 🔐 Features

* 🔑 Secure authentication with JWT (Admin, Teacher, Parent roles)
* 🧾 Admission enquiry form with email acknowledgment and admin notifications
* 🧑‍🏫 Teacher dashboard for managing courses, students, and communication
* 📊 Admin panel with student/teacher management and course assignment
* 📁 File and image support for various assets (hero banners, etc.)

---

## 🧩 Backend Microservices

The backend is designed using a **modular microservice-based architecture** for better scalability and maintainability.

### 🔹 juniorg-core-service
* Core business logic and workflows
* Authentication & authorization (JWT)
* Admission enquiries
* Admin, Teacher, Parent modules
* Database access via JPA/Hibernate

### 🔹 juniorg-notification-service
* Dedicated notification microservice
* Handles email acknowledgments and admin alerts
* Decoupled from core business logic
* Runs independently on a separate port

This separation ensures **fault isolation**, **clean responsibilities**, and easier future scaling.

---

## 📦 Folder Structure

```bash
junior-g/
├── backend/
│   ├── juniorg-core-service/          # Core backend microservice
│   └── juniorg-notification-service/  # Notification microservice
├── frontend/                          # React frontend (Vite)
│   ├── src/
│   └── public/
└── README.md
