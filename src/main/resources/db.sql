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
                        relative_identity_card_number bigint,
                        relative varchar(50),
                        created_time bigint,
                        updated_time bigint,
                        branch_id int,
                        specialist_id bigint,
                        birth_province_id int,
                        temp_province_id int,
                        temp_district_id int,
                        temp_ward_id int,
                        perm_province_id int,
                        perm_district_id int,
                        perm_ward_id int
);

CREATE TABLE examination_history(
                                    id bigint not null primary key auto_increment,
                                    start_date datetime,
                                    end_date datetime,
                                    branch_id int,
                                    patient_id bigint,
                                    ex_doctor_id bigint,
                                    re_doctor_id bigint,
                                    record_type varchar(50),
                                    status varchar(20),
                                    brief_file_url varchar(500)
);

CREATE TABLE clinical_details (
                                  id bigint not null primary key auto_increment,
                                  patient_id bigint,
                                  history_id bigint,
                                  reason varchar(1000),
                                  pathological_progress varchar(1000),
                                  pulse double,
                                  temperature double,
                                  bp varchar(20),
                                  breathing double,
                                  height double,
                                  weight double,
                                  full_body_examination varchar(1000),
                                  circulatory_system varchar(500),
                                  respiratory_system varchar(500),
                                  digestive_system varchar(500),
                                  genitourinary_system varchar(500),
                                  nerve_system varchar(500),
                                  musculoskeletal_system varchar(500),
                                  ent_system varchar(500),
                                  maxillofacial_system varchar(500),
                                  eye varchar(500),
                                  nutritional_and_endocrinology_etc varchar(500),
                                  brief_file_url varchar(500)
);

CREATE TABLE diag_and_conclusion(
                                    id bigint not null primary key auto_increment,
                                    patient_id bigint,
                                    history_id bigint,
                                    main_disease varchar(200),
                                    additional_disease varchar(200),
                                    disease_prognosis varchar(200),
                                    solution varchar(500),
                                    disease_conclusion varchar(200),
                                    consultation varchar(500),
                                    brief_file_url varchar(200)
);

CREATE TABLE birth_status(
                                    id bigint not null primary key auto_increment,
                                    patient_id bigint,
                                    history_id bigint,
                                    updated_doctor_id bigint,
                                    normal_birth bool,
                                    hard_birth bool,
                                    caesarean_section bool,
                                    born_prematurely bool,
                                    suffocated_at_birth bool,
                                    born_weight double,
                                    born_length double,
                                    birth_defects varchar(300),
                                    others_problem varchar(300)
);

CREATE TABLE risk_factors(
                             id bigint not null primary key auto_increment,
                             patient_id bigint,
                             history_id bigint,
                             updated_doctor_id bigint,
                             smoke bool,
                             drink bool,
                             drug bool,
                             exercise bool,
                             exposure_factors varchar(300),
                             occupational_hazards varchar(200),
                             others_hazards varchar(200)
);

CREATE TABLE anamnesis(
                          id bigint not null primary key auto_increment,
                          patient_id bigint,
                          history_id bigint,
                          updated_doctor_id bigint,
                          anamnesis_type varchar(15),
                          name varchar(100),
                          who varchar(50),
                          description varchar(300)
);

CREATE TABLE surgery_history(
                          id bigint not null primary key auto_increment,
                          patient_id bigint,
                          history_id bigint,
                          updated_doctor_id bigint,
                          surgical_system varchar(50),
                          year_of_surgery varchar(10),
                          description varchar(200),
                          where_of_surgery varchar(200)
);

CREATE TABLE medicine(
                         id bigint not null primary key auto_increment,
                         medicine_code varchar(20),
                         medicine_name varchar(100),
                         content varchar(100),
                         dosage_forms varchar(30),
                         usage_forms varchar(30),
                         medicine_type varchar(30),
                         unit varchar(20),
                         unit_price double
);

CREATE TABLE prescription(
                             id bigint not null primary key auto_increment,
                             patient_id bigint,
                             history_id bigint,
                             updated_specialist_id bigint,
                             performed_specialist_id bigint,
                             supplied_specialist_id bigint,
                             prescription_status varchar(20),
                             content varchar(1000),
                             brief_file_url varchar(500)
);

CREATE TABLE sub_clinical_details(
                                     id bigint not null primary key auto_increment,
                                     patient_id bigint,
                                     history_id bigint,
                                     subclinical_type_id bigint,
                                     subclinical_brief varchar(200),
                                     list_image_keys varchar(1000),
                                     brief_file_url varchar(500)
);