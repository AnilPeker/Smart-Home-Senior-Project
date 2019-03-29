-- phpMyAdmin SQL Dump
-- version 4.8.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Mar 29, 2019 at 01:35 AM
-- Server version: 5.7.24
-- PHP Version: 7.2.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `androiddeft`
--

-- --------------------------------------------------------

--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
CREATE TABLE IF NOT EXISTS `member` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `full_name` varchar(50) NOT NULL,
  `password_hash` varchar(256) NOT NULL,
  `salt` varchar(256) NOT NULL,
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=12327 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `member`
--

INSERT INTO `member` (`user_id`, `username`, `full_name`, `password_hash`, `salt`, `created_date`) VALUES
(12318, 'anil', 'Anil', '$2y$10$yS9ZaJZeTDCBMqXmUBT3FuUeyx0ucjYc33qJpIVUziyER8qSnYl9e', 'cdaad15a3ae3f82b43045c5dc083633b1e1e4fe435850e87c651568bdc922d49', '2019-03-29 03:13:39'),
(12319, 'anil1', 'Anil', '$2y$10$7L0H4OpahU2CVIMIOxQuBeP6rc/FC6xh4UWJ2E1XKSTSclum2DAqC', '9363fbabea03af73de0f0378316f858231ff78df7079fb6db48bb39c89e2a444', '2019-03-29 03:13:53'),
(12320, 'anil12', 'Anil', '$2y$10$IQBhsEKuYb2ZsZaLFcBoAuf5njMI1fcrUJ52FFwm9Lx9ZqEdwJgRa', '9a03b824083f61b67866fb922f21ee3a6c2783ff07e77f2a26df2e72e17e6eab', '2019-03-29 03:14:55'),
(12321, 'anil123', 'Anil', '$2y$10$dunBs8KHi7jdUkzTTSwkHuhVWh9A4NhwSV3aLbJjbkhtWVXy5rJlS', '054ab6e2ac3d987186c60cea88c999ff38545c23f85b0459fc344b676f125562', '2019-03-29 03:15:37'),
(12322, 'peker', 'Anil', '$2y$10$.fnD4sxcqNtexe8JMAmpZebMkKdRLe27UvYgz9RURaJVDB7ps7RFe', '16020848ff12f6d5dff1af2b4824bf76038ece44b713c9aa4240a96e22a2c7df', '2019-03-29 03:16:51'),
(12323, 'peker1', 'Anil', '$2y$10$TghKdVG9mNYl4aytKwn.Be2DpN2aALSn29m6ZD8A4Pl5/o2SVYr/y', 'a211d8a21a4cc57cfce5dc0e90e22bc099c5cb5b6c508b0870af3608abb0871a', '2019-03-29 03:19:24'),
(12324, 'peker12', 'Anil', '$2y$10$//cLdnsOyow.ahkQccLgb.32V1Bhrj/Au6osDXP27t/zfiyWkvSjy', 'f92e3813cd169ea0d2dee435a57b7dbc89f6805f0923d3110f2be7c829686ddf', '2019-03-29 03:19:34'),
(12325, 'anil123577', 'Anil', '$2y$10$Cd0NQmW0tM/PASVcn8zkjONmHbrpy5FrT6uV7Oe0lVz2DasmUIthK', 'a971b8bbe0bfe32020b35e0101cc1c7d090ca66b0d1199d8a41eed11aa98939c', '2019-03-29 03:22:10'),
(12326, 'taicho', 'Tugce Zaferler', '$2y$10$bhE1641QZtnOerq7TaX51OOWRK14.7prNaut7n0N13We05PIlTfI6', '6d946c5203635e576190c99956cd49da7e43972942bc1b1a16fd4f1ca94df5f2', '2019-03-29 03:23:28');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
