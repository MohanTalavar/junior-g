# Changelog — Junior-G International Pre School Website

## v1.4.1 — February 1, 2026

### 🧱 Backend Architecture Enhancements
- Refactored backend into a **microservice-based architecture**
  - Extracted core business logic into **juniorg-core-service**
  - Introduced **juniorg-notification-service** as a dedicated notification microservice
- Improved separation of concerns between business logic and notification handling
- Enabled independent build, deployment, and scaling of backend services

### 🔔 Notification Service Improvements
- Centralized email notification logic into a standalone service
- Improved reliability and maintainability of admission enquiry acknowledgments
- Prepared foundation for future async/event-driven notifications

### 🚀 Deployment & Infrastructure
- Deployed backend services as **independent Spring Boot JARs** on AWS EC2
- Improved backend deployment consistency and service isolation
- Minor configuration cleanups for environment separation

### 🧹 Maintenance
- Updated project structure and internal documentation
- Updated README to reflect microservice-based backend design
- General refactoring and cleanup after architectural changes

---

## v1.4.0 — November 18, 2025

- Introduced full **Attendance Management Module**
  - Course-wise attendance submission
  - Student records page with search & pagination
  - Edit remark modal for quick updates
- Added **Weekly & Monthly automated attendance reports**
  - Implemented Spring Scheduler for automated report generation & delivery
- Added **Java Records** for cleaner DTO structures
- Improved API structure for attendance workflows
- UI enhancements:
  - "Learn More" section now clickable
  - New **Get in Touch** modal with Call Us & Admission Enquiry
- Improved backend consistency and pagination logic
- Minor UI adjustments & AWS deployment stability improvements
- Updated internal documentation for new flows

## v1.3.3 — October 26, 2025

- Added JUnit & Mockito-based unit tests for core backend services
- Optimized service layer for improved performance and readability
- Updated CORS policies with stricter domain access rules
- Improved API error messages and logging consistency
- Minor UI/UX alignment tweaks
- Updated internal documentation and changelog references

## v1.3.2 — June 21, 2025

- Added Social Media floaters
- Added vector images to course cards
- Updated footer and keeping track of app version

## v1.3.1 — June 16, 2025

- Font style updates
- Sidebar enhancement (Home tab added, active tab indiacator)

## v1.3.0 — June 2025

- Focus on UI/UX enhancements
- Sidebar now closes automatically

## v1.2.0 — May 2025

- Added full CRUD operations for Junior-G staff (teachers, students, users)
- Admin role management and access controls

## v1.1.0 — May 2025

- Finalized UI for parent view
- Course listing, contact form, basic pages

## v1.0.0 — April 2025

- Initial version of the website
- Basic structure, routing, landing page setup
