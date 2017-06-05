-- phpMyAdmin SQL Dump
-- version 3.5.8.1
-- http://www.phpmyadmin.net
--
-- Host: dd39116.kasserver.com
-- Erstellungszeit: 29. Mai 2017 um 19:44
-- Server Version: 5.6.33-nmm1-log
-- PHP-Version: 5.6.29-nmm1

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Datenbank: `d0261ae9`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(128) NOT NULL,
  `lastName` varchar(128) NOT NULL,
  `email` varchar(128) NOT NULL,
  `pwd` varchar(128) NOT NULL,
  `createdAt` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=91 ;

--
-- Daten für Tabelle `users`
--

INSERT INTO `users` (`id`, `firstName`, `lastName`, `email`, `pwd`, `createdAt`) VALUES
(1, 'Tester', 'Testet', 'test@test.at', 'tracksterTest#123', '2017-05-02'),
(7, 'pete', 'perg', 'p@p.at', '123', '0000-00-00'),
(90, 'alf', 'er', 'pa', 'pat', '2017-05-24');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
