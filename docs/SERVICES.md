#  Services – MovieTheater System

This document details all the core services supported by the "MovieTheater" cinema management system.

---

##  1. Login and Role-Based Interface

Upon login, users are presented with interfaces tailored to their role:
- Customer
- Content Manager
- Branch Manager
- Network Manager
- Customer Service Representative

---

## 2. Browse & Search Movies

- View a list of movies with:
  - Title (Hebrew and English)
  - Producer
  - Main actors
  - Summary
  - Movie image/poster
  - Navigate (scroll) through available movies
  - Search movies by:
   - Genre
   - Cinema branch
   - Screening date
 - View:
  - "Coming Soon" movies
  - Home-viewing packages
- View details of selected movie or home package

---

## 3. Content Management (Content Manager Only)

- Add / Remove in-theater movies
- Add / Remove home-viewing packages
- Set or update screening schedules (date, time, hall)
- Set or update pricing
- Send notifications to cardholders about new movies

---

## 4. Purchase In-Theater Tickets

- Select movie → branch → date/time
- View hall seating map with:
  - Hall number, movie, seats layout
- Choose seats and confirm booking
- System saves booking details
- Confirmation message sent to customer

---

## 5. Purchase Home-Viewing Links

- Purchase a package for watching a movie at home
- Movie link is active only during specified time window
- Confirmation email includes:
  - Movie name, viewing time range, link
- System sends reminder one hour before link activation
- Booking details are saved

---

## 6. Purchase Ticket Cards

- Purchase a prepaid card ( 20 tickets)
- View current balance
- Card purchases are stored
- No refund available for ticket cards

---

## 7. Cancel Purchases

- Cancel cinema ticket:
  - Refund depends on time of cancellation
- Cancel home-viewing package:
  - Refund based on cancellation time

---

## 8. Handle Complaints

- Customer Service handles complaints
- May issue refunds or compensation
- Complaints are handled within 24 hours

---

## 9. Reporting

### Accessible by:
- Network Manager → Full system reports
- Branch Manager → Reports per branch

### Types of Reports:
- Monthly movie ticket sales:
  - Per day
  - Per branch
  - For entire network
- Sales of cards and home packages
- Complaints report:
  - Number and type of complaints per cinema
  - Visual histogram by month

---

## 10. Non-Functional Requirements

- Server and client run as separate applications on different machines (JAR-based)
- Supports multiple
