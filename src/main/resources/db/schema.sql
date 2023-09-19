USE like_knu;

CREATE TABLE IF NOT EXISTS announcement
(
    id                 BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    announcement_title VARCHAR(100) NOT NULL,
    announcement_url   VARCHAR(500) NOT NULL,
    announcement_date  DATETIME     NOT NULL,
    campus             VARCHAR(10)  NOT NULL,
    category           VARCHAR(20)  NOT NULL,
    tag                VARCHAR(20)  NOT NULL
);

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
    id          BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    bus_number  VARCHAR(10) NOT NULL,
    bus_name    VARCHAR(30),
    bus_color   VARCHAR(7),
    bus_stop    VARCHAR(30) NOT NULL,
    is_realtime BOOLEAN DEFAULT FALSE
) AUTO_INCREMENT = 10000;

CREATE TABLE IF NOT EXISTS bus_route
(
    bus_id   BIGINT NOT NULL,
    route_id BIGINT NOT NULL,
    PRIMARY KEY (bus_id, route_id),
    FOREIGN KEY (bus_id) REFERENCES city_bus (id),
    FOREIGN KEY (route_id) REFERENCES route (id)
);

CREATE TABLE IF NOT EXISTS bus_time
(
    bus_id       BIGINT NOT NULL,
    arrival_time TIME   NOT NULL,
    PRIMARY KEY (bus_id, arrival_time),
    FOREIGN KEY (bus_id) REFERENCES city_bus (id) ON DELETE CASCADE
);
