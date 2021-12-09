CREATE DATABASE ehealicords_v2;

USE ehealicords_v2;

CREATE TABLE nationality(
                            id int not null primary key auto_increment,
                            name varchar(50) not null
);

CREATE TABLE religion(
                         id int not null primary key auto_increment,
                         name varchar(50) not null
);

CREATE TABLE folk(
                     id int not null primary key auto_increment,
                     name varchar(50) not null
);

CREATE TABLE occupation(
                           id int not null primary key auto_increment,
                           name varchar(50) not null
);

CREATE TABLE academic_level(
                               id int not null primary key auto_increment,
                               name varchar(50) not null
);

CREATE TABLE marital_status(
                               id int not null primary key auto_increment,
                               name varchar(50) not null
);

CREATE TABLE relative(
                         id int not null primary key auto_increment,
                         name varchar(50) not null
);

CREATE TABLE province(
                         id int not null primary key auto_increment,
                         name varchar(255) not null,
                         prefix varchar(255) not null,
                         code varchar(50)
);

CREATE TABLE district(
                         id int not null primary key auto_increment,
                         name varchar(255) not null,
                         prefix varchar(255) not null,
                         province_id int not null
);

CREATE TABLE ward(
                     id int not null primary key auto_increment,
                     name varchar(255) not null,
                     prefix varchar(255) not null,
                     district_id int not null
);

ALTER TABLE district ADD FOREIGN KEY(province_id) REFERENCES province(id);
ALTER TABLE ward ADD FOREIGN KEY(district_id) REFERENCES district(id);

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
                       name varchar(255) not null unique,
                       full_address varchar(255),
                       address varchar(255),
                       email varchar(100),
                       phone_number varchar(15),
                       status varchar(10) not null,
                       province_id int,
                       district_id int,
                       ward_id int,
                       business_hours_id int
);

ALTER TABLE branch ADD FOREIGN KEY(province_id) REFERENCES province(id);
ALTER TABLE branch ADD FOREIGN KEY(district_id) REFERENCES district(id);
ALTER TABLE branch ADD FOREIGN KEY(ward_id) REFERENCES ward(id);
ALTER TABLE branch ADD FOREIGN KEY(business_hours_id) REFERENCES business_hours(id);

CREATE TABLE medical_specialty(
                                  id int not null primary key auto_increment,
                                  name varchar(255) not null,
                                  description varchar(255),
                                  branch_id int not null
);

ALTER TABLE medical_specialty ADD FOREIGN KEY(branch_id) REFERENCES branch(id);

CREATE TABLE room_type(
                          id int not null primary key auto_increment,
                          name varchar(255) not null,
                          description varchar(255),
                          branch_id int not null
);

ALTER TABLE room_type ADD FOREIGN KEY(branch_id) REFERENCES branch(id);

CREATE TABLE room(
                     id int not null primary key auto_increment,
                     name varchar(255) not null,
                     description varchar(255),
                     room_type_id int not null,
                     branch_id int not null
);

ALTER TABLE room ADD FOREIGN KEY(room_type_id) REFERENCES room_type(id);
ALTER TABLE room ADD FOREIGN KEY(branch_id) REFERENCES branch(id);

CREATE TABLE subclinical_type(
                                 id bigint not null primary key auto_increment,
                                 name varchar(100) not null unique,
                                 description varchar(200)
);

CREATE TABLE record_type(
                            id bigint not null primary key auto_increment,
                            name varchar(100) not null unique,
                            description varchar(200)
);

CREATE TABLE medicine(
                         id bigint not null primary key auto_increment,
                         medicine_code varchar(20) not null,
                         medicine_name varchar(100) not null,
                         content varchar(100),
                         dosage_forms varchar(30),
                         usage_forms varchar(30) not null,
                         medicine_type varchar(30),
                         unit varchar(20) not null,
                         unit_price double not null
);

CREATE TABLE role(
                     id bigint not null primary key auto_increment,
                     type varchar(15) not null unique
);

CREATE TABLE specialist(
                           id bigint not null primary key auto_increment,
                           email varchar(100) not null unique,
                           full_name varchar(255) not null,
                           phone_number varchar(15),
                           avatar_key varchar(500),
                           date_of_birth date,
                           date_of_starting_work date,
                           created_time bigint,
                           updated_time bigint,
                           gender varchar(10),
                           specialist_type varchar(15) not null,
                           academic_rank varchar(20),
                           degree varchar(10),
                           degree_of_specialist varchar(20),
                           medial_specialty_id int,
                           room_id int,
                           staff_id bigint,
                           branch_id int not null
);

CREATE TABLE staff(
                      id bigint not null primary key auto_increment,
                      email varchar(100) not null unique,
                      full_name varchar(255) not null,
                      password varchar(255) not null,
                      created_time bigint,
                      status varchar(30) not null,
                      branch_id int not null
);

ALTER TABLE specialist ADD FOREIGN KEY(medial_specialty_id) REFERENCES medical_specialty(id);
ALTER TABLE specialist ADD FOREIGN KEY(room_id) REFERENCES room(id);
ALTER TABLE specialist ADD FOREIGN KEY(staff_id) REFERENCES staff(id);
ALTER TABLE specialist ADD FOREIGN KEY(branch_id) REFERENCES branch(id);

ALTER TABLE staff ADD FOREIGN KEY(branch_id) REFERENCES branch(id);

CREATE TABLE staff_roles(
                            staff_id bigint not null,
                            role_id bigint not null,
                            primary key (staff_id, role_id)
);

ALTER TABLE staff_roles ADD FOREIGN KEY(staff_id) REFERENCES staff(id);
ALTER TABLE staff_roles ADD FOREIGN KEY(role_id) REFERENCES role(id);

CREATE TABLE patient(
                        id bigint not null primary key auto_increment,
                        identity_card_number bigint not null unique,
                        personal_health_id bigint not null unique,
                        gender varchar(10),
                        health_insurance_card_number varchar(20),
                        full_name varchar(255) not null,
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
                        branch_id int not null,
                        specialist_id bigint not null,
                        birth_province_id int,
                        temp_province_id int,
                        temp_district_id int,
                        temp_ward_id int,
                        perm_province_id int,
                        perm_district_id int,
                        perm_ward_id int
);

ALTER TABLE patient ADD FOREIGN KEY(branch_id) REFERENCES branch(id);
ALTER TABLE patient ADD FOREIGN KEY(specialist_id) REFERENCES specialist(id);
ALTER TABLE patient ADD FOREIGN KEY(birth_province_id) REFERENCES province(id);
ALTER TABLE patient ADD FOREIGN KEY(temp_province_id) REFERENCES province(id);
ALTER TABLE patient ADD FOREIGN KEY(temp_district_id) REFERENCES district(id);
ALTER TABLE patient ADD FOREIGN KEY(temp_ward_id) REFERENCES ward(id);
ALTER TABLE patient ADD FOREIGN KEY(perm_province_id) REFERENCES province(id);
ALTER TABLE patient ADD FOREIGN KEY(perm_district_id) REFERENCES district(id);
ALTER TABLE patient ADD FOREIGN KEY(perm_ward_id) REFERENCES ward(id);

CREATE TABLE examination_history(
                                    id bigint not null primary key auto_increment,
                                    start_date datetime not null,
                                    end_date datetime not null,
                                    branch_id int,
                                    patient_id bigint not null,
                                    ex_doctor_id bigint,
                                    re_doctor_id bigint,
                                    record_type varchar(50) not null,
                                    status varchar(20) not null,
                                    brief_file_url varchar(500)
);

ALTER TABLE examination_history ADD FOREIGN KEY(branch_id) REFERENCES branch(id);
ALTER TABLE examination_history ADD FOREIGN KEY(patient_id) REFERENCES patient(id);
ALTER TABLE examination_history ADD FOREIGN KEY(ex_doctor_id) REFERENCES specialist(id);
ALTER TABLE examination_history ADD FOREIGN KEY(re_doctor_id) REFERENCES specialist(id);
ALTER TABLE examination_history ADD CONSTRAINT unique(start_date, end_date, patient_id);

CREATE TABLE clinical_details (
                                  id bigint not null primary key auto_increment,
                                  patient_id bigint not null,
                                  history_id bigint not null,
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

ALTER TABLE clinical_details ADD FOREIGN KEY(patient_id) REFERENCES patient(id);
ALTER TABLE clinical_details ADD FOREIGN KEY(history_id) REFERENCES examination_history(id);

CREATE TABLE diag_and_conclusion(
                                    id bigint not null primary key auto_increment,
                                    patient_id bigint not null,
                                    history_id bigint not null,
                                    main_disease varchar(200),
                                    additional_disease varchar(200),
                                    disease_prognosis varchar(200),
                                    solution varchar(500),
                                    disease_conclusion varchar(200),
                                    consultation varchar(500),
                                    brief_file_url varchar(200)
);

ALTER TABLE diag_and_conclusion ADD FOREIGN KEY(patient_id) REFERENCES patient(id);
ALTER TABLE diag_and_conclusion ADD FOREIGN KEY(history_id) REFERENCES examination_history(id);

CREATE TABLE birth_status(
                             id bigint not null primary key auto_increment,
                             patient_id bigint not null,
                             history_id bigint not null,
                             updated_doctor_id bigint not null,
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

ALTER TABLE birth_status ADD FOREIGN KEY(patient_id) REFERENCES patient(id);
ALTER TABLE birth_status ADD FOREIGN KEY(history_id) REFERENCES examination_history(id);
ALTER TABLE birth_status ADD FOREIGN KEY(updated_doctor_id) REFERENCES specialist(id);

CREATE TABLE risk_factors(
                             id bigint not null primary key auto_increment,
                             patient_id bigint not null,
                             history_id bigint not null,
                             updated_doctor_id bigint not null,
                             smoke bool,
                             drink bool,
                             drug bool,
                             exercise bool,
                             exposure_factors varchar(300),
                             occupational_hazards varchar(200),
                             others_hazards varchar(200)
);

ALTER TABLE risk_factors ADD FOREIGN KEY(patient_id) REFERENCES patient(id);
ALTER TABLE risk_factors ADD FOREIGN KEY(history_id) REFERENCES examination_history(id);
ALTER TABLE risk_factors ADD FOREIGN KEY(updated_doctor_id) REFERENCES specialist(id);

CREATE TABLE anamnesis(
                          id bigint not null primary key auto_increment,
                          patient_id bigint not null,
                          history_id bigint not null,
                          updated_doctor_id bigint not null,
                          anamnesis_type varchar(15) not null,
                          name varchar(100) not null,
                          who varchar(50),
                          description varchar(300)
);

ALTER TABLE anamnesis ADD FOREIGN KEY(patient_id) REFERENCES patient(id);
ALTER TABLE anamnesis ADD FOREIGN KEY(history_id) REFERENCES examination_history(id);
ALTER TABLE anamnesis ADD FOREIGN KEY(updated_doctor_id) REFERENCES specialist(id);

CREATE TABLE surgery_history(
                                id bigint not null primary key auto_increment,
                                patient_id bigint not null,
                                history_id bigint not null,
                                updated_doctor_id bigint not null,
                                surgical_system varchar(50) not null,
                                year_of_surgery varchar(10),
                                description varchar(200),
                                where_of_surgery varchar(200)
);

ALTER TABLE surgery_history ADD FOREIGN KEY(patient_id) REFERENCES patient(id);
ALTER TABLE surgery_history ADD FOREIGN KEY(history_id) REFERENCES examination_history(id);
ALTER TABLE surgery_history ADD FOREIGN KEY(updated_doctor_id) REFERENCES specialist(id);

CREATE TABLE prescription(
                             id bigint not null primary key auto_increment,
                             patient_id bigint not null,
                             history_id bigint not null,
                             updated_specialist_id bigint,
                             performed_specialist_id bigint,
                             supplied_specialist_id bigint,
                             prescription_status varchar(20) not null,
                             content varchar(1000),
                             brief_file_url varchar(500)
);

ALTER TABLE prescription ADD FOREIGN KEY(patient_id) REFERENCES patient(id);
ALTER TABLE prescription ADD FOREIGN KEY(history_id) REFERENCES examination_history(id);
ALTER TABLE prescription ADD FOREIGN KEY(updated_specialist_id) REFERENCES specialist(id);
ALTER TABLE prescription ADD FOREIGN KEY(performed_specialist_id) REFERENCES specialist(id);
ALTER TABLE prescription ADD FOREIGN KEY(supplied_specialist_id) REFERENCES specialist(id);

CREATE TABLE sub_clinical_details(
                                     id bigint not null primary key auto_increment,
                                     patient_id bigint not null,
                                     history_id bigint not null,
                                     subclinical_type_id bigint not null,
                                     subclinical_brief varchar(200),
                                     list_image_keys varchar(1000),
                                     brief_file_url varchar(500)
);

ALTER TABLE sub_clinical_details ADD FOREIGN KEY(patient_id) REFERENCES patient(id);
ALTER TABLE sub_clinical_details ADD FOREIGN KEY(history_id) REFERENCES examination_history(id);
ALTER TABLE sub_clinical_details ADD FOREIGN KEY(subclinical_type_id) REFERENCES subclinical_type(id);
