alter table pacientes add activo smallint;

update pacientes set activo = 1;