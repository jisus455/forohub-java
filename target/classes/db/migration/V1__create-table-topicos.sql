create table topicos(

    id bigint not null auto_increment,
    titulo varchar(50) not null,
    mensaje varchar(100) not null,
    fecha_creacion datetime not null,
    id_usuario int not null,
    nombre_curso varchar(50) not null,
    primary key(id)

);