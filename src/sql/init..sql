-- ================================
-- 1) Nettoyage (ordre des dépendances)
-- ================================
DROP TABLE IF EXISTS paiement        CASCADE;
DROP TABLE IF EXISTS message         CASCADE;
DROP TABLE IF EXISTS avis            CASCADE;
DROP TABLE IF EXISTS panier          CASCADE;
DROP TABLE IF EXISTS commande_plat   CASCADE;
DROP TABLE IF EXISTS commande        CASCADE;
DROP TABLE IF EXISTS plat            CASCADE;
DROP TABLE IF EXISTS utilisateur     CASCADE;

-- ================================
-- 2) Schéma cohérent avec l'app
-- ================================

-- Utilisateur (+ compat password / admin)
CREATE TABLE utilisateur (
                             id            SERIAL PRIMARY KEY,
                             nom           VARCHAR(50)  NOT NULL,
                             prenom        VARCHAR(50)  NOT NULL,
                             email         VARCHAR(100) NOT NULL UNIQUE,
                             mot_de_passe  VARCHAR(100),               -- ancien nom
                             password      VARCHAR(100),               -- nouveau nom (utilisé par l'app)
                             is_admin      BOOLEAN NOT NULL DEFAULT FALSE
);
-- garder synchro password/mot_de_passe
CREATE OR REPLACE FUNCTION sync_user_password() RETURNS trigger AS $$
BEGIN
    IF TG_OP = 'INSERT' OR TG_OP = 'UPDATE' THEN
        IF NEW.password IS NULL AND NEW.mot_de_passe IS NOT NULL THEN
            NEW.password := NEW.mot_de_passe;
        ELSIF NEW.password IS NOT NULL AND (NEW.mot_de_passe IS NULL OR NEW.mot_de_passe <> NEW.password) THEN
            NEW.mot_de_passe := NEW.password;
        END IF;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_sync_user_password ON utilisateur;
CREATE TRIGGER trg_sync_user_password
    BEFORE INSERT OR UPDATE ON utilisateur
    FOR EACH ROW EXECUTE FUNCTION sync_user_password();

-- Plat (+ disponibilité)
CREATE TABLE plat (
                      id           SERIAL PRIMARY KEY,
                      nom          VARCHAR(100) NOT NULL,
                      description  TEXT,
                      prix         NUMERIC(10,2) NOT NULL,
                      categorie    VARCHAR(50)   NOT NULL,
                      disponible   BOOLEAN NOT NULL DEFAULT TRUE
);

-- Commande (statut, date, paiement_en_ligne)
CREATE TABLE commande (
                          id                  SERIAL PRIMARY KEY,
                          user_id             INT NOT NULL REFERENCES utilisateur(id) ON DELETE CASCADE,
                          date_commande       TIMESTAMP NOT NULL DEFAULT NOW(),
                          statut              TEXT      NOT NULL DEFAULT 'En attente',
                          paiement_en_ligne   BOOLEAN   NOT NULL DEFAULT FALSE
);

-- Détails de commande (commande_plat) + prix_unitaire figé
CREATE TABLE commande_plat (
                               id             SERIAL PRIMARY KEY,
                               commande_id    INT NOT NULL REFERENCES commande(id) ON DELETE CASCADE,
                               plat_id        INT NOT NULL REFERENCES plat(id),
                               quantite       INT NOT NULL CHECK (quantite > 0),
                               prix_unitaire  NUMERIC(10,2)  -- rempli automatiquement si NULL
);

-- Trigger pour remplir prix_unitaire depuis plat.prix si non fourni
CREATE OR REPLACE FUNCTION fill_prix_unitaire() RETURNS trigger AS $$
BEGIN
    IF NEW.prix_unitaire IS NULL THEN
        SELECT p.prix INTO NEW.prix_unitaire FROM plat p WHERE p.id = NEW.plat_id;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_fill_prix_unitaire ON commande_plat;
CREATE TRIGGER trg_fill_prix_unitaire
    BEFORE INSERT OR UPDATE ON commande_plat
    FOR EACH ROW EXECUTE FUNCTION fill_prix_unitaire();

-- Panier (clé composite)
CREATE TABLE panier (
                        user_id   INT NOT NULL REFERENCES utilisateur(id) ON DELETE CASCADE,
                        plat_id   INT NOT NULL REFERENCES plat(id),
                        quantite  INT NOT NULL CHECK (quantite > 0),
                        PRIMARY KEY (user_id, plat_id)
);

-- Avis
CREATE TABLE avis (
                      id           SERIAL PRIMARY KEY,
                      user_id      INT NOT NULL REFERENCES utilisateur(id) ON DELETE CASCADE,
                      plat_id      INT NOT NULL REFERENCES plat(id) ON DELETE CASCADE,
                      note         INT NOT NULL CHECK (note BETWEEN 1 AND 5),
                      commentaire  TEXT,
                      date_avis    TIMESTAMP NOT NULL DEFAULT NOW()
);

-- Message (support) — conforme au code (contenu/date_message)
CREATE TABLE message (
                         id            SERIAL PRIMARY KEY,
                         user_id       INT NOT NULL REFERENCES utilisateur(id) ON DELETE CASCADE,
                         contenu       TEXT,                               -- texte du message
                         date_message  TIMESTAMP NOT NULL DEFAULT NOW()
);

-- Paiement — conforme au DAO (avec user_id, statut)
CREATE TABLE paiement (
                          id             SERIAL PRIMARY KEY,
                          commande_id    INT NOT NULL REFERENCES commande(id) ON DELETE CASCADE,
                          user_id        INT NOT NULL REFERENCES utilisateur(id) ON DELETE CASCADE,
                          montant        NUMERIC(10,2) NOT NULL,
                          methode        TEXT NOT NULL,                     -- 'en ligne' / 'au comptoir'...
                          statut         TEXT NOT NULL DEFAULT 'effectué',
                          date_paiement  TIMESTAMP NOT NULL DEFAULT NOW()
);

-- ================================
-- 3) Index utiles
-- ================================
CREATE INDEX idx_plat_disponible       ON plat(disponible);
CREATE INDEX idx_commande_user         ON commande(user_id);
CREATE INDEX idx_commande_statut       ON commande(statut);
CREATE INDEX idx_commande_plat_cmd     ON commande_plat(commande_id);
CREATE INDEX idx_commande_plat_plat    ON commande_plat(plat_id);
CREATE INDEX idx_panier_user           ON panier(user_id);
CREATE INDEX idx_avis_plat             ON avis(plat_id);
CREATE INDEX idx_avis_user             ON avis(user_id);
CREATE INDEX idx_message_user          ON message(user_id);
CREATE INDEX idx_paiement_user         ON paiement(user_id);

-- ================================
-- 4) Seed minimal
-- ================================
INSERT INTO utilisateur (nom, prenom, email, password, is_admin)
VALUES ('Admin','Root','admin@resto.local','admin123', TRUE)
ON CONFLICT (email) DO NOTHING;

INSERT INTO utilisateur (nom, prenom, email, password, is_admin)
VALUES ('Jean','Dupont','jean.dupont@test.com','1234', FALSE)
ON CONFLICT (email) DO NOTHING;

INSERT INTO utilisateur (nom, prenom, email, password, is_admin)
VALUES ('Alice','Martin','alice.martin@test.com','1234', FALSE)
ON CONFLICT (email) DO NOTHING;

INSERT INTO plat (nom, description, prix, categorie) VALUES
                                                         ('Margherita', 'Tomate, mozzarella', 8.50, 'Pizza'),
                                                         ('Pepperoni', 'Tomate, mozzarella, pepperoni', 9.50, 'Pizza'),
                                                         ('Burger Maison', 'Steak, fromage, salade', 10.00, 'Burger'),
                                                         ('Salade César', 'Poulet, laitue, parmesan', 9.00, 'Salade'),
                                                         ('Tiramisu', 'Dessert italien classique', 5.00, 'Dessert')
ON CONFLICT DO NOTHING;
