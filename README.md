# ds0122
This demo Java application simulates a construction tool rental system.
The app provides a REST api for listing the tools available for rent and the list of charges. It also provides a checkout endpoint that will generate a rental agreement based on the tool rented and the number of rental days.

Tech stack:
Spring Boot
H2 In-memory database

Get all tools:
GET http://localhost:8080/tools

Get a tool:
GET http://localhost:8080/tools/{id}

Get all rental charges:
GET http://localhost:8080/rental-charges

Get a rental charge:
GET http://localhost:8080/rental-charges/{id}

Submit a checkout:
POST http://localhost:8080/checkout
Request body:
  {
    "toolCode": [tool code],
    "rentalDays: [number of rental days],
    "discountPercent": [discount percent 0 -100],
    "checkoutDate": [yyyy-mm-dd]
  }

Unit test are included with the primary test suite Ds0122ApplicationTests executing tests 1 - 6
