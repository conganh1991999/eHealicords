INSERT INTO business_hours
VALUES(null, '06:00:00', '11:30:00', '13:00:00', '20:00:00', null, null, 'MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY');

INSERT INTO province
VALUES(null, 'Hồ Chí Minh', 'TP', '700000');

INSERT INTO district
VALUES(null, '11', 'Quận', 1);

INSERT INTO ward
VALUES(null, '7', 'Phường', 1);

INSERT INTO branch
VALUES(null, 'Phòng khám đa khoa Đại Phước chi nhánh quận 11', '829-829A-831, đường 3/2, Phường 7, Quận 11, TP.HCM', '829-829A-831, đường 3/2', 'info@ytedaiphuoc.vn',
       '028 39 557 999', 'ACTIVE', 1, 1, 1, 1);

INSERT INTO role VALUE (null, 'ROLE_SUB_ADMIN');
INSERT INTO role VALUE (null, 'ROLE_ADMIN');
INSERT INTO role VALUE (null, 'ROLE_DOCTOR');

INSERT INTO staff VALUES(null, 'anh.nguyeniotbycse@hcmut.edu.vn', 'Cong Anh Nguyen', '$2a$10$yW4JCgzIbsXkfvK71mYFHueCQUIhbJdzs/DQJuvnFmdNu6qQ6KVMG', CURRENT_TIMESTAMP(), 'ACTIVE', 1);

INSERT INTO staff_roles VALUES(1, 2);