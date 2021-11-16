create table employee_info (
	uuid_ VARCHAR(75) null,
	employeeId LONG not null primary key,
	employeeName VARCHAR(75) null,
	mobile VARCHAR(75) null,
	email VARCHAR(75) null,
	uploadPhoto LONG,
	currentDesignation VARCHAR(75) null,
	department VARCHAR(75) null
);