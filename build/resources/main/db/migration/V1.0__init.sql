CREATE TABLE floors
(
    floor_id   BIGINT PRIMARY KEY,
    floor_name VARCHAR(255) NOT NULL
);

CREATE TABLE rooms
(
    room_id   BIGINT PRIMARY KEY,
    room_name VARCHAR(255) NOT NULL,
    floor_id  BIGINT       NOT NULL,
    FOREIGN KEY (floor_id) REFERENCES floors (floor_id)
);

CREATE TABLE desks
(
    desk_id   BIGINT PRIMARY KEY,
    desk_name VARCHAR(255) NOT NULL,
    room_id   BIGINT       NOT NULL,
    FOREIGN KEY (room_id) REFERENCES rooms (room_id)
);

CREATE TABLE users
(
    user_id      BIGINT PRIMARY KEY,
    username     VARCHAR(255) NOT NULL,
    password     VARCHAR(255) NOT NULL,
    first_name   VARCHAR(255) NOT NULL,
    account_type ENUM('USER', 'ADMIN') NOT NULL,
    last_name    VARCHAR(255) NOT NULL,
    email        VARCHAR(255) NOT NULL
);

CREATE TABLE reservations
(
    reservation_id    BIGINT PRIMARY KEY,
    user_id           BIGINT    NOT NULL,
    floor_id          BIGINT    NOT NULL,
    desk_id           BIGINT    NOT NULL,
    reservation_start TIMESTAMP NOT NULL,
    reservation_end   TIMESTAMP NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (floor_id) REFERENCES floors (floor_id),
    FOREIGN KEY (desk_id) REFERENCES desks (desk_id)
);

INSERT INTO floors (floor_id, floor_name)
VALUES (1, 'First floor'),
       (2, 'Second floor'),
       (3, 'Third floor');

INSERT INTO rooms (room_id, room_name, floor_id)
VALUES (1, 'Room 1', 1),
       (2, 'Room 2', 1),
       (3, 'Room 1', 2),
       (4, 'Room 1', 3),
       (5, 'Room 2', 3);

INSERT INTO desks (desk_id, desk_name, room_id)
VALUES (1, 'Desk 1', 1),
       (2, 'Desk 2', 1),
       (3, 'Desk 3', 2),
       (4, 'Desk 4', 3),
       (5, 'Desk 5', 3);

INSERT INTO users (user_id, username, password, first_name, account_type, last_name, email)
VALUES (1, 'john_doe', 'password123', 'John', 'USER', 'Doe', 'john.doe@example.com'),
       (2, 'jane_smith', 'password123', 'Jane', 'ADMIN', 'Smith', 'jane.smith@example.com'),
       (3, 'bob_johnson', 'password123', 'Bob', 'USER', 'Johnson', 'bob.johnson@example.com');

INSERT INTO reservations (reservation_id, user_id, floor_id, desk_id, reservation_start, reservation_end)
VALUES (1, 1, 1, 1, '2023-04-18 10:00:00', '2023-04-18 11:00:00'),
       (2, 2, 2, 3, '2023-04-18 12:00:00', '2023-04-18 14:00:00'),
       (3, 3, 3, 5, '2023-04-19 09:00:00', '2023-04-19 12:00:00');