insert into employee(id, name, department, company_name) values (1,'shazzad','SD','XYZ') on conflict do nothing;
insert into employee(id, name, department, company_name) values (2,'emon','STAT','ABC') on conflict do nothing;
insert into employee(id, name, department, company_name) values (3,'raqib','TE','PQR') on conflict do nothing;

insert into address(id, city, road_number, employee_id) VALUES (1,'Dhaka','16',1) on conflict do nothing;
insert into address(id, city, road_number, employee_id) VALUES (2,'Tangail','10',2) on conflict do nothing;
insert into address(id, city, road_number, employee_id) VALUES (3,'Gazipur','15',3) on conflict do nothing;