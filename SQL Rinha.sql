CREATE SCHEMA rinha ;
USE rinha;

CREATE TABLE Login (
    Email varchar(50) unique,
    Senha varchar(50),
    UUID varchar(50) PRIMARY KEY
);

CREATE TABLE Usuario (
    Nome varchar(50),
    CPF varchar(11),
    Data_de_Nascimento date,
    fk_Login_UUID varchar(50),
    Img_Perfil blob
);

CREATE TABLE Equipe (
    Nome varchar(50),
    Qtd_Membros smallint,
    Categoria varchar(50),
    Descricao varchar(250),
    Img blob,
    ID int auto_increment PRIMARY KEY
);

CREATE TABLE Campeonato (
    Nome varchar(50),
    Qtd_Times int,
    Categoria varchar(50),
    Descricao varchar(250),
    ID int  auto_increment PRIMARY KEY,
    Img blob,
    Data_Inicial date,
    Data_Final date
);

CREATE TABLE Endereco (
    Bairro varchar(50),
    Logradouro varchar(50),
    CEP varchar(8),
    Numero varchar(10),
    Complemento varchar(50),
    uf varchar(50),
    cidade varchar(50),
    ID int auto_increment PRIMARY KEY
);

CREATE TABLE Compete (
    fk_Equipe_ID int,
    fk_Campeonato_ID int
);

CREATE TABLE Participa (
    fk_Equipe_ID int,
    fk_Login_UUID varchar(50),
    editor boolean
);

CREATE TABLE Organiza (
    fk_Campeonato_ID int,
    fk_Login_UUID varchar(50) 
);

CREATE TABLE Reside (
    fk_Endereco_ID int,
    fk_Login_UUID varchar(50) 
);

CREATE TABLE Ocorre (
    fk_Campeonato_ID int,
    fk_Endereco_ID int
);
 
 ALTER TABLE Usuario ADD CONSTRAINT FK_Login_1
    FOREIGN KEY (fk_Login_UUID)
    REFERENCES Login (UUID)
    ON DELETE SET NULL;
 
ALTER TABLE Compete ADD CONSTRAINT FK_Compete_1
    FOREIGN KEY (fk_Equipe_ID)
    REFERENCES Equipe (ID)
    ON DELETE SET NULL;
 
ALTER TABLE Compete ADD CONSTRAINT FK_Compete_2
    FOREIGN KEY (fk_Campeonato_ID)
    REFERENCES Campeonato (ID)
    ON DELETE SET NULL;
 
ALTER TABLE Participa ADD CONSTRAINT FK_Participa_1
    FOREIGN KEY (fk_Equipe_ID)
    REFERENCES Equipe (ID)
    ON DELETE RESTRICT;
 
ALTER TABLE Participa ADD CONSTRAINT FK_Participa_2
    FOREIGN KEY (fk_Login_UUID)
    REFERENCES Login (UUID)
    ON DELETE SET NULL;
 
ALTER TABLE Organiza ADD CONSTRAINT FK_Organiza_1
    FOREIGN KEY (fk_Campeonato_ID)
    REFERENCES Campeonato (ID)
    ON DELETE RESTRICT;
 
ALTER TABLE Organiza ADD CONSTRAINT FK_Organiza_2
    FOREIGN KEY (fk_Login_UUID)
    REFERENCES Login (UUID)
    ON DELETE SET NULL;
 
ALTER TABLE Reside ADD CONSTRAINT FK_Reside_1
    FOREIGN KEY (fk_Endereco_ID)
    REFERENCES Endereco (ID)
    ON DELETE SET NULL;
 
ALTER TABLE Reside ADD CONSTRAINT FK_Reside_2
    FOREIGN KEY (fk_Login_UUID)
    REFERENCES Login (UUID)
    ON DELETE SET NULL;
 
ALTER TABLE Ocorre ADD CONSTRAINT FK_Ocorre_1
    FOREIGN KEY (fk_Campeonato_ID)
    REFERENCES Campeonato (ID)
    ON DELETE RESTRICT;
 
ALTER TABLE Ocorre ADD CONSTRAINT FK_Ocorre_2
    FOREIGN KEY (fk_Endereco_ID)
    REFERENCES Endereco (ID)
    ON DELETE SET NULL;

