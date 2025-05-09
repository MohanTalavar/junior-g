# Junior G 🌱

A full-stack preschool management web application designed to streamline daily operations for **administrators, teachers, and parents**.

## 🛠️ Tech Stack

* **Frontend**: React (Vite), TypeScript, Tailwind CSS, Redux Toolkit
* **Backend**: Java 17, Spring Boot, Spring Security (JWT), JPA/Hibernate
* **Database**: MySQL (AWS RDS)
* **Authentication**: JWT-based role-specific login (Admin, Teacher, Parent)
* **DevOps / Cloud**: AWS S3 (frontend hosting), EC2 (backend deployment), CloudFront, Route 53

## 🔐 Features

* 🔑 Secure authentication with JWT (Admin, Teacher, Parent roles)
* 🧾 Admission enquiry form with email acknowledgment and admin notifications
* 🧑‍🏫 Teacher dashboard for managing courses, students, and communication
* 📊 Admin panel with student/teacher management and course assignment
* 📁 File and image support for various assets (hero banners, etc.)

## 🌐 Live Demo

👉 [https://juniorg.site](https://juniorg.site)

## 📦 Folder Structure

```bash
junior-g/
├── backend/              # Spring Boot API
│   ├── src/main/java/
│   └── resources/
├── frontend/             # React frontend (Vite)
│   ├── src/
│   └── public/
└── README.md
```

## 🚀 Deployment

* **Frontend** hosted on AWS S3 + CloudFront with a custom domain via Route 53
* **Backend** hosted on AWS EC2 (Ubuntu) with MySQL via AWS RDS
* Environment configurations managed via AWS Parameter Store

## 🧠 Skills Demonstrated

* Clean and layered backend architecture (Controller → Service → Repo)
* Frontend state management using Redux Toolkit
* Secure credential management and configuration loading
* Cloud deployment and networking (S3, EC2, Route 53, CloudFront)
* Logging with Logback + file-based rotation
