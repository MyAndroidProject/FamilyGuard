-- phpMyAdmin SQL Dump
-- version 3.5.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Dec 17, 2014 at 09:38 AM
-- Server version: 5.5.24-log
-- PHP Version: 5.3.13

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `childtracker`
--

-- --------------------------------------------------------

--
-- Table structure for table `dependent`
--

CREATE TABLE IF NOT EXISTS `dependent` (
  `id` int(11) NOT NULL,
  `parent_id` int(11) NOT NULL,
  `child_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `dependent`
--

INSERT INTO `dependent` (`id`, `parent_id`, `child_id`) VALUES
(1, 1, 0);

-- --------------------------------------------------------

--
-- Table structure for table `login`
--

CREATE TABLE IF NOT EXISTS `login` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `Role` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

--
-- Dumping data for table `login`
--

INSERT INTO `login` (`id`, `username`, `password`, `Role`) VALUES
(1, 'Nikhil', 'tutu', 0),
(2, 'tutu', 'nikhil', 1);

-- --------------------------------------------------------

--
-- Table structure for table `service_mst`
--

CREATE TABLE IF NOT EXISTS `service_mst` (
  `id` int(11) NOT NULL,
  `dependent_id` int(11) NOT NULL,
  `child_location` varchar(20) NOT NULL,
  `battery_status` int(11) NOT NULL,
  `speed` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `service_mst`
--

INSERT INTO `service_mst` (`id`, `dependent_id`, `child_location`, `battery_status`, `speed`) VALUES
(1, 1, 'assdadas', 12, 13);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
