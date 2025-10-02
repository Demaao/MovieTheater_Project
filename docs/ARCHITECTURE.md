# System Architecture – MovieTheater Project

##  Modular Structure

The system is divided into three Maven modules:

- **Client** (`client/`)  
  - JavaFX UI application for customer interaction
  - Uses EventBus (Mediator Pattern) for internal communication
  - Sends requests to the server using shared `Request`/`Response` objects

- **Server** (`server/`)  
  - OCSF-based server that receives and handles requests
  - Manages data logic, communication, and persistence
  - Responds to the client with appropriate data

- **Entities** (`entities/`)  
  - Shared data models (DTOs) used by both client and server
  - Includes domain classes such as `Movie`, `Screening`, `Branch`, `Customer`, etc.
  - Also includes all request/response wrapper classes

---

##  Client–Server Communication Flow

1. **Client sends request**
   - For example: `GetMoviesRequest`, `BookSeatsRequest`
2. **Server processes**
   - Handles the request logic (e.g. retrieves from DB)
3. **Server sends response**
   - Response object (e.g. `GetMoviesResponse`) sent back
4. **Client updates UI**
   - UI reflects the data received using EventBus

---

## Persistence Layer

- Data is persisted using Hibernate / SQL backend (MySQL)
- Entity relationships are mapped using annotations (e.g. `@ManyToOne`)
- All business logic remains server-side for consistency and security

---

## Design Patterns Used

- **Mediator** (EventBus): decouples UI components
- **DTO (Data Transfer Object)**: all `Request` / `Response` types
- **MVC**: View (FXML), Controller (JavaFX), Model (Entities)

---

##  Access Control & Roles

Each user has a defined role that determines the visible UI and available actions:
- Customer, Content Manager, Branch Manager, Network Admin, Customer Service

---

## System Scalability Notes

- Easy to replace OCSF with REST or WebSockets
- Entities module ensures type-safe communication
- Can be containerized and deployed across environments
