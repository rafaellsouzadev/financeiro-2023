CREATE TABLE categoria (
    id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50)NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO categoria (nome) VALUES(
    'Lazer'
);

INSERT INTO categoria (nome) VALUES(
    'Alimentação'
);

INSERT INTO categoria (nome) VALUES(
    'Fármacia'
);

INSERT INTO categoria (nome) VALUES(
    'Luz'
);

INSERT INTO categoria (nome) VALUES(
    'Casa'
);

INSERT INTO categoria (nome) VALUES(
    'Eletronicos'
);

INSERT INTO categoria (nome) VALUES(
    'Outros'
);