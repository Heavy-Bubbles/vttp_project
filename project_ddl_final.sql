create database project;
use project;

create table services (
	id int not null auto_increment,
    name varchar(100),
    price decimal(7,2),
    duration_in_minutes integer,
    
    constraint pk_service_id primary key (id)
);

create table customer (
	id int not null auto_increment,
    name varchar(100),
    gender enum('F', 'M'),
    phone char(8),
    email varchar(100),
    
    constraint pk_customer_id primary key (id)
);

create table appointment (
	id char(8) not null,
    appointment_start bigint,
    appointment_end bigint,
    customer_id integer not null,
    
    constraint pk_appointment_id primary key (id),
    constraint fk_appointment_customer_id foreign key (customer_id) references customer (id)
);

create table appointment_details (
	service_id integer not null,
    appointment_id char(8),
    
    constraint fk_apptdet_service_id foreign key (service_id) references services (id),
    constraint fk_apptdet_appointment_id foreign key (appointment_id) references appointment (id)
);

create table invoice (
	id char(8) not null,
    appointment_id char(8),
    amount_due decimal(8,2),
    invoice_date bigint,
    url varchar(100),
    
    constraint pk_invoice_id primary key (id),
    constraint fk_invoice_appointment_id foreign key (appointment_id) references appointment (id)
);
