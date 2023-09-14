CREATE TABLE IF NOT EXISTS route
(
    id             BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    route_type     VARCHAR(10) NOT NULL,
    departure_stop VARCHAR(30) NOT NULL,
    arrival_stop   VARCHAR(30) NOT NULL,
    origin         VARCHAR(20) NOT NULL,
    destination    VARCHAR(20) NOT NULL,
    campus         VARCHAR(10) NOT NULL
) AUTO_INCREMENT = 10000;

CREATE TABLE IF NOT EXISTS city_bus
(
    id         BIGINT      NOT NULL PRIMARY KEY,
    bus_number VARCHAR(10) NOT NULL,
    bus_name   VARCHAR(30),
    bus_color  VARCHAR(7),
    route_id   BIGINT      NOT NULL,
    FOREIGN KEY (route_id) REFERENCES route (id)
) AUTO_INCREMENT = 10000;

CREATE TABLE IF NOT EXISTS bus_time
(
    bus_id       BIGINT   NOT NULL,
    arrival_time DATETIME NOT NULL,
    PRIMARY KEY (bus_id, arrival_time),
    FOREIGN KEY (bus_id) REFERENCES city_bus (id)
);
