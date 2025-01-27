-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 27, 2025 at 01:11 PM
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
-- Database: `pedalaq`
--

-- --------------------------------------------------------

--
-- Table structure for table `abbonamento`
--

CREATE TABLE `abbonamento` (
  `id` bigint(20) NOT NULL,
  `dataFine` date DEFAULT NULL,
  `dataInizio` date DEFAULT NULL,
  `id_tariffa_abbonamento` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `abbonamento`
--

INSERT INTO `abbonamento` (`id`, `dataFine`, `dataInizio`, `id_tariffa_abbonamento`) VALUES
(1, '2025-01-28', '2025-01-18', 1),
(2, '2025-01-31', '2025-01-20', 1),
(3, '2025-01-27', '2025-01-01', 1);

-- --------------------------------------------------------

--
-- Table structure for table `accessorio`
--

CREATE TABLE `accessorio` (
  `id` bigint(20) NOT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `id_veicolo` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `citta`
--

CREATE TABLE `citta` (
  `id` bigint(20) NOT NULL,
  `lat` double NOT NULL,
  `lon` double NOT NULL,
  `nome` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `citta`
--

INSERT INTO `citta` (`id`, `lat`, `lon`, `nome`) VALUES
(1, 42.357342, 13.367412, 'L\'Aquila'),
(2, 42.661145, 13.698667, 'Teramo'),
(3, 42.34796, 14.16411, 'Chieti'),
(4, 42.461535, 14.220118, 'Pescara');

-- --------------------------------------------------------

--
-- Table structure for table `cittadino`
--

CREATE TABLE `cittadino` (
  `id` bigint(20) NOT NULL,
  `CF` varchar(255) DEFAULT NULL,
  `cognome` varchar(255) DEFAULT NULL,
  `lat` double NOT NULL,
  `lng` double NOT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `saldo` float NOT NULL,
  `id_abbonamento_attivo` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `cittadino`
--

INSERT INTO `cittadino` (`id`, `CF`, `cognome`, `lat`, `lng`, `nome`, `saldo`, `id_abbonamento_attivo`) VALUES
(1, 'DSMNRL01P27A345T', 'Di Simone', 42.372549, 13.357023, 'Andrea Luca', 0, 1),
(2, 'MZYNDA02P55A345F', 'Muzyka', 42.363443, 13.3445664, 'Nadia', 0, 2),
(3, 'NNTNDR02M12C632G', 'Iannotti', 42.36828, 13.332279, 'Andrea', 0, 3);

-- --------------------------------------------------------

--
-- Table structure for table `noleggio`
--

CREATE TABLE `noleggio` (
  `id` bigint(20) NOT NULL,
  `fineCorsa` datetime(6) DEFAULT NULL,
  `inizioCorsa` datetime(6) DEFAULT NULL,
  `id_prenotazione` bigint(20) DEFAULT NULL,
  `id_tariffa` bigint(20) DEFAULT NULL,
  `id_stallo_partenza` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `prenotazione`
--

CREATE TABLE `prenotazione` (
  `id` bigint(20) NOT NULL,
  `scadenza` datetime(6) DEFAULT NULL,
  `id_cittadino` bigint(20) DEFAULT NULL,
  `id_veicolo` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `stallo`
--

CREATE TABLE `stallo` (
  `id` bigint(20) NOT NULL,
  `descrizione` varchar(255) DEFAULT NULL,
  `lat` double NOT NULL,
  `lon` double NOT NULL,
  `maxPosti` int(11) NOT NULL,
  `id_citta` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `stallo`
--

INSERT INTO `stallo` (`id`, `descrizione`, `lat`, `lon`, `maxPosti`, `id_citta`) VALUES
(1, 'Stallo del polo di coppito', 42.367679, 13.352341, 10, 1),
(2, 'Stallo del polo di roio', 42.336983, 13.37676, 15, 1),
(3, 'Stallo del DSU', 42.355634, 13.399981, 5, 1),
(4, 'Stallo Movieplex', 42.36658, 13.372693, 10, 1);

-- --------------------------------------------------------

--
-- Table structure for table `tariffaabbonamento`
--

CREATE TABLE `tariffaabbonamento` (
  `id` bigint(20) NOT NULL,
  `costo` double NOT NULL,
  `dataFine` date DEFAULT NULL,
  `dataInizio` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tariffaabbonamento`
--

INSERT INTO `tariffaabbonamento` (`id`, `costo`, `dataFine`, `dataInizio`) VALUES
(1, 5, '2025-12-31', '2025-01-01'),
(2, 3, '2025-08-31', '2025-06-01');

-- --------------------------------------------------------

--
-- Table structure for table `tariffanoleggiopromozione`
--

CREATE TABLE `tariffanoleggiopromozione` (
  `id` bigint(20) NOT NULL,
  `codice` varchar(255) DEFAULT NULL,
  `costoAlMinuto` float NOT NULL,
  `dataFine` date DEFAULT NULL,
  `dataInizio` date DEFAULT NULL,
  `id_citta` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tariffanoleggiostandard`
--

CREATE TABLE `tariffanoleggiostandard` (
  `id` bigint(20) NOT NULL,
  `costoAlMinuto` float NOT NULL,
  `dataFine` date DEFAULT NULL,
  `dataInizio` date DEFAULT NULL,
  `id_citta` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `veicolo`
--

CREATE TABLE `veicolo` (
  `id` bigint(20) NOT NULL,
  `codiceSblocco` varchar(255) DEFAULT NULL,
  `stato` varchar(255) DEFAULT NULL,
  `id_stallo` bigint(20) DEFAULT NULL,
  `VEICOLO_TYPE` varchar(31) NOT NULL,
  `batteria` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `veicolo`
--

INSERT INTO `veicolo` (`id`, `codiceSblocco`, `stato`, `id_stallo`, `VEICOLO_TYPE`, `batteria`) VALUES
(1, 'AA22CC', 'Libero', 1, 'BICI', NULL),
(2, 'BB22CC', 'Libero', 1, 'BICIELETTRICA', 78),
(3, 'CC22CC', 'Libero', 1, 'MONOPATTINO', 99),
(4, 'DD22CC', 'Libero', 2, 'BICI', NULL),
(5, 'EE22CC', 'Libero', 3, 'BICIELETTRICA', 90),
(6, 'FF22CC', 'Libero', 4, 'MONOPATTINO', 42);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `abbonamento`
--
ALTER TABLE `abbonamento`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKqy8di27e5deyw6t63ka7x8c47` (`id_tariffa_abbonamento`);

--
-- Indexes for table `accessorio`
--
ALTER TABLE `accessorio`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKi9avlqyicj37di1qc9sox5tyo` (`id_veicolo`);

--
-- Indexes for table `citta`
--
ALTER TABLE `citta`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `cittadino`
--
ALTER TABLE `cittadino`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKgiqh2hff5frc5wy7xyynposn5` (`id_abbonamento_attivo`);

--
-- Indexes for table `noleggio`
--
ALTER TABLE `noleggio`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKoaaqf6mw28mq5f5py4d0lc5k7` (`id_prenotazione`),
  ADD KEY `FKd8nmwwpr2jpacsobjd94t23tr` (`id_tariffa`),
  ADD KEY `FK4099tqfgn3uidoyun5smoxnfo` (`id_stallo_partenza`);

--
-- Indexes for table `prenotazione`
--
ALTER TABLE `prenotazione`
  ADD PRIMARY KEY (`id`),
  ADD KEY `UK95wp5h66gxav6j1jtnn559xer` (`id_veicolo`) USING BTREE,
  ADD KEY `UKfwg4xvtg5kcqplr6cqsend931` (`id_cittadino`) USING BTREE;

--
-- Indexes for table `stallo`
--
ALTER TABLE `stallo`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKfjkl5ow0nh19gmwjey8ott3ml` (`id_citta`);

--
-- Indexes for table `tariffaabbonamento`
--
ALTER TABLE `tariffaabbonamento`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tariffanoleggiopromozione`
--
ALTER TABLE `tariffanoleggiopromozione`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKscnjx5d50oajaog22y4y542dg` (`id_citta`);

--
-- Indexes for table `tariffanoleggiostandard`
--
ALTER TABLE `tariffanoleggiostandard`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKb0a6ulii0sgjrrhf600kv1f9l` (`id_citta`);

--
-- Indexes for table `veicolo`
--
ALTER TABLE `veicolo`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK5rcbuft5ghwnex52qw6rj848i` (`id_stallo`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `abbonamento`
--
ALTER TABLE `abbonamento`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `accessorio`
--
ALTER TABLE `accessorio`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `citta`
--
ALTER TABLE `citta`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `cittadino`
--
ALTER TABLE `cittadino`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `noleggio`
--
ALTER TABLE `noleggio`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `prenotazione`
--
ALTER TABLE `prenotazione`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `stallo`
--
ALTER TABLE `stallo`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `tariffaabbonamento`
--
ALTER TABLE `tariffaabbonamento`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `tariffanoleggiopromozione`
--
ALTER TABLE `tariffanoleggiopromozione`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tariffanoleggiostandard`
--
ALTER TABLE `tariffanoleggiostandard`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `veicolo`
--
ALTER TABLE `veicolo`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `abbonamento`
--
ALTER TABLE `abbonamento`
  ADD CONSTRAINT `FKqy8di27e5deyw6t63ka7x8c47` FOREIGN KEY (`id_tariffa_abbonamento`) REFERENCES `tariffaabbonamento` (`id`);

--
-- Constraints for table `accessorio`
--
ALTER TABLE `accessorio`
  ADD CONSTRAINT `FKi9avlqyicj37di1qc9sox5tyo` FOREIGN KEY (`id_veicolo`) REFERENCES `veicolo` (`id`);

--
-- Constraints for table `cittadino`
--
ALTER TABLE `cittadino`
  ADD CONSTRAINT `FKs1udbmyydq6fv7b4vcfw3ycr5` FOREIGN KEY (`id_abbonamento_attivo`) REFERENCES `abbonamento` (`id`);

--
-- Constraints for table `noleggio`
--
ALTER TABLE `noleggio`
  ADD CONSTRAINT `FK4099tqfgn3uidoyun5smoxnfo` FOREIGN KEY (`id_stallo_partenza`) REFERENCES `stallo` (`id`),
  ADD CONSTRAINT `FK7ec5gc3dm4l3jk41bjgg35uh2` FOREIGN KEY (`id_prenotazione`) REFERENCES `prenotazione` (`id`),
  ADD CONSTRAINT `FKl6ouong92tejq8kx5sha0s1ka` FOREIGN KEY (`id_tariffa`) REFERENCES `tariffanoleggiopromozione` (`id`);

--
-- Constraints for table `prenotazione`
--
ALTER TABLE `prenotazione`
  ADD CONSTRAINT `FK2kddd5rw6gfsj1g4xlp0ly7qd` FOREIGN KEY (`id_veicolo`) REFERENCES `veicolo` (`id`),
  ADD CONSTRAINT `FKrl3pe7i6vy7v0rou5ncyqarli` FOREIGN KEY (`id_cittadino`) REFERENCES `cittadino` (`id`);

--
-- Constraints for table `stallo`
--
ALTER TABLE `stallo`
  ADD CONSTRAINT `FKfjkl5ow0nh19gmwjey8ott3ml` FOREIGN KEY (`id_citta`) REFERENCES `citta` (`id`);

--
-- Constraints for table `tariffanoleggiopromozione`
--
ALTER TABLE `tariffanoleggiopromozione`
  ADD CONSTRAINT `FKscnjx5d50oajaog22y4y542dg` FOREIGN KEY (`id_citta`) REFERENCES `citta` (`id`);

--
-- Constraints for table `tariffanoleggiostandard`
--
ALTER TABLE `tariffanoleggiostandard`
  ADD CONSTRAINT `FKb0a6ulii0sgjrrhf600kv1f9l` FOREIGN KEY (`id_citta`) REFERENCES `citta` (`id`);

--
-- Constraints for table `veicolo`
--
ALTER TABLE `veicolo`
  ADD CONSTRAINT `FK5rcbuft5ghwnex52qw6rj848i` FOREIGN KEY (`id_stallo`) REFERENCES `stallo` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
