# MovieTheater Project – README

## Overview

This project is a **Cinema Management System** called "MovieTheater" designed as a full-stack application with modular separation between **Client**, **Server**, and **Shared Entities**. It allows customers to browse, filter, and purchase movie tickets (in-cinema or at home), and provides content managers, branch managers, and network administrators with tools to manage movies, schedules, pricing, reports, and complaints.

Built using **Java**, **JavaFX**, and **OCSF (Open Client-Server Framework)**, this system supports multiple simultaneous users, role-based interfaces, and persistent data storage with a database.

This project was developed over 3 months as part of a Software Engineering academic course, by a team of 6 students working collaboratively.and this repository represents my personal version of the code with my own improvements and updates.

---

## Technologies Used

* Java 17+
* JavaFX (GUI)
* OCSF – Open Client-Server Framework
* Maven (multi-module project)
* MySQL (or Hibernate for ORM)
* Design Patterns: Mediator, DTO, MVC

---

## Project Structure

```
MovieTheater_Project_Improved/
├── client/            # JavaFX UI client
│   └── src/main/java/il/cshaifasweng/OCSFMediatorExample/client/
├── server/            # Server-side application
│   └── src/main/java/il/cshaifasweng/OCSFMediatorExample/server/
├── entities/          # Shared DTOs and domain models
│   └── src/main/java/il/cshaifasweng/OCSFMediatorExample/entities/
├── pom.xml            # Parent Maven file
└── README.md
```

---

## Features

### For Customers:

* Login and view personalized interface
* Browse movie catalog with details (name, cast, producer, synopsis, image)
* Filter movies by genre, branch, or date
* View upcoming releases and home packages
* Purchase tickets
* View hall map and available seats
* Receive confirmation and reminder
* Purchase home-viewing links
* Time-limited streaming
* Email reminder before start
* Purchase card packages 
* View personal area: purchase history, cancellations, complaints.

### For Content Manager:

* Add/remove movies and home packages
* Set screening times
* Update ticket pricing
* Notify cardholders about new movies

### For Admins:

* View system-wide reports (monthly sales, complaints)
* View per-branch reports

### For Customer Service:

* Handle cancellations and refunds
* Respond to user complaints within 24 hours

---

## How to Run

1. **Install Requirements:**

   * Java 17+
   * Maven 3+
   * MySQL server (if using Hibernate)

2. **Build Project:**

   ```bash
   mvn clean install
   ```

3. **Run Server:**

   ```bash
   cd server
   mvn exec:java
   ```

4. **Run Client:**

   ```bash
   cd client
   mvn javafx:run
   ```

> Ensure the server is running before launching the client.

---

## Modules & Architecture

See [`docs/ARCHITECTURE.md`](docs/ARCHITECTURE.md) for a detailed description of:

* Module relationships
* Client-Server message flow
* Entity usage between layers

---

##  Full Services List

See [`docs/SERVICES.md`](docs/SERVICES.md) for a breakdown of all business flows:

* Ticket purchasing
* Home streaming packages
* Reports & complaint handling
* Role-based permissions

---

##  Developer Notes

* Modular design allows for easy extension and testing
* EventBus pattern is used to handle in-client communication between screens
* DTOs are passed between client/server to ensure loose coupling
* Unit test templates are found in the `server/src/test` directory

---

## Future Improvements

* Authentication and user management
* RESTful APIs for external access
* Full database integration with ORM
* Dockerization and CI/CD setup

---

## Contact / Contribution

Want to contribute or ask a question?
Feel free to open issues or contact the maintainer: [GitHub Profile](https://github.com/Demaao)
