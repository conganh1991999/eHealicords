-- Trước hết tạo các quyền trong hệ thống:
INSERT INTO role VALUE (null, 'ROLE_ADMIN');
INSERT INTO role VALUE (null, 'ROLE_SUB_ADMIN');
INSERT INTO role VALUE (null, 'ROLE_DOCTOR');
--

-- Phải tự tạo account admin trước, vì sẽ không có api làm chuyện này:
INSERT INTO staff VALUES(null, 'admin@gmail.com', 'Admin Tổng Quản', '$2a$10$c7RttNBTz0tIGBlxjb6nuuL8KNbRXG7yU/opaorEgmG87gr2v1YDa', CURRENT_TIMESTAMP(), 'ACTIVE', null);
INSERT INTO staff_roles VALUES(1, 1);
--

-- Thêm các thông tin về địa chỉ
INSERT INTO province VALUES(null, 'Hồ Chí Minh', 'TP', '700000');
INSERT INTO district VALUES(null, 'Thủ Đức', 'Quận', 1);
INSERT INTO district VALUES(null, 'Bình Thạnh', 'Quận', 1);
INSERT INTO district VALUES(null, '10', 'Quận', 1);
INSERT INTO district VALUES(null, '11', 'Quận', 1);
INSERT INTO ward VALUES(null, '7', 'Phường', 2);
INSERT INTO ward VALUES(null, '8', 'Phường', 2);
INSERT INTO ward VALUES(null, '9', 'Phường', 2);
INSERT INTO ward VALUES(null, '10', 'Phường', 2);
--

-- Các dữ liệu khác thì gọi API để tạo.