-- -----------------------------

create database project_resto;
-- TABLE USER
-- -----------------------------
CREATE TABLE "user" (
                        id SERIAL PRIMARY KEY,
                        nom VARCHAR(50) NOT NULL,
                        prenom VARCHAR(50) NOT NULL,
                        email VARCHAR(100) UNIQUE NOT NULL,
                        password VARCHAR(100) NOT NULL,
                        is_admin BOOLEAN DEFAULT FALSE
);

-- -----------------------------
-- TABLE PLAT
-- -----------------------------
CREATE TABLE plat (
                      id SERIAL PRIMARY KEY,
                      nom VARCHAR(100) NOT NULL,
                      description TEXT,
                      prix DECIMAL(6,2) NOT NULL,
                      categorie VARCHAR(50) NOT NULL
);

-- -----------------------------
-- TABLE COMMANDE
-- -----------------------------
CREATE TABLE commande (
                          id SERIAL PRIMARY KEY,
                          user_id INT REFERENCES "user"(id),
                          date_commande TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          statut VARCHAR(50) DEFAULT 'En attente', -- ex: En attente, Préparée, Livrée
                          paiement_en_ligne BOOLEAN DEFAULT FALSE
);

-- -----------------------------
-- TABLE COMMANDE_DETAIL
-- -----------------------------
CREATE TABLE commande_detail (
                                 id SERIAL PRIMARY KEY,
                                 commande_id INT REFERENCES commande(id) ON DELETE CASCADE,
                                 plat_id INT REFERENCES plat(id),
                                 quantite INT NOT NULL
);

-- -----------------------------
-- TABLE PANIER
-- -----------------------------
CREATE TABLE panier (
                        id SERIAL PRIMARY KEY,
                        user_id INT REFERENCES "user"(id),
                        plat_id INT REFERENCES plat(id),
                        quantite INT NOT NULL
);

-- -----------------------------
-- TABLE AVIS
-- -----------------------------
CREATE TABLE avis (
                      id SERIAL PRIMARY KEY,
                      user_id INT REFERENCES "user"(id),
                      plat_id INT REFERENCES plat(id),
                      note INT CHECK (note >=1 AND note <=5),
                      commentaire TEXT,
                      date_avis TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- -----------------------------
-- TABLE MESSAGE (support)
-- -----------------------------
CREATE TABLE message (
                         id SERIAL PRIMARY KEY,
                         user_id INT REFERENCES "user"(id),
                         message TEXT NOT NULL,
                         date_envoi TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         reponse TEXT
);

-- -----------------------------
-- DONNÉES TEST
-- -----------------------------
-- Users
INSERT INTO "user" (nom, prenom, email, password, is_admin) VALUES
                                                                ('Dupont','Jean','jean.dupont@test.com','1234',FALSE),
                                                                ('Martin','Alice','alice.martin@test.com','1234',FALSE),
                                                                ('Admin','Admin','admin@test.com','admin',TRUE);

-- Plats
INSERT INTO plat (nom, description, prix, categorie) VALUES
                                                         ('Margherita', 'Tomate, mozzarella', 8.5, 'Pizza'),
                                                         ('Pepperoni', 'Tomate, mozzarella, pepperoni', 9.5, 'Pizza'),
                                                         ('Burger Maison', 'Steak, fromage, salade', 10.0, 'Burger'),
                                                         ('Salade César', 'Poulet, laitue, parmesan', 9.0, 'Salade');

-- Panier test
INSERT INTO panier (user_id, plat_id, quantite) VALUES
                                                    (1,1,2),
                                                    (1,3,1);

-- Commande test
INSERT INTO commande (user_id, statut, paiement_en_ligne) VALUES
                                                              (1, 'Livrée', TRUE),
                                                              (2, 'En attente', FALSE);

-- Commande detail
INSERT INTO commande_detail (commande_id, plat_id, quantite) VALUES
                                                                 (1,1,2),
                                                                 (1,3,1),
                                                                 (2,2,1);

-- Avis test
INSERT INTO avis (user_id, plat_id, note, commentaire) VALUES
                                                           (1,1,5,'Très bon !'),
                                                           (2,3,4,'Correct');

-- Messages test
INSERT INTO message (user_id, message) VALUES
                                           (1,'Bonjour, je n’ai pas reçu ma commande.'),
                                           (2,'Comment puis-je payer en ligne ?');
