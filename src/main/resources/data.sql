INSERT INTO AIRLINES(AIRLINE, FLEET_SIZE, DESTINATIONS)
VALUES ('Qantas Airlines', 134, 77),
       ('Turkish Airlines', 350, 304),
       ('Air New Zealand', 114, 80),
       ('Virgin Australia', 68, 40),
       ('Bahamas Air', 10, 21),
       ('Private Voyages', 11, 13),
       ('Iberia Airlines', 68, 120),
       ('All Nippon Airways', 220, 75);

INSERT INTO FLIGHTS(AIRLINE, FLIGHT_NUMBER, FLIGHT_TYPE, DEPARTURE, ARRIVAL, DATE, STATUS)
VALUES ('Qantas Airlines', 'QF12', 'COMM', 'Los Angeles', 'Sydney', DATE '2021-10-21', true),
       ('Turkish Airlines', 'TK77', 'COMM', 'Istanbul', 'Miami', DATE '2021-10-22', false),
       ('Private Voyages', 'HI4506', 'PRIV', 'Honolulu', 'Sydney', DATE '2021-10-21', true),
       ('Air New Zealand', 'NZ6141', 'COMM', 'Auckland', 'Sydney', DATE '2021-10-22', false),
       ('Bahamas Air', 'BHS224', 'COMM', 'Miami', 'Nassau', DATE '2021-10-23', false),
       ('Virgin Australia', 'VA526', 'COMM', 'Sydney', 'Gold Coast', DATE '2021-10-22', true),
       ('Virgin Australia', 'VA734', 'COMM', 'Gold Coast', 'Melbourne', DATE '2021-10-23', false),
       ('Private Voyages', 'HI1204', 'PRIV', 'New York', 'Miami', DATE '2021-10-23', false),
       ('Iberia Airlines', 'IB3013', 'COMM', 'Barcelona', 'Madrid', DATE '2021-11-19', true),
       ('All Nippon Airways', 'NH802', 'COMM', 'Singapore', 'Tokyo', DATE '2021-12-13', true);

-- SELECT * FROM FLIGHTS;

INSERT INTO PASSENGERS(NAME, AGE, GENDER, passenger_type)
VALUES ('Johnny Thunder', 21, 'M', TRUE),
       ('Anna Cooper', 23, 'F', TRUE),
       ('Padme Amidala', 35, 'F', FALSE),
       ('Kilroy Barron', 52, 'M', FALSE),
       ('Senor Palomar', 32, 'M', FALSE),
       ('Kül Tigin', 37, 'M', FALSE),
       ('Valaria Ferreria', 26, 'F', FALSE),
       ('Akane Tanaka', 34, 'F', FALSE);
