# PROGRAMMING 2.3 SELF PROJECT ~ Research - Ihsan Hepsen
## **_!_ THIS PROJECT IMPLEMENTS THE RESEARCH TOPIC _!_**

## PROJECT OWNER INFORMATION
- **STUDENT NAME**: IHSAN HEPSEN
- **STUDENT EMAIL**: ihsan.hepsen@student.kdg.com
- **STUDENT ID**: 0145029-14

---

## How To Run

### **REQUIRED** JDK VERSION = **Java 16**.
#### Run the application
* Please make sure you have the project cloned in your machine
* Open the project location in your terminal
* Execute the following commands to run the application:
  * 1st: `./gradlew npm_run_build`
  * 2nd: `./gradlew bootRun`
  * 3rd: Navigate to [localhost:4506](http://localhost:4506) on your browser

### SERVER PORT
#### This project uses the Server Port 4506.

### Additional JS Libraries
- Flatpickr
- @popperjs/core
- SweetAlert

---

## PROJECT OVERVIEW

- In this project an airline, flight, and a passenger can be viewed. Flights and Passengers can be browsed and new
  flights or passengers can be added to the system. This system shows all the flights, passengers, and airlines that
  exists in its records(Database). Please see below sections to see the details of an entity class.

## DOMAIN

### DOMAIN BRIEF

- This project's domain is an Online Flight Browsing system where airlines, flights and passengers, and their details
  can be seen. In this project of Ihsan Hepsen you can add passengers or flights to the system. There are 3 domain
  classes in this project. The 2 main classes are Flight and Passenger classes. Airline class is more of an extra class
  to add more logic to the project domain.

## ENTITY CLASSES

### AIRLINE

- Airline class defines an airline company. The airline class defines the airline's name, airline's fleet size,
  airline's total number of destinations, and total current flights of that airline.

#### Attribute Detail

- **airlineName:** `String` ~ Airline name i.e. Qantas Airlines
- **fleetSize:** `int` ~  Airline fleet size(how many aircraft does that airline has)
- **numberOfDestinations:** `int` ~ Number of location that Airline flies to.
- **flights:** `List<Flight>`

### FLIGHT

- Flight class defines an airline's flight. Flight has an airline, flight number, flight type(Private or Commercial),
  departure, arrival, date, flight status(on time or delayed), and passengers on that flight.

#### Attribute Detail

- **airline:** `Airline` ~ Airline of the flight. i.e. TK77 belongs to Turkish Airlines. This field must be an `Airline`
  type to apply and maintain one-to-many relation.
- **flightNumber:** `String` ~ unique flight code to identify that particular flight.
- **flightType:** `FlightType` (Custom Enum Class) `PRIV, COMM` Private or Commercial. In this domain there are 2 flight
  types; private or commercial. Private flight are not ordinary flights that everyone can book (travelling with private
  jet etc.). Commercial flight is a normal flight type that pretty much every passenger can book.
- **date:** `LocalDate` ~ Scheduled departure date of the flight (YYYY-MM-DD) -no hours-.
- **flightStatus:** `boolean` ~ Flight status of departure. On time or delayed
- **departure:** `String` ~ City of departure.
- **arrival:** `String` ~ City of arrival.

### PASSENGER

- Passenger class defines a passenger of a flight. The passenger class has a name(full name), passenger's age, gender,
  passenger type(Transit or Non-transit passenger), and flights of that passenger currently has.

#### Attribute Detail

- **name:** `String` ~ Passenger's full name.
- **age:** `int` ~ Passenger age.
- **gender:** `Gender` ~ (Custom Enum) `MALE or FEMALE`
- **transitPassenger:** `boolean` ~ Demonstrates if a passenger is a transit passenger or a non-transit
  passenger. `true`: transit, `false`: non-transit.
- **flights:** `List<Flight>` ~ List of flight that passenger has.

## DOMAIN RELATIONS

### AIRLINE - FLIGHT Relation(one-to-many)

- There is a one-to-many relation between Airline and Flight classes. Every flight must have an Airline, and one airline
  can have multiple flights. Airline class also has a list of its flights. In this structure, every Flight class has a
  foreign key dependency on Airline class.

### FLIGHT - PASSENGER Relation(many-to-many)

- There is a many-to-many relation between Flight and Passenger classes. Every passenger can have one or more flights
  and every flight can have one or many passenger on board.
    - Please note: a passenger can have 0(zero) flights, and a flight can have 0(zero) passengers.
