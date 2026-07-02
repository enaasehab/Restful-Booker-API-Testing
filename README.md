# Restful-Booker API Testing Framework 🚀

## Project Overview
A professional automated API testing framework built for the [Restful-Booker](https://restful-booker.herokuapp.com) API, covering both manual and automated test scenarios across authentication, booking management, and end-to-end workflows.

## Team
| Member | Responsibilities |
|--------|----------------|
| Enas Ehab | TC_001 – TC_011 (Auth + Get/Create Booking) |
| Samah Sameh | TC_012 – TC_022 (Update/Delete + E2E) |

## Tech Stack
| Tool | Purpose |
|------|---------|
| Java 21 | Programming language |
| REST Assured | API automation library |
| TestNG | Test framework |
| Maven | Build and dependency management |
| Allure | Test reporting |
| Lombok | Reduce boilerplate code |
| Jackson | JSON serialization/deserialization |
| Log4j2 | Logging |

---

## Project Structure
```text
src/
├── main/java/com/restfulBooker/
│   ├── apisManager/
│   │   ├── apis/          # HTTP request methods
│   │   ├── base/          # Shared request specification
│   │   ├── data/          # API routes constants
│   │   ├── models/        # POJO classes
│   │   └── steps/         # Test data preparation
│   ├── dataReader/        # Properties & JSON readers
│   ├── listeners/         # TestNG listeners
│   ├── logs/              # Logging manager
│   ├── report/            # Allure report manager
│   └── utils/             # Utility classes
└── test/java/com/restfulBooker/tests/
├── AuthTest.java           # TC_001 – TC_003
├── BookingTest.java        # TC_004 – TC_011
├── UpdateBookingTests.java # TC_013 – TC_015
├── PartiallyUpdateTests.java # TC_016 – TC_017
├── DeleteBookingTests.java # TC_018 – TC_019
├── HealthCheckTests.java   # TC_020
└── E2ETests.java           # TC_021 – TC_022
```
---

## Test Coverage
| TC ID | Scenario | Category | Status |
|-------|----------|----------|--------|
| TC_001 | Create token with valid credentials | Positive | ✅ Pass |
| TC_002 | Create token with invalid password | Negative | ❌ Fail — Bug_RB_001 |
| TC_003 | Create token with missing username | Negative | ❌ Fail — Bug_RB_002 |
| TC_004 | Get all bookings | Positive | ✅ Pass |
| TC_005 | Get booking by valid ID | Positive | ✅ Pass |
| TC_006 | Filter bookings by name | Positive | ✅ Pass |
| TC_007 | Get booking with non-existent ID | Negative | ✅ Pass |
| TC_008 | Get booking with invalid non-numeric ID | Negative | ✅ Pass |
| TC_009 | Create booking with valid data | Positive | ✅ Pass |
| TC_010 | Create booking with missing firstname | Negative | ❌ Fail — Bug_RB_003 |
| TC_011 | Create booking with negative price | Negative | ❌ Fail — Bug_RB_004 |
| TC_012 | Create booking with invalid dates | Negative | ❌ Fail — Bug_RB_005 |
| TC_013 | Full update booking with valid token | Positive | ✅ Pass |
| TC_014 | Update booking without token | Negative | ✅ Pass |
| TC_015 | Update booking with invalid token | Negative | ✅ Pass |
| TC_016 | Partial update booking with valid token | Positive | ✅ Pass |
| TC_017 | Partial update with empty body | Boundary | ✅ Pass |
| TC_018 | Delete booking with valid token | Positive | ✅ Pass |
| TC_019 | Delete booking without token | Negative | ✅ Pass |
| TC_020 | Health check /ping endpoint | Positive | ✅ Pass |
| TC_021 | E2E: Full booking lifecycle with PUT | E2E | ✅ Pass |
| TC_022 | E2E: Booking lifecycle with PATCH | E2E | ✅ Pass |

## Bugs Found
| Bug ID | Description | Expected | Actual |
|--------|-------------|----------|--------|
| Bug_RB_001 | Failed auth returns 200 instead of 401 | 401 Unauthorized | 200 OK |
| Bug_RB_002 | Missing username returns 200 instead of 400 | 400 Bad Request | 200 OK |
| Bug_RB_003 | Missing mandatory field returns 500 instead of 400 | 400 Bad Request | 500 Internal Server Error |
| Bug_RB_004 | Negative price accepted without validation | 400 Bad Request | 200 OK |
| Bug_RB_005 | Invalid date range accepted without validation | 400 Bad Request | 200 OK |

## How to Run
### Prerequisites
- Java 21
- Maven 3.9+

### Run all tests
```bash
mvn clean test
```

### Run specific test class
```bash
mvn clean test -Dtest=AuthTest
```

## Allure Report
After running tests, the Allure report opens automatically in your browser.
