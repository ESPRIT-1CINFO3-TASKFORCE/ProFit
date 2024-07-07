-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : dim. 07 juil. 2024 à 21:07
-- Version du serveur : 10.4.32-MariaDB
-- Version de PHP : 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `profit2_db`
--

-- --------------------------------------------------------

--
-- Structure de la table `commande`
--

CREATE TABLE `commande` (
  `id_c` int(20) NOT NULL,
  `client_id` int(30) NOT NULL,
  `etat` varchar(30) NOT NULL,
  `date_c` date NOT NULL,
  `total` int(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `commande`
--

INSERT INTO `commande` (`id_c`, `client_id`, `etat`, `date_c`, `total`) VALUES
(53, 1, 'Livré', '2024-07-06', 0),
(54, 1, 'Livré', '2024-07-07', 0),
(55, 1, 'En cours', '2024-07-07', 0),
(56, 1, 'En cours', '2024-07-07', 0),
(57, 1, 'Livré', '2024-07-07', 0),
(58, 1, 'En cours', '2024-07-07', 0),
(59, 1, 'Livré', '2024-07-07', 0),
(60, 1, 'En cours', '2024-07-07', 0),
(70, 1, 'En cours', '2024-07-07', 0),
(71, 1, 'En cours', '2024-07-07', 0),
(72, 1, 'En cours', '2024-07-07', 370),
(73, 1, 'En cours', '2024-07-07', 455),
(74, 1, 'En cours', '2024-07-07', 350),
(75, 1, 'En cours', '2024-07-07', 350),
(76, 1, 'Livré', '2024-07-07', 275);

-- --------------------------------------------------------

--
-- Structure de la table `comments`
--

CREATE TABLE `comments` (
  `comment_id` int(50) NOT NULL,
  `forum_id` int(50) DEFAULT NULL,
  `user_id` int(50) DEFAULT NULL,
  `comment` varchar(250) DEFAULT NULL,
  `date_comment` timestamp NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `forum`
--

CREATE TABLE `forum` (
  `id_forum` int(11) NOT NULL,
  `createur` int(50) DEFAULT NULL,
  `titre` varchar(50) DEFAULT NULL,
  `topique` varchar(50) DEFAULT NULL,
  `contenu` varchar(255) DEFAULT NULL,
  `comments` int(255) DEFAULT NULL,
  `DateCreation` timestamp NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `lingepanier`
--

CREATE TABLE `lingepanier` (
  `id_lp` int(30) NOT NULL,
  `id_pro` int(11) NOT NULL,
  `id_pan` int(11) NOT NULL,
  `quantite` int(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `message`
--

CREATE TABLE `message` (
  `id_message` int(11) NOT NULL,
  `envoyeur` int(50) DEFAULT NULL,
  `recepteur` int(50) DEFAULT NULL,
  `messages` varchar(255) DEFAULT NULL,
  `status` varchar(30) DEFAULT NULL,
  `timestamp` timestamp NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `panier`
--

CREATE TABLE `panier` (
  `id_pan` int(20) NOT NULL,
  `total` int(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `panier`
--

INSERT INTO `panier` (`id_pan`, `total`) VALUES
(25, 0);

-- --------------------------------------------------------

--
-- Structure de la table `produit`
--

CREATE TABLE `produit` (
  `id_pro` int(11) NOT NULL,
  `nom_p` varchar(30) NOT NULL,
  `prix` int(11) NOT NULL,
  `qnt` int(20) NOT NULL,
  `image` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `produit`
--

INSERT INTO `produit` (`id_pro`, `nom_p`, `prix`, `qnt`, `image`) VALUES
(26, 'Kit Fitness Femme', 250, 5, 'file:/C:/Users/DJAPPA/Desktop/ProFit/images/Kit%20Fitness%20Femme.jpg'),
(28, 'Whey Proteine', 350, 6, 'file:/C:/Users/DJAPPA/Desktop/ProFit/images/impact-proteine-premium-whey.jpg'),
(29, 'Sacs', 50, 10, 'file:/C:/Users/DJAPPA/Desktop/ProFit/images/sac.jpg'),
(30, 'Shaker', 15, 15, 'file:/C:/Users/DJAPPA/Desktop/ProFit/images/Shaker.jpg'),
(31, 'Barre protéinée', 5, 30, 'file:/C:/Users/DJAPPA/Desktop/ProFit/images/Barre%20protéinée.jpg'),
(32, 'Roue abdominale', 150, 5, 'file:/C:/Users/DJAPPA/Desktop/ProFit/images/Roue%20abdominale.jpg'),
(33, 'Haltére 5kg', 70, 15, 'file:/C:/Users/DJAPPA/Desktop/ProFit/images/Haltére%203kg.jpg'),
(38, 'Gants', 35, 15, 'file:/C:/Users/DJAPPA/Desktop/ProFit/images/gants.jpg');

-- --------------------------------------------------------

--
-- Structure de la table `progression`
--

CREATE TABLE `progression` (
  `id_prg` int(20) NOT NULL,
  `id` int(20) DEFAULT NULL,
  `poids` int(25) DEFAULT NULL,
  `longueur` decimal(4,2) DEFAULT NULL,
  `IMC` int(30) DEFAULT NULL,
  `date_inscri` date DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `masse_musc` int(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `progression`
--

INSERT INTO `progression` (`id_prg`, `id`, `poids`, `longueur`, `IMC`, `date_inscri`, `description`, `masse_musc`) VALUES
(2227, 2208, 73, 1.68, 26, '2024-07-05', 'Surpoids', NULL),
(2228, 2208, 70, 1.44, 34, '2005-08-22', 'Obésité', NULL),
(2230, 1234, 77, 1.77, 25, '2024-07-07', 'Poids normal', NULL),
(2231, 1234, 70, 1.98, 18, '2024-05-05', 'Insuffisance pondérale', NULL),
(2232, 1234, 75, 1.98, 19, '2024-06-05', 'Poids normal', NULL),
(2233, 1234, 80, 1.98, 20, '2024-07-05', 'Poids normal', NULL),
(2234, 1234, 90, 1.98, 23, '2024-03-05', 'Poids normal', NULL),
(2235, 1234, 80, 1.90, 8, '2024-06-07', 'Insuffisance pondérale', 0);

-- --------------------------------------------------------

--
-- Structure de la table `regimuser`
--

CREATE TABLE `regimuser` (
  `id_ligne` int(11) NOT NULL,
  `id_C` int(11) DEFAULT NULL,
  `id_R` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `regimuser`
--

INSERT INTO `regimuser` (`id_ligne`, `id_C`, `id_R`) VALUES
(11, 2208, 20),
(12, 1234, 21),
(13, 1234, 22),
(14, 1234, 23),
(15, 2208, 24),
(16, 72, 25),
(17, 2208, 26),
(18, 2208, 27),
(19, 2208, 28),
(20, 72, 29),
(21, 2208, 30),
(22, 72, 31),
(23, 72, 32),
(24, 72, 33),
(25, 2225, 34),
(26, 2226, 35),
(27, 2226, 36),
(28, 2208, 37),
(29, 72, 38),
(30, 72, 39),
(31, 72, 41),
(32, 2208, 42);

-- --------------------------------------------------------

--
-- Structure de la table `régime`
--

CREATE TABLE `régime` (
  `id_regime` int(20) NOT NULL,
  `id_client` int(20) DEFAULT NULL,
  `nom_regime` varchar(100) NOT NULL,
  `description` varchar(10000) DEFAULT NULL,
  `date_debut` date DEFAULT NULL,
  `date_fin` date DEFAULT NULL,
  `semaine` enum('Lundi','Mardi','Mercredi','Jeudi','Vendredi','Samedi','Dimanche') DEFAULT NULL,
  `repas` enum('Matin','Snacks1','Midi','Snacks2','Soir') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `régime`
--

INSERT INTO `régime` (`id_regime`, `id_client`, `nom_regime`, `description`, `date_debut`, `date_fin`, `semaine`, `repas`) VALUES
(20, 2208, 'perte de poids', NULL, '2024-06-22', '2024-07-23', NULL, NULL),
(21, 1234, 'Gagne de poids', NULL, '2024-06-22', '2024-07-25', NULL, NULL),
(22, 1234, 'gagne poids', NULL, '2024-07-05', '2024-07-10', NULL, NULL),
(23, 1234, 'perte poids', NULL, '2000-08-22', '2008-08-22', NULL, NULL),
(24, 2208, 'healthy', NULL, '2000-08-22', '2000-09-22', NULL, NULL),
(25, 72, 'Healthy', NULL, '2000-08-22', '2000-06-23', NULL, NULL),
(26, 2208, 'Healthy', NULL, '2000-08-22', '2000-06-23', NULL, NULL),
(27, 2208, 'Healthy', NULL, '2000-08-22', '2000-06-23', NULL, NULL),
(28, 2208, 'helathy', NULL, '2000-08-22', '2000-08-22', NULL, NULL),
(29, 72, 'helathy', NULL, '2000-08-22', '2000-08-22', NULL, NULL),
(30, 2208, 'healthy', NULL, '2023-08-22', '2007-08-22', NULL, NULL),
(31, 72, 'healthy', NULL, '2000-08-22', '2000-08-22', NULL, NULL),
(32, 72, 'healthy', NULL, '2000-08-22', '2000-08-22', NULL, NULL),
(33, 72, 'healthy', NULL, '2000-08-22', '2000-08-22', NULL, NULL),
(34, 2225, 'pertepoids', NULL, '2024-07-05', '2024-07-10', NULL, NULL),
(35, 2226, 'pertepoids', NULL, '2024-07-05', '2024-07-15', NULL, NULL),
(36, 2226, 'healthy', NULL, '2024-07-05', '2024-07-15', NULL, NULL),
(37, 2208, 'healthy', NULL, '2024-07-05', '2024-07-15', NULL, NULL),
(38, 72, 'perte poids', NULL, '2024-07-05', '2024-07-15', NULL, NULL),
(39, 72, 'gagne poids', NULL, '2024-07-05', '2024-07-13', NULL, NULL),
(41, 72, 'perte de poids', NULL, '2024-07-07', '2024-07-13', NULL, NULL),
(42, 2208, 'healthy', NULL, '2024-07-07', '2024-07-15', NULL, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `age` int(11) NOT NULL,
  `poids` int(11) NOT NULL,
  `longeur` int(11) NOT NULL,
  `note_c` int(11) NOT NULL,
  `note_n` int(11) NOT NULL,
  `n_tel` int(11) NOT NULL,
  `nom` varchar(50) NOT NULL,
  `prenom` varchar(50) NOT NULL,
  `login` varchar(100) NOT NULL,
  `mdp` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `role` varchar(255) DEFAULT NULL,
  `active` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `users`
--

INSERT INTO `users` (`id`, `age`, `poids`, `longeur`, `note_c`, `note_n`, `n_tel`, `nom`, `prenom`, `login`, `mdp`, `email`, `role`, `active`) VALUES
(38, 0, 0, 0, 0, 0, 20676913, 'Moalla', 'Malek', 'malek94', 'malek94', 'malek.moalla888@gmail.com', 'ADMIN', 0),
(45, 37, 0, 0, 0, 0, 51140140, 'Karchoud', 'Emir', 'ekarchoud37', 'Z{–U6C²‛', 'karchoud.mahdi@gmail.com', 'Adherent', 1),
(52, 35, 0, 0, 0, 0, 50214789, 'Sassi', 'Chahine', 'csassi24', 'csassi24', 'chahine.sassi@esprit.tn', 'COACH', 0),
(68, 23, 0, 0, 0, 0, 20365247, 'Aouadi', 'Ines', 'iaouadi23', '\'h·÷₤w0¢', 'ines.aouadi@esprit.tn', 'ADMIN', NULL),
(70, 23, 0, 0, 0, 0, 65987412, 'Trabelsi', 'Bahaeddine', 'btrabelsi23', 'N&$W5!lR', 'bahaeddine.trabelsi@esprit.tn', 'ADHERENT', NULL),
(1234, 28, 0, 0, 0, 0, 20365285, 'Hajri', 'Aziz', 'ahajri28', '¾₵¦qHk;9', 'aziz.hajri@esprit.tn', 'ADHERENT', NULL),
(2208, 23, 0, 0, 0, 0, 54376915, 'Rahmouni', 'Ameni', 'arahmouni22', 'arahmouni22', 'ameni.rahmouni@esprit.tn', 'Adherant', 1);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `commande`
--
ALTER TABLE `commande`
  ADD PRIMARY KEY (`id_c`),
  ADD KEY `fk_client` (`client_id`);

--
-- Index pour la table `comments`
--
ALTER TABLE `comments`
  ADD PRIMARY KEY (`comment_id`),
  ADD KEY `forum_fk` (`forum_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Index pour la table `forum`
--
ALTER TABLE `forum`
  ADD PRIMARY KEY (`id_forum`),
  ADD KEY `CRT_FK` (`createur`),
  ADD KEY `CMT_FK` (`comments`);

--
-- Index pour la table `lingepanier`
--
ALTER TABLE `lingepanier`
  ADD PRIMARY KEY (`id_lp`),
  ADD KEY `fk_Ligne_panier` (`id_pan`),
  ADD KEY `fk_ligne_produitt` (`id_pro`);

--
-- Index pour la table `message`
--
ALTER TABLE `message`
  ADD PRIMARY KEY (`id_message`),
  ADD KEY `ENV_FK` (`envoyeur`),
  ADD KEY `REC_FK` (`recepteur`);

--
-- Index pour la table `panier`
--
ALTER TABLE `panier`
  ADD PRIMARY KEY (`id_pan`);

--
-- Index pour la table `produit`
--
ALTER TABLE `produit`
  ADD PRIMARY KEY (`id_pro`);

--
-- Index pour la table `progression`
--
ALTER TABLE `progression`
  ADD PRIMARY KEY (`id_prg`),
  ADD KEY `fk_user` (`id`);

--
-- Index pour la table `regimuser`
--
ALTER TABLE `regimuser`
  ADD PRIMARY KEY (`id_ligne`),
  ADD KEY `fk_idC` (`id_C`),
  ADD KEY `fk_idR` (`id_R`);

--
-- Index pour la table `régime`
--
ALTER TABLE `régime`
  ADD PRIMARY KEY (`id_regime`),
  ADD KEY `FK_userss` (`id_client`);

--
-- Index pour la table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `commande`
--
ALTER TABLE `commande`
  MODIFY `id_c` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=77;

--
-- AUTO_INCREMENT pour la table `comments`
--
ALTER TABLE `comments`
  MODIFY `comment_id` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT pour la table `forum`
--
ALTER TABLE `forum`
  MODIFY `id_forum` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT pour la table `lingepanier`
--
ALTER TABLE `lingepanier`
  MODIFY `id_lp` int(30) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=864;

--
-- AUTO_INCREMENT pour la table `message`
--
ALTER TABLE `message`
  MODIFY `id_message` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=50;

--
-- AUTO_INCREMENT pour la table `panier`
--
ALTER TABLE `panier`
  MODIFY `id_pan` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT pour la table `produit`
--
ALTER TABLE `produit`
  MODIFY `id_pro` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;

--
-- AUTO_INCREMENT pour la table `progression`
--
ALTER TABLE `progression`
  MODIFY `id_prg` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2240;

--
-- AUTO_INCREMENT pour la table `regimuser`
--
ALTER TABLE `regimuser`
  MODIFY `id_ligne` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- AUTO_INCREMENT pour la table `régime`
--
ALTER TABLE `régime`
  MODIFY `id_regime` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=43;

--
-- AUTO_INCREMENT pour la table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2209;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `lingepanier`
--
ALTER TABLE `lingepanier`
  ADD CONSTRAINT `fk_Ligne_panier` FOREIGN KEY (`id_pan`) REFERENCES `panier` (`id_pan`),
  ADD CONSTRAINT `fk_ligne_produitt` FOREIGN KEY (`id_pro`) REFERENCES `produit` (`id_pro`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
