USE like_knu;

CREATE TABLE IF NOT EXISTS announcement
(
    id                 VARCHAR(60)   NOT NULL PRIMARY KEY,
    announcement_title VARCHAR(70)   NOT NULL,
    announcement_url   VARCHAR(1000) NOT NULL,
    announcement_date  DATE          NOT NULL,
    campus             VARCHAR(10)   NOT NULL,
    category           VARCHAR(20)   NOT NULL,
    tag                VARCHAR(20)   NOT NULL,
    CONSTRAINT announcement_unique
        UNIQUE (announcement_title, announcement_date, campus, category)
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
);

CREATE TABLE IF NOT EXISTS city_bus
(
    id          VARCHAR(60) NOT NULL PRIMARY KEY,
    bus_number  VARCHAR(10) NOT NULL,
    bus_name    VARCHAR(30),
    bus_color   VARCHAR(7),
    bus_stop    VARCHAR(30) NOT NULL,
    is_realtime BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS bus_route
(
    bus_id   VARCHAR(60) NOT NULL,
    route_id VARCHAR(60) NOT NULL,
    PRIMARY KEY (bus_id, route_id),
    FOREIGN KEY (bus_id) REFERENCES city_bus (id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (route_id) REFERENCES route (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS bus_time
(
    bus_id       VARCHAR(60) NOT NULL,
    arrival_time TIME        NOT NULL,
    PRIMARY KEY (bus_id, arrival_time),
    FOREIGN KEY (bus_id) REFERENCES city_bus (id) ON UPDATE CASCADE ON DELETE CASCADE
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
    id           VARCHAR(60) NOT NULL PRIMARY KEY,
    menus        VARCHAR(300),
    meal_type    VARCHAR(15) NOT NULL,
    menu_date    DATE        NOT NULL,
    cafeteria_id VARCHAR(60) NOT NULL,
    FOREIGN KEY (cafeteria_id) REFERENCES cafeteria (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS device
(
    id            VARCHAR(36) NOT NULL PRIMARY KEY,
    fcm_token     VARCHAR(60) UNIQUE,
    campus        VARCHAR(10) NOT NULL,
    registered_at DATETIME    NOT NULL
);

CREATE TABLE IF NOT EXISTS subscribe
(
    device_id VARCHAR(36) NOT NULL,
    tag       VARCHAR(20) NOT NULL,
    PRIMARY KEY (device_id, tag),
    FOREIGN KEY (device_id) REFERENCES device (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS notification
(
    id                 VARCHAR(60)  NOT NULL PRIMARY KEY,
    notification_title VARCHAR(80)  NOT NULL,
    notification_body  VARCHAR(150) NOT NULL,
    notification_date  DATETIME     NOT NULL,
    `read`             BOOLEAN      NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS device_notification
(
    device_id       VARCHAR(36) NOT NULL,
    notification_id VARCHAR(60) NOT NULL,
    PRIMARY KEY (device_id, notification_id),
    FOREIGN KEY (device_id) REFERENCES device (id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (notification_id) REFERENCES notification (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS shuttle
(
    id           VARCHAR(60) NOT NULL PRIMARY KEY,
    origin       VARCHAR(20) NOT NULL,
    destination  VARCHAR(20) NOT NULL,
    shuttle_type VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS shuttle_bus
(
    id         VARCHAR(60) NOT NULL PRIMARY KEY,
    bus_name   VARCHAR(20) NOT NULL,
    shuttle_id VARCHAR(60) NOT NULL,
    FOREIGN KEY (shuttle_id) REFERENCES shuttle (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS shuttle_time
(
    bus_id       VARCHAR(60) NOT NULL,
    arrival_stop VARCHAR(30) NOT NULL,
    arrival_time TIME        NOT NULL,
    sequence     INT         NOT NULL,
    PRIMARY KEY (bus_id, arrival_stop, arrival_time),
    FOREIGN KEY (bus_id) REFERENCES shuttle_bus (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS academic_calendar
(
    id         VARCHAR(60) NOT NULL PRIMARY KEY,
    contents   VARCHAR(30) NOT NULL,
    start_date DATE        NOT NULL,
    end_date   DATE        NOT NULL,
    UNIQUE schedule_unique (contents, start_date, end_date)
);

CREATE TABLE IF NOT EXISTS shuttle_campus
(
    shuttle_id VARCHAR(60) NOT NULL,
    campus     VARCHAR(10) NOT NULL,
    PRIMARY KEY (shuttle_id, campus),
    FOREIGN KEY (shuttle_id) REFERENCES shuttle (id) ON UPDATE CASCADE ON DELETE CASCADE
);
