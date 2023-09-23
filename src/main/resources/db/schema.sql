USE like_knu;

CREATE TABLE IF NOT EXISTS announcement
(
    id                 VARCHAR(60)   NOT NULL PRIMARY KEY,
    announcement_title VARCHAR(70)   NOT NULL,
    announcement_url   VARCHAR(1000) NOT NULL,
    announcement_date  DATE          NOT NULL,
    campus             VARCHAR(10)   NOT NULL,
    category           VARCHAR(20)   NOT NULL,
    tag                VARCHAR(20)   NOT NULL
);

CREATE TABLE IF NOT EXISTS route
(
    id             VARCHAR(60) NOT NULL PRIMARY KEY,
    route_type     VARCHAR(10) NOT NULL,
    departure_stop VARCHAR(30) NOT NULL,
    arrival_stop   VARCHAR(30) NOT NULL,
    origin         VARCHAR(20) NOT NULL,
    destination    VARCHAR(20) NOT NULL,
    campus         VARCHAR(10) NOT NULL
) AUTO_INCREMENT = 10000;

CREATE TABLE IF NOT EXISTS city_bus
(
    id          VARCHAR(60) NOT NULL PRIMARY KEY,
    bus_number  VARCHAR(10) NOT NULL,
    bus_name    VARCHAR(30),
    bus_color   VARCHAR(7),
    bus_stop    VARCHAR(30) NOT NULL,
    is_realtime BOOLEAN DEFAULT FALSE
) AUTO_INCREMENT = 10000;

CREATE TABLE IF NOT EXISTS bus_route
(
    bus_id   VARCHAR(60) NOT NULL,
    route_id VARCHAR(60) NOT NULL,
    PRIMARY KEY (bus_id, route_id),
    FOREIGN KEY (bus_id) REFERENCES city_bus (id),
    FOREIGN KEY (route_id) REFERENCES route (id)
);

CREATE TABLE IF NOT EXISTS bus_time
(
    bus_id       VARCHAR(60) NOT NULL,
    arrival_time TIME        NOT NULL,
    PRIMARY KEY (bus_id, arrival_time),
    FOREIGN KEY (bus_id) REFERENCES city_bus (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS cafeteria
(
    id                VARCHAR(60) NOT NULL PRIMARY KEY,
    cafeteria_name    VARCHAR(20) NOT NULL,
    weekday_breakfast VARCHAR(15),
    weekday_lunch     VARCHAR(15),
    weekday_dinner    VARCHAR(15),
    weekend_breakfast VARCHAR(15),
    weekend_lunch     VARCHAR(15),
    weekend_dinner    VARCHAR(15),
    campus            VARCHAR(10) NOT NULL
);

CREATE TABLE IF NOT EXISTS menu
(
    id           VARCHAR(60)  NOT NULL PRIMARY KEY,
    menus        VARCHAR(300) NOT NULL,
    meal_type    VARCHAR(15)  NOT NULL,
    cafeteria_id VARCHAR(60)  NOT NULL,
    FOREIGN KEY (cafeteria_id) REFERENCES cafeteria (id)
);

