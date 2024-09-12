

-- Insertar empresas
INSERT INTO EMPRESA (nombre, cif, direccion, telefono, email, pagina_web, sector, pais) VALUES
('TechSolutions', 'B12345678', 'Av. Libertador 123', '123456789', 'info@techsolutions.com', 'www.techsolutions.com', 'Tecnología', 'Argentina'),
('SoftDev Corp', 'A87654321', 'Calle Falsa 456', '987654321', 'contacto@softdev.com', 'www.softdev.com', 'Software', 'Argentina'),
('DataAnalytics', 'C11223344', 'Calle Corrientes 789', '555444333', 'info@dataanalytics.com', 'www.dataanalytics.com', 'Análisis de Datos', 'Argentina'),
('AI Innovators', 'Z99887766', 'Calle Inteligencia 101', '654987321', 'info@aiinnovators.com', 'www.aiinnovators.com', 'Inteligencia Artificial', 'Argentina'),
('WebMasters Ltd', 'M55667788', 'Av. Web 202', '321654987', 'contact@webmasters.com', 'www.webmasters.com', 'Desarrollo Web', 'Argentina');

-- Insertar empleados
INSERT INTO EMPLEADO (id_empresa, nombre, apellido, dni, email, telefono, posicion) VALUES
(1, 'Juan', 'Pérez', '12345678A', 'juan.perez@techsolutions.com', '111222333', 'Desarrollador Backend'),
(1, 'Ana', 'Gómez', '87654321B', 'ana.gomez@techsolutions.com', '444555666', 'Analista de Sistemas'),
(2, 'Carlos', 'López', '45678901C', 'carlos.lopez@softdev.com', '777888999', 'Desarrollador Frontend'),
(3, 'María', 'Rodríguez', '78901234D', 'maria.rodriguez@dataanalytics.com', '222333444', 'Científica de Datos'),
(4, 'Luis', 'Fernández', '98765432E', 'luis.fernandez@aiinnovators.com', '555666777', 'Ingeniero de Machine Learning'),
(5, 'Elena', 'Martínez', '32198765F', 'elena.martinez@webmasters.com', '888999000', 'Desarrolladora Web');