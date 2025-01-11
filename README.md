# Renault Garage Management System

This microservice manages Renault's network of affiliated garages, their vehicles, and accessories.

## Features

### 1. Garage Management
- Create, update, and delete garages
- Retrieve specific garage by ID
- List all garages with pagination and sorting
- Manage opening hours for each day of the week

### 2. Vehicle Management
- Add, update, and delete vehicles in garages
- List vehicles by garage
- Search vehicles across multiple garages
- Enforce maximum vehicle limit (50) per garage

### 3. Accessory Management
- Add, update, and delete accessories for vehicles
- List accessories by vehicle
- Categorize accessories by type

### 4. Search Capabilities
- Find garages by vehicle type
- Search garages by available accessories
- Filter and sort results

## API Endpoints

### Garage Endpoints

```
GET /api/garages
- List all garages (paginated)
- Query params: page, size, sort

GET /api/garages/{id}
- Get specific garage

POST /api/garages
- Create new garage
- Required fields: name, address, telephone, email, openingHours

---
{
  "name": "Garage Renault",
  "address": "1 Street",
  "telephone": "0612344344678",
  "email": "garage@renault.com",
  "openingHours": {
    "MONDAY": [
      { "startTime": "08:00", "endTime": "18:00" }
    ],
    "TUESDAY": [
      { "startTime": "08:00", "endTime": "18:00" }
    ],
    "WEDNESDAY": [
      { "startTime": "08:00", "endTime": "18:00" }
    ],
    "THURSDAY": [
      { "startTime": "08:00", "endTime": "18:00" }
    ],
    "FRIDAY": [
      { "startTime": "08:00", "endTime": "18:00" }
    ],
    "SATURDAY": [
      { "startTime": "10:00", "endTime": "16:00" }
    ],
    "SUNDAY": [] 
  }
}

---

PUT /api/garages/{id}
- Update existing garage

DELETE /api/garages/{id}
- Delete garage

search by brand :

http://localhost:8080/api/garages/search/by-brand?brand=Renault Clio
```

### Vehicle Endpoints

```
GET /api/garages/{garageId}/vehicles
- List all vehicles in a garage

POST /api/garages/{garageId}/vehicles
- Add vehicle to garage
- Required fields: brand, manufacturingYear, fuelType

---

{
  "brand": "Renault Clio",
  "manufacturingYear": 2022,
  "fuelType": "GASOLINE"
}

---

PUT /api/garages/{garageId}/vehicles/{vehicleId}
- Update vehicle

DELETE /api/garages/{garageId}/vehicles/{vehicleId}
- Remove vehicle from garage
```

### Accessory Endpoints

```
GET /api/vehicles/{vehicleId}/accessories
- List all accessories for a vehicle

POST /api/vehicles/{vehicleId}/accessories
- Add accessory to vehicle
- Required fields: name, description, price, type

---

{
  "name": "Sunroof",
  "description": "Electric sliding sunroof",
  "price": 1200.50,
  "type": "INTERIOR",
  "vehicleId": 1
}

---

PUT /api/vehicles/{vehicleId}/accessories/{accessoryId}
- Update accessory

---

{
  "name": "Sunroof v2",
  "description": "Electric sliding sunroof v2",
  "price": 1999,99,
  "type": "INTERIOR"
}

---

DELETE /api/vehicles/{vehicleId}/accessories/{accessoryId}
- Remove accessory
```

## Business Rules

1. **Garage Capacity**
    - Maximum 50 vehicles per garage
    - Enforced at the domain level

2. **Required Information**
    - Garage: name, address, telephone, email, opening hours
    - Vehicle: brand, manufacturing year, fuel type
    - Accessory: name, description, price, type

3. **Opening Hours**
    - Structured as Map<DayOfWeek, List<OpeningTime>>
    - Each OpeningTime contains start and end times

## Technical Stack

- Java 17
- Spring Boot 3.1.5
- Spring Data JPA
- H2 Database (for development)
- Lombok
- MapStruct
- Maven

## Running the Application

1. Clone the repository
2. Run `mvn clean install`
3. Start the application: `mvn spring-boot:run`
4. Access the API at `http://localhost:8080`
5. H2 Console available at `http://localhost:8080/h2-console`

## Testing

The project includes:
- Unit tests for services and domain logic
- Integration tests for REST endpoints
- Repository tests for data access

Run tests with: `mvn test`