-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 05, 2024 at 06:49 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `profit_bd`
--

-- --------------------------------------------------------

--
-- Table structure for table `comments`
--

CREATE TABLE `comments` (
  `comment_id` int(50) NOT NULL,
  `forum_id` int(50) DEFAULT NULL,
  `user_id` int(50) DEFAULT NULL,
  `comment` varchar(250) DEFAULT NULL,
  `date_comment` timestamp NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `comments`
--

INSERT INTO `comments` (`comment_id`, `forum_id`, `user_id`, `comment`, `date_comment`) VALUES
(2, 4, 1, 'COMMENTAIRE DE TEST ', '2024-07-03 12:46:40'),
(3, 4, 1, 'TEST', '2024-07-03 12:47:06'),
(4, 4, 1, 'Test222222', '2024-07-03 12:51:41'),
(8, 5, 1, 'test for the second forum', '2024-07-03 13:05:58'),
(9, 5, 1, 'dddddd', '2024-07-03 13:37:05'),
(10, 6, 1, 'dfgddf', '2024-07-03 15:29:01'),
(14, 4, 1, 'sdfsfd', '2024-07-03 15:42:49'),
(19, 6, 1, 'dfsdffs', '2024-07-03 15:48:17'),
(20, 4, 1, 'dfsdffsdf', '2024-07-04 12:59:49');

-- --------------------------------------------------------

--
-- Table structure for table `cours`
--

CREATE TABLE `cours` (
  `id_cour` int(20) NOT NULL,
  `nom_c` varchar(30) NOT NULL,
  `duréé` varchar(30) NOT NULL,
  `event` varchar(30) NOT NULL,
  `niveau` varchar(30) NOT NULL,
  `nb_max` int(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `forum`
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

--
-- Dumping data for table `forum`
--

INSERT INTO `forum` (`id_forum`, `createur`, `titre`, `topique`, `contenu`, `comments`, `DateCreation`) VALUES
(4, 1, 'J\'aime le sport', 'SPORT', 'Ceci est un message de test.', NULL, '2024-07-02 12:05:20'),
(5, 1, 'Je cherche un coach.', 'SPORT', 'Je cherche un coach. Merci.', NULL, '2024-07-03 10:03:40'),
(6, 1, 'sdfsdfdf', 'Value 3', 'sdfsdfsdfsdf', NULL, '2024-07-03 15:26:59');

-- --------------------------------------------------------

--
-- Table structure for table `message`
--

CREATE TABLE `message` (
  `id_message` int(11) NOT NULL,
  `envoyeur` int(50) DEFAULT NULL,
  `recepteur` int(50) DEFAULT NULL,
  `messages` varchar(255) DEFAULT NULL,
  `status` varchar(30) DEFAULT NULL,
  `timestamp` timestamp NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `message`
--

INSERT INTO `message` (`id_message`, `envoyeur`, `recepteur`, `messages`, `status`, `timestamp`) VALUES
(1, 1, 2, 'test', NULL, '2024-06-27 19:39:09');

-- --------------------------------------------------------

--
-- Table structure for table `panier`
--

CREATE TABLE `panier` (
  `id_pan` int(20) NOT NULL,
  `total` int(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `planning`
--

CREATE TABLE `planning` (
  `id_pl` int(11) NOT NULL,
  `date` date NOT NULL,
  `planning` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `produit`
--

CREATE TABLE `produit` (
  `id_pro` int(11) NOT NULL,
  `nom` varchar(30) NOT NULL,
  `prix` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `progression`
--

CREATE TABLE `progression` (
  `id_prg` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `store`
--

CREATE TABLE `store` (
  `id_s` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(20) NOT NULL,
  `nom` varchar(30) NOT NULL,
  `prenom` varchar(50) NOT NULL,
  `mdp` varchar(30) NOT NULL,
  `email` varchar(30) NOT NULL,
  `ntel` int(30) NOT NULL,
  `role` varchar(30) NOT NULL,
  `age` int(20) NOT NULL,
  `poids` int(20) NOT NULL,
  `longueur` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `nom`, `prenom`, `mdp`, `email`, `ntel`, `role`, `age`, `poids`, `longueur`) VALUES
(1, 'Trabelsi', 'Baha', 'admin', 'btrabelsi@esprit.tn', 53586160, 'admin', 23, 1, 1),
(2, 'Mekni', 'Sara', 'admin', 'btrabelsi@esprit.tn', 0, '', 0, 0, 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `comments`
--
ALTER TABLE `comments`
  ADD PRIMARY KEY (`comment_id`),
  ADD KEY `forum_fk` (`forum_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `cours`
--
ALTER TABLE `cours`
  ADD PRIMARY KEY (`id_cour`);

--
-- Indexes for table `forum`
--
ALTER TABLE `forum`
  ADD PRIMARY KEY (`id_forum`),
  ADD KEY `CRT_FK` (`createur`),
  ADD KEY `CMT_FK` (`comments`);

--
-- Indexes for table `message`
--
ALTER TABLE `message`
  ADD PRIMARY KEY (`id_message`),
  ADD KEY `ENV_FK` (`envoyeur`),
  ADD KEY `REC_FK` (`recepteur`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `comments`
--
ALTER TABLE `comments`
  MODIFY `comment_id` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `forum`
--
ALTER TABLE `forum`
  MODIFY `id_forum` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `message`
--
ALTER TABLE `message`
  MODIFY `id_message` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `comments`
--
ALTER TABLE `comments`
  ADD CONSTRAINT `forum_fk` FOREIGN KEY (`forum_id`) REFERENCES `forum` (`id_forum`),
  ADD CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Constraints for table `forum`
--
ALTER TABLE `forum`
  ADD CONSTRAINT `CMT_FK` FOREIGN KEY (`comments`) REFERENCES `comments` (`comment_id`),
  ADD CONSTRAINT `CRT_FK` FOREIGN KEY (`createur`) REFERENCES `user` (`id`);

--
-- Constraints for table `message`
--
ALTER TABLE `message`
  ADD CONSTRAINT `ENV_FK` FOREIGN KEY (`envoyeur`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `REC_FK` FOREIGN KEY (`recepteur`) REFERENCES `user` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
