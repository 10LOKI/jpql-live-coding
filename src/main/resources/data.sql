INSERT INTO clients (nom, prenom, adresse) VALUES
('Bennani', 'Youssef', '12 avenue Hassan II, 20000 Casablanca'),
('El Amrani', 'Fatima', '45 rue Mohammed V, 10000 Rabat'),
('Tahiri', 'Ahmed', '8 boulevard Zerktouni, 40000 Marrakech'),
('Ouazzani', 'Salma', '23 avenue des FAR, 30000 Fès'),
('Idrissi', 'Karim', '12 avenue Hassan II, 20000 Casablanca');

INSERT INTO commandes
(produit, quantite, prix, total_ht, tva, total_ttc, client_id) VALUES
('Laptop HP', 2, 899.99, 1799.98, 20.00, 2159.98, 1),
('Souris Logitech', 5, 29.99, 149.95, 20.00, 179.94, 1),
('Clavier Mécanique', 1, 149.99, 149.99, 20.00, 179.99, 2),
('Écran 27 pouces', 3, 349.99, 1049.97, 20.00, 1259.96, 2),
('Webcam HD', 2, 79.99, 159.98, 20.00, 191.98, 3),
('Casque Audio', 1, 199.99, 199.99, 20.00, 239.99, 4),
('SSD 1To', 4, 89.99, 359.96, 20.00, 431.95, 4),
('RAM 16Go', 2, 69.99, 139.98, 20.00, 167.98, 5),
('Câble HDMI', 10, 9.99, 99.90, 20.00, 119.88, 1),
('Hub USB', 3, 24.99, 74.97, 20.00, 89.96, 3);
