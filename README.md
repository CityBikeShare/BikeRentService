### BikeRentService

[![Build Status](https://travis-ci.org/CityBikeShare/BikeRentService.svg?branch=master)](https://travis-ci.org/CityBikeShare/BikeRentService)

- Service allows a user to rent a bike, or cancel rent later.

#### Requests on kubernetes
    GET
        - 159.122.177.235:30001/sources/bikerent/                      ### Get all bikes ever rented
        - 159.122.177.235:30001/sources/bikerent/{id}                  ### Get bike rented by id

    POST
        - 159.122.177.235:30001/sources/bikerent/rent                  ### Rent a bike [@QueryParam bikeId, @QueryParam userid]

    PUT
        - 159.122.177.235:30001/sources/bikerent/return/{bikeid}       ### Return rented bike with given id
        
    DELETE
        - 159.122.177.235:30001/sources/bikerent/{id}                  ### Delete bike record with bikeRent id 
