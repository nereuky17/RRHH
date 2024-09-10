-- Eliminamos las tablas si ya existen
DROP TABLE IF EXISTS EMPLEADO;
DROP TABLE IF EXISTS EMPRESA;

-- Eliminamos la secuencia si ya existe
DROP SEQUENCE IF EXISTS EMPLEADO_SEQ;


CREATE SEQUENCE EMPLEADO_SEQ START WITH 1 INCREMENT BY 50;

-- Creamos la tabla EMPRESA
CREATE TABLE EMPRESA (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    cif VARCHAR(10) NOT NULL,
    direccion VARCHAR(255),
    telefono VARCHAR(15),
    email VARCHAR(255),
    pagina_web VARCHAR(100), 
    sector VARCHAR(50),
    pais VARCHAR(50)
);

-- Creamos la tabla EMPLEADO 
CREATE TABLE EMPLEADO (
    id BIGINT DEFAULT NEXT VALUE FOR EMPLEADO_SEQ PRIMARY KEY,
    id_empresa BIGINT NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50),
    dni VARCHAR(9) NOT NULL UNIQUE,
    email VARCHAR(255),
    telefono VARCHAR(15),
    posicion VARCHAR(50),
    fecha_contratacion DATE NOT NULL, 
    FOREIGN KEY (id_empresa) REFERENCES EMPRESA(id) ON DELETE CASCADE
);
