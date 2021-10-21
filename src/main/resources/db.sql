CREATE DATABASE ehealicords;

USE ehealicords;

CREATE TABLE business_hours(
                               id int not null primary key auto_increment,
                               morning_open time,
                               morning_close time,
                               afternoon_open time,
                               afternoon_close time,
                               evening_open time,
                               evening_close time,
                               days varchar(255)
);

CREATE TABLE branch(
                         id int not null primary key auto_increment,
                         name varchar(255),
                         full_address varchar(255),
                         address varchar(255),
                         email varchar(100),
                         phone_number varchar(15),
                         status varchar(10),
                         province_id integer,
                         district_id integer,
                         ward_id integer,
                         business_hours_id int
);

CREATE TABLE district(
                          id int not null primary key auto_increment,
                          name varchar(255),
                          prefix varchar(255),
                          province_id int
);

CREATE TABLE medical_specialty(
                                    id int not null primary key auto_increment,
                                    name varchar(255),
                                    description varchar(255),
                                    branch_id int
);

CREATE TABLE province(
                          id int not null primary key auto_increment,
                          name varchar(255),
                          prefix varchar(255),
                          code varchar(50)
);

CREATE TABLE role(
                      id bigint not null primary key auto_increment,
                      type varchar(15)
);

CREATE TABLE ward(
                      id int not null primary key auto_increment,
                      name varchar(255),
                      prefix varchar(255),
                      district_id int
);

-- CREATE TABLE user_relatives(
--                                id bigint not null primary key,
--                                full_name varchar(255),
--                                relation varchar(30),
--                                user_id bigint,
--                                date_of_birth date,
--                                job varchar(50),
--                                gender varchar(10),
--                                personal_id varchar(15)
-- );
--
-- CREATE TABLE users(
--                       id bigint not null primary key,
--                       phone_number varchar(15),
--                       full_name varchar(255),
--                       job varchar(50),
--                       address varchar(255),
--                       status varchar(30),
--                       personal_id varchar(15),
--                       gender varchar(10),
--                       province_id int,
--                       ward_id int,
--                       district_id int,
--                       updated_time bigint,
--                       date_of_birth date,
--                       avatar_key varchar(500),
--                       balance bigint,
--                       balance_update_timestamp bigint,
--                       is_valid boolean
-- );

CREATE TABLE specialist(
                            id bigint not null primary key auto_increment,
                            email varchar(100),
                            full_name varchar(255),
                            phone_number varchar(15),
                            avatar_key varchar(500),
                            date_of_birth date,
                            date_of_starting_work date,
                            created_time bigint,
                            updated_time bigint,
                            gender varchar(10),
                            specialist_type varchar(15),
                            academic_rank varchar(20),
                            degree varchar(10),
                            degree_of_specialist varchar(20),
                            medial_specialty_id int,
                            room_id int,
                            staff_id bigint,
                            branch_id int
);

CREATE TABLE staff(
                       id bigint not null primary key auto_increment,
                       email varchar(100),
                       full_name varchar(255),
                       password varchar(255),
                       created_time bigint,
                       status varchar(30),
                       branch_id int
);

CREATE TABLE staff_roles(
                            staff_id bigint,
                            role_id bigint,
                            primary key (staff_id, role_id)
);

CREATE TABLE room(
                     id int not null primary key auto_increment,
                     name varchar(255),
                     description varchar(255),
                     room_type_id int,
                     branch_id int
);

CREATE TABLE room_type(
                          id int not null primary key auto_increment,
                          name varchar(255),
                          description varchar(255),
                          branch_id int
);

CREATE TABLE patient(
                        id bigint not null primary key auto_increment,
                        identity_card_number bigint not null unique,
                        personal_health_id bigint not null unique,
                        gender varchar(10),
                        health_insurance_card_number varchar(20),
                        full_name varchar(255),
                        date_of_birth date,
                        place_of_birth varchar(50),
                        hometown varchar(50),
                        phone_number varchar(15),
                        identity_card_issue_place varchar(50),
                        identity_card_issue_date date,
                        temporary_residence_address varchar(255),
                        permanent_address varchar(255),
                        nationality varchar(50),
                        religion varchar(50),
                        folk varchar(50),
                        occupation varchar(50),
                        academic_level varchar(50),
                        marital_status varchar(50),
                        relative_name varchar(255),
                        relative_phone_number varchar(15),
                        relative_identity_card_number bigint not null unique,
                        relative varchar(50),
                        created_time bigint,
                        updated_time bigint,
                        branch_id int,
                        birth_province_id int,
                        temp_province_id int,
                        temp_district_id int,
                        temp_ward_id int,
                        perm_province_id int,
                        perm_district_id int,
                        perm_ward_id int
);