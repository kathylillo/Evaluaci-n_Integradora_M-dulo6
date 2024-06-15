CREATE SCHEMA alke_wallet_6 ;
USE alke_wallet_6;


-- Creación de la tabla de usuario
CREATE TABLE Usuario (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255),
    correo VARCHAR(255),
    clave VARCHAR(255),
    saldo INT,
    fecha_de_creacion TIMESTAMP
);

-- Creación de  la tabla de transaccion
CREATE TABLE Transaccion (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    sender_user_id INT,
    receiver_user_id INT,
    valor INT,
    transaction_date TIMESTAMP,
    currency_id INT,
    FOREIGN KEY (sender_user_id) REFERENCES Usuario(user_id),
    FOREIGN KEY (receiver_user_id) REFERENCES Usuario(user_id),
    FOREIGN KEY (currency_id) REFERENCES Moneda(currencyId)
);

-- Creación de la tabla de moneda
CREATE TABLE Moneda (
    currencyId INT AUTO_INCREMENT PRIMARY KEY,
    currencyName VARCHAR(255),
    currencySymbol VARCHAR(10)
);

-- Insert de monedas
INSERT INTO Moneda (currencyName, currencySymbol) VALUES
    ('Peso Chileno', 'CLP $'),
    ('Dólar Americano', 'USD $'),
    ('Euro', 'EUR €');

-- Insert de  usuarios
INSERT INTO Usuario (nombre, correo, clave, saldo, fecha_de_creacion) VALUES
    ('Juan Pablo Reyes ', 'juanpablo@mail.com', '$2a$10$H/kfkXxnbpoD86qnGHcwjO8Z0h6OqG01FCUgY9ppYAHWmdrR1pubO', 1000, NOW()), --  clave : Profe12345
    ('Monica Perez ', 'mperez@mail.com', '$2a$10$IyGqsKo.bJ7LFxfOOyyRw.hF26UBnhb0fypN.KNzIStgAyjF0Vw1.', 2000, NOW()),  -- clave : 12345
    ('Carlos Rojas', 'crojas@mail.com', '$2a$10$H9XdR8cepQx39C2n.7OosOkecCQrRyrNaswIggX2xU91jyWYmGS3q', 1500, NOW()), -- clave : 123456
    ('Antonio Marin ', 'amarin@mail.com', '$2a$10$WBfrCS6KdAcVPq4pJCs0xO1iQuKeoALiJynKnNP8oL8rhdQtKF/3W', 3000, NOW()),  -- clave : 98765
    ('Leonardo Andrade', 'landrade@mail.com', '$2a$10$GSpvwsPp2wqSUZL3FKl08evH.fCrBRtDEj5nNkR403QGueNZxzn.i', 500, NOW()), -- clave : 54321
    ('Luisa Godoy', 'lgodoy@mail.com', '$2a$10$DskFJ293ekQZGpFfP7dCEO7DGCfeDWJG824X9UX87u/rXwMB62K86', 800, NOW()), -- clave : 12345l
    ('Maria Reyes', 'mreyes@mail.com', '$2a$10$OSvdF/pp2/EsHlaqvK3tiOBX2/hIiQ7s4bnTeTzPU4zyhh9jJurKe', 2500, NOW()), -- clave : sol123
    ('Carolina Molina', 'cmolina@mail.com', '$2a$10$kF2GNXX5wowAIWdIaPof8.72x4eRdG/.1yEG0jjJqbAYGisQak2xm', 1200, NOW()), -- clave : caro123
    ('Isabella Carter', 'icarter@mail.com', '$2a$10$loNnNuLIXFsRP0gnBh//weQgm/8Aa31wI5YZ8/iDqFnABu6730vYG', 1800, NOW()), -- clave : isa321
    ('Ignacio Rhein', 'irhein@mail.com', '$2a$10$WL7qWsaEO5WRsilrgoGiRuS9Dir0bkVuOUP3r.DyqGK8znQelSA2C', 2200, NOW()); -- clave : igna987

    
    
    select * from usuario;
    
    select * from Transaccion ; 