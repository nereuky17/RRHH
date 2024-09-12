-- Eliminamos las tablas si ya existen
DROP TABLE IF EXISTS EMPLEADO;
DROP TABLE IF EXISTS EMPRESA;





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

CREATE TABLE EMPLEADO (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_empresa BIGINT NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50),
    dni VARCHAR(9) NOT NULL UNIQUE,
    email VARCHAR(255),
    telefono VARCHAR(15),
    posicion VARCHAR(50),
    CONSTRAINT fk_empresa FOREIGN KEY (id_empresa) REFERENCES EMPRESA(id) ON DELETE CASCADE
);
