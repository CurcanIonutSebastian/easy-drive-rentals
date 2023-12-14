# EasyDrive Rentals

**Overview:**

This is a demonstration backend project designed to showcase functionalities where users have the ability to create a
personalized profile. Within this profile, users can not only save information but also bookmark their favorite cars for
easy access. Additionally, the platform facilitates the process of renting vehicles, providing a seamless and
user-friendly experience for individuals looking to explore and utilize various car rental options. The project serves
as a practical example of backend development, highlighting features essential for a comprehensive user experience in
the realm of profiles, vehicle preferences, and rental services.

**Key Features:**

- Client management
- Rental management
- Car management

**Technologies used:**

- Java 17
- SpringBoot 3.1.5
- PostgreSQL
- Maven
- Mockito
- Lombok
- h2database

# I. Clients

## 1. Create client:

- Description: Create a new client
- Endpoint: `/clients`
- Method: `POST`
- Request body:

```json
{
  "firstName": "John",
  "lastName": "Doe",
  "clientDetailsDTO": {
    "phoneNumber": "0746795123",
    "email": "john@gmail.com",
    "country": "Romania",
    "city": "Bucharest",
    "street": "Principal street",
    "block": "E2",
    "stair": "C",
    "floor": 2,
    "apartment": 10
  }
}
```

## 2. Add car to favorite:

- Description: Save a favorite car by id and by user id
- Endpoint: `/clients/{clientId}/favorite-cars`
- Method: `POST`
- Request: `/clients/1/favorite-cars`

```json
{
  "carId": 1
}
```

## 3. Get client by id:

- Description: Get a list at clients by client id
- Endpoint: `/clients/{clientId}`
- Method: `GET`
- Request: `/clients/1`
- Response:

```json
{
  "id": 1,
  "firstName": "John",
  "lastName": "Doe",
  "clientDetailsDTO": {
    "phoneNumber": "0746795123",
    "email": "john@gmail.com",
    "country": "Romania",
    "city": "Bucharest",
    "street": "Principal street",
    "block": "E2",
    "stair": "C",
    "floor": 2,
    "apartment": 10
  }
}
```

## 4. Get all client:

- Description: Get a list of clients clients
- Endpoint: `/clients`
- Method: `GET`
- Request: `/clients`
- Response:

```json
[
  {
    "id": 1,
    "firstName": "John",
    "lastName": "Doe",
    "clientDetailsDTO": {
      "phoneNumber": "0746795123",
      "email": "john@gmail.com",
      "country": "Romania",
      "city": "Bucharest",
      "street": "Principal street",
      "block": "E2",
      "stair": "C",
      "floor": 2,
      "apartment": 10
    }
  },
  {
    "id": 2,
    "firstName": "Olivia",
    "lastName": "King",
    "clientDetailsDTO": {
      "id": 12,
      "phoneNumber": "723456282",
      "email": "king@gmail.com",
      "country": "United Kingdom",
      "city": "London",
      "street": "Principal Street",
      "block": "",
      "stair": "",
      "floor": 0,
      "apartment": 0
    }
  }
]
```

## 5. Get all favorite cars by client id:

- Description: Get a list of favorite cars by client id
- Endpoint: `/clients/{clientId}/favorite-cars`
- Method: `GET`
- Request: `/clients/1/favorite-cars`
- Response:

```json
{
  "id": 1,
  "firstName": "John",
  "lastName": "Doe",
  "favoriteCars": [
    {
      "id": 1,
      "brand": "BMW",
      "model": "3 series",
      "capacity": 1996,
      "productYear": 2008,
      "pricePerDay": 100.3,
      "carStatus": "RENTED"
    },
    {
      "id": 2,
      "brand": "BMW",
      "model": "5 series",
      "capacity": 1996,
      "productYear": 2010,
      "pricePerDay": 235.99,
      "carStatus": "RENTED"
    },
    {
      "id": 3,
      "brand": "Audi",
      "model": "A4",
      "capacity": 2994,
      "productYear": 2014,
      "pricePerDay": 350.0,
      "carStatus": "AVAILABLE"
    }
  ]
}
```

## 6. Get history by client id:

- Description: Get a list of history and rentals by client id
- Endpoint: `/clients/{clientId}/client-histories`
- Method: `GET`
- Request: `/clients/1/client-histories`
- Response:

```json
[
  {
    "id": 4,
    "carId": 12,
    "startRentalDate": "2023-12-11",
    "endRentalDate": "2023-12-11",
    "returnedCar": "2023-12-11",
    "userHistoryStatus": "AT_TIME",
    "totalPrice": 320.0
  }
]
```

## 7. Update client by id:

- Description: Update client and client details by client id
- Endpoint: `/clients/{clientId}`
- Method: `GET`
- Request: `/clients/1`

```json
  {
  "id": 1,
  "firstName": "John",
  "lastName": "Doe",
  "clientDetailsDTO": {
    "phoneNumber": "0746795123",
    "email": "john@gmail.com",
    "country": "Romania",
    "city": "Bucharest",
    "street": "Principal street",
    "block": "E2",
    "stair": "C",
    "floor": 2,
    "apartment": 10
  }
}
```

## 8. Delete client by id:

- Description: Delete a client by id
- Endpoint: `/clients/{clientId}`
- Method: `DELETE`
- Request: `/clients/1`
- Response: "Client deleted successfully!"

# II. Cars

## 1. Create cars:

- Description: Create a new car
- Endpoint: `/cars`
- Method: `POST`
- Request body:

```json
{
  "brand": "Hyundai",
  "model": "Elantra",
  "capacity": 2000,
  "productYear": 2015,
  "pricePerDay": 320.0
}
```

## 2. Get all cars:

- Description: Get a list of cars
- Endpoint: `/cars`
- Method: `POST`
- Response:

```json
[
  {
    "id": 1,
    "brand": "BMW",
    "model": "seria 3",
    "capacity": 1996,
    "productYear": 2008,
    "pricePerDay": 100.3,
    "carStatus": "RENTED"
  },
  {
    "id": 2,
    "brand": "BMW",
    "model": "seria 5",
    "capacity": 1996,
    "productYear": 2010,
    "pricePerDay": 235.99,
    "carStatus": "RENTED"
  }
]
```

## 3. Sort cars:

- Description: Get a list of cars by: brand, <= product year, <= price
- Endpoint: `/cars/search?brand= &year= &price= `
- Method: `GET`
- Request: `/cars/search?brand=bmw&year=2020&price=500`
- Response:

```json
[
  {
    "id": 1,
    "brand": "BMW",
    "model": "sera 3",
    "capacity": 1996,
    "productYear": 2008,
    "pricePerDay": 100.3,
    "carStatus": "RENTED"
  },
  {
    "id": 2,
    "brand": "BMW",
    "model": "seria 5",
    "capacity": 1996,
    "productYear": 2010,
    "pricePerDay": 235.99,
    "carStatus": "RENTED"
  },
  {
    "id": 4,
    "brand": "BMW",
    "model": "X5",
    "capacity": 3500,
    "productYear": 2018,
    "pricePerDay": 450.0,
    "carStatus": "AVAILABLE"
  }
]
```

# III. Rentals

## 1. Create rentals:

- Description: Create rental by client id and car id
- Endpoint: `/rentals/{clientId}/{carId}`
- Method: `POST`
- Request: `/rentals/{clientId}/{carId}`

```json
{
  "rentalDays": 6
}
```

- Response:

```json
{
  "id": 6,
  "startRentalDate": "2023-12-14",
  "endRentalDate": "2023-12-20",
  "totalPrice": 1920.0,
  "clientId": 6,
  "carId": 8
}
```

## 2. Get all rentals:

- Description: Get a list of rentals
- Endpoint: `/rentals`
- Method: `GET`
- Response:

```json
[
  {
    "id": 5,
    "startRentalDate": "2023-12-11",
    "endRentalDate": "2023-12-11",
    "totalPrice": 400.0,
    "clientId": 7,
    "carId": 11
  },
  {
    "id": 6,
    "startRentalDate": "2023-12-14",
    "endRentalDate": "2023-12-20",
    "totalPrice": 1920.0,
    "clientId": 6,
    "carId": 8
  }
]
```

## 3. Update rental done:

- Description: Update a rental done by rental id
- Endpoint: `/rentals/{rentalId}`
- Method: `PATCH`
- Request: `/rentals/2`
- Response: The car was returned!