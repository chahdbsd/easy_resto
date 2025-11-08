-- ================================
-- 1️⃣ Nettoyage des anciennes tables
-- ================================
DROP TABLE IF EXISTS paiement CASCADE;
DROP TABLE IF EXISTS message CASCADE;
DROP TABLE IF EXISTS avis CASCADE;
DROP TABLE IF EXISTS panier CASCADE;
DROP TABLE IF EXISTS commande_detail CASCADE;
DROP TABLE IF EXISTS commande CASCADE;
DROP TABLE IF EXISTS plat CASCADE;
DROP TABLE IF EXISTS utilisateur CASCADE;

-- ================================
-- 2️⃣ Création des tables
-- ================================

-- Table utilisateur
CREATE TABLE utilisateur (
                             id SERIAL PRIMARY KEY,
                             nom VARCHAR(50) NOT NULL,
                             prenom VARCHAR(50) NOT NULL,
                             email VARCHAR(100) UNIQUE NOT NULL,
                             mot_de_passe VARCHAR(100) NOT NULL,
                             is_admin BOOLEAN DEFAULT FALSE
);

-- Table plat
CREATE TABLE plat (
                      id SERIAL PRIMARY KEY,
                      nom VARCHAR(100) NOT NULL,
                      description TEXT,
                      prix DECIMAL(6,2) NOT NULL,
                      categorie VARCHAR(50) NOT NULL
);

-- Table commande
CREATE TABLE commande (
                          id SERIAL PRIMARY KEY,
                          user_id INT NOT NULL REFERENCES utilisateur(id) ON DELETE CASCADE,
                          date_commande TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          statut VARCHAR(50) DEFAULT 'En attente',
                          paiement_en_ligne BOOLEAN DEFAULT FALSE
);

-- Table commande_detail
CREATE TABLE commande_detail (
                                 id SERIAL PRIMARY KEY,
                                 commande_id INT NOT NULL REFERENCES commande(id) ON DELETE CASCADE,
                                 plat_id INT NOT NULL REFERENCES plat(id),
                                 quantite INT NOT NULL
);

-- Table panier
CREATE TABLE panier (
                        user_id INT NOT NULL REFERENCES utilisateur(id) ON DELETE CASCADE,
                        plat_id INT NOT NULL REFERENCES plat(id),
                        quantite INT NOT NULL,
                        PRIMARY KEY(user_id, plat_id) -- permet ON CONFLICT
);

-- Table avis
CREATE TABLE avis (
                      id SERIAL PRIMARY KEY,
                      user_id INT NOT NULL REFERENCES utilisateur(id) ON DELETE CASCADE,
                      plat_id INT NOT NULL REFERENCES plat(id),
                      note INT CHECK(note >= 1 AND note <= 5),
                      commentaire TEXT,
                      date_avis TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table message (support)
CREATE TABLE message (
                         id SERIAL PRIMARY KEY,
                         user_id INT NOT NULL REFERENCES utilisateur(id) ON DELETE CASCADE,
                         message TEXT NOT NULL,
                         date_envoi TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         reponse TEXT
);

-- Table paiement
CREATE TABLE paiement (
                          id SERIAL PRIMARY KEY,
                          commande_id INT NOT NULL REFERENCES commande(id) ON DELETE CASCADE,
                          montant NUMERIC(10,2) NOT NULL,
                          methode VARCHAR(50) NOT NULL, -- 'en ligne' ou 'à la récupération'
                          effectue BOOLEAN DEFAULT FALSE,
                          date_paiement TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ================================
-- 3️⃣ Jeu de données initial
-- ================================

-- Utilisateurs
INSERT INTO utilisateur (nom, prenom, email, mot_de_passe, is_admin) VALUES
                                                                         ('Dupont','Jean','jean.dupont@test.com','1234',FALSE),
                                                                         ('Martin','Alice','alice.martin@test.com','1234',FALSE),
                                                                         ('Admin','Admin','admin@test.com','admin',TRUE);

-- Plats
INSERT INTO plat (nom, description, prix, categorie) VALUES
                                                         ('Margherita', 'Tomate, mozzarella', 8.50, 'Pizza'),
                                                         ('Pepperoni', 'Tomate, mozzarella, pepperoni', 9.50, 'Pizza'),
                                                         ('Burger Maison', 'Steak, fromage, salade', 10.00, 'Burger'),
                                                         ('Salade César', 'Poulet, laitue, parmesan', 9.00, 'Salade'),
                                                         ('Tiramisu', 'Dessert italien classique', 5.00, 'Dessert');