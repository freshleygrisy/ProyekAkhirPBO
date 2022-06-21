-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 21, 2022 at 06:13 PM
-- Server version: 10.4.21-MariaDB
-- PHP Version: 7.3.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `apotek`
--

-- --------------------------------------------------------

--
-- Table structure for table `anggota`
--

CREATE TABLE `anggota` (
  `Nama_lengkap` varchar(50) NOT NULL,
  `Alamat` varchar(100) NOT NULL,
  `Umur` varchar(2) NOT NULL,
  `Lulusan` varchar(50) NOT NULL,
  `Nomor_shift` varchar(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `anggota`
--

INSERT INTO `anggota` (`Nama_lengkap`, `Alamat`, `Umur`, `Lulusan`, `Nomor_shift`) VALUES
('gris', 'Diski', '', 'S1 Kedokteran', '05'),
('hartanto', 'dusun III jalan Kemayoran Binjai', '21', 'S1 Farmasi', '03');

-- --------------------------------------------------------

--
-- Table structure for table `obat`
--

CREATE TABLE `obat` (
  `Nama_obat` varchar(50) NOT NULL,
  `Kegunaan` varchar(100) NOT NULL,
  `Harga` varchar(50) NOT NULL,
  `Dosis` varchar(50) NOT NULL,
  `Untuk_usia` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `obat`
--

INSERT INTO `obat` (`Nama_obat`, `Kegunaan`, `Harga`, `Dosis`, `Untuk_usia`) VALUES
('mixagrip flu dan batuk', 'meringankan gejala flu, pusing, demam, dan batuk.', '5000', '4 tablet', '12-60tahun'),
('panadol', 'sakit kepala, nyeri, sakit gigi', '10000', '2 tablet', '15-70 tahun');

-- --------------------------------------------------------

--
-- Table structure for table `pembelian`
--

CREATE TABLE `pembelian` (
  `Nama_obat` varchar(50) NOT NULL,
  `Harga` varchar(50) NOT NULL,
  `Jumlah_yang_dibeli` varchar(50) NOT NULL,
  `Nama_pembeli` varchar(50) NOT NULL,
  `Tanggal_pembelian` date NOT NULL,
  `Nomor_pembelian` longtext NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `pembelian`
--

INSERT INTO `pembelian` (`Nama_obat`, `Harga`, `Jumlah_yang_dibeli`, `Nama_pembeli`, `Tanggal_pembelian`, `Nomor_pembelian`) VALUES
('panadol', '10000', '3', 'hartanto', '2022-06-05', '345'),
('panadol', '10000', '5', 'hartanto', '2022-06-22', '');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `anggota`
--
ALTER TABLE `anggota`
  ADD UNIQUE KEY `Nama lengkap` (`Nama_lengkap`);

--
-- Indexes for table `obat`
--
ALTER TABLE `obat`
  ADD UNIQUE KEY `Nama obat` (`Nama_obat`);

--
-- Indexes for table `pembelian`
--
ALTER TABLE `pembelian`
  ADD UNIQUE KEY `Nomor pembelian` (`Nomor_pembelian`) USING HASH,
  ADD KEY `obat` (`Nama_obat`),
  ADD KEY `anggota` (`Nama_pembeli`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `pembelian`
--
ALTER TABLE `pembelian`
  ADD CONSTRAINT `anggota` FOREIGN KEY (`Nama_pembeli`) REFERENCES `anggota` (`Nama_lengkap`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `obat` FOREIGN KEY (`Nama_obat`) REFERENCES `obat` (`Nama_obat`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
