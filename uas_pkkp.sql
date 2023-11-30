-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 30, 2023 at 03:13 PM
-- Server version: 10.4.21-MariaDB
-- PHP Version: 8.0.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `uas_pkkp`
--

-- --------------------------------------------------------

--
-- Table structure for table `administrasi`
--

CREATE TABLE `administrasi` (
  `no_seleksi` varchar(50) NOT NULL,
  `nik` char(16) NOT NULL,
  `nama_lengkap` varchar(255) NOT NULL,
  `keterangan` varchar(30) NOT NULL,
  `kab_asal` varchar(255) NOT NULL,
  `kab_penempatan` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `administrasi`
--

INSERT INTO `administrasi` (`no_seleksi`, `nik`, `nama_lengkap`, `keterangan`, `kab_asal`, `kab_penempatan`) VALUES
('1', '3322100908761', 'Wanti Warniya', 'LOLOS', 'Wonosobo', 'Grobogan'),
('10', '3322578900765', 'Yanti Wati', 'TIDAK LOLOS', 'Magelang', 'Banyumas'),
('11', '3322777655432', 'Sri Wadani', 'LOLOS', 'Wonogiri', 'Banyumas'),
('12', '3322100908762', 'Warto Warniyem', 'LOLOS', 'Wonosobo', 'Grobogan'),
('13', '33221897873', 'Suksma Jana', 'LOLOS', 'Magelang', 'Grobogan'),
('2', '3322185812876', 'Yanto Supratman', 'LOLOS', 'Grobogan', 'Grobogan'),
('3', '3322187908761', 'Sugiono Slamet', 'TIDAK LOLOS', 'Blora', 'Grobogan'),
('4', '3322190312341', 'Yarto Bagus', 'TIDAK LOLOS', 'Cilacap', 'Grobogan'),
('5', '3322198109902', 'Tukijan Putro', 'LOLOS', 'Grobogan', 'Banyumas'),
('6', '3322198706531', 'Warto Vario', 'LOLOS', 'Magelang', 'Grobogan'),
('7', '3322435675432', 'Sri Wanto', 'LOLOS', 'Banyumas', 'Banyumas'),
('8', '3322451178908', 'Sindoro', 'LOLOS', 'Blora', 'Grobogan'),
('9', '3322564785231', 'Tukiman', 'LOLOS', 'Cilacap', 'Banyumas');

-- --------------------------------------------------------

--
-- Table structure for table `kabupaten`
--

CREATE TABLE `kabupaten` (
  `kode_kab` int(11) NOT NULL,
  `nama_kab` varchar(255) DEFAULT NULL,
  `kuota` int(11) DEFAULT NULL,
  `jumlah_kecamatan` int(11) DEFAULT NULL,
  `jumlah_desa_penempatan` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `kabupaten`
--

INSERT INTO `kabupaten` (`kode_kab`, `nama_kab`, `kuota`, `jumlah_kecamatan`, `jumlah_desa_penempatan`) VALUES
(3302, 'Banyumas', 3, 27, 3),
(3315, 'Grobogan', 3, 19, 3);

-- --------------------------------------------------------

--
-- Table structure for table `kecamatan`
--

CREATE TABLE `kecamatan` (
  `kode_kec` int(11) NOT NULL,
  `nama_kec` varchar(255) DEFAULT NULL,
  `nama_kab` varchar(255) DEFAULT NULL,
  `jumlah_desa` int(11) DEFAULT NULL,
  `jumlah_kuota` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `kecamatan`
--

INSERT INTO `kecamatan` (`kode_kec`, `nama_kec`, `nama_kab`, `jumlah_desa`, `jumlah_kuota`) VALUES
(330203, 'Jatilawang', 'Banyumas', 11, 1),
(330214, 'Ajibarang', 'Banyumas', 14, 1),
(330215, 'Gumelar', 'Banyumas', 10, 1),
(331505, 'Geyer', 'Grobogan', 13, 1),
(331508, 'Gabus', 'Grobogan', 14, 1),
(331514, 'Brati', 'Grobogan', 9, 1);

-- --------------------------------------------------------

--
-- Table structure for table `kelurahan`
--

CREATE TABLE `kelurahan` (
  `kode_kel` char(11) NOT NULL,
  `nama_kel` varchar(255) DEFAULT NULL,
  `nama_kec` varchar(255) DEFAULT NULL,
  `nama_kab` varchar(255) DEFAULT NULL,
  `kuota` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `kelurahan`
--

INSERT INTO `kelurahan` (`kode_kel`, `nama_kel`, `nama_kec`, `nama_kab`, `kuota`) VALUES
('53163', 'Ciberung', 'Ajibarang', 'Banyumas', 1),
('53165', 'Cihonje', 'Gumelar', 'Banyumas', 1),
('53174', 'Adisara', 'Jatilawang', 'Banyumas', 1),
('58153', 'Katekan', 'Brati', 'Grobogan', 1),
('58172', 'Bangsri', 'Geyer', 'Grobogan', 1),
('59173', 'Babalan', 'Gabus', 'Grobogan', 1);

-- --------------------------------------------------------

--
-- Table structure for table `peserta`
--

CREATE TABLE `peserta` (
  `nik` char(16) NOT NULL,
  `kab_asal` varchar(255) DEFAULT NULL,
  `kab_penempatan` varchar(255) DEFAULT NULL,
  `nama_lengkap` varchar(255) DEFAULT NULL,
  `ttl` varchar(255) DEFAULT NULL,
  `jenis_kelamin` varchar(15) DEFAULT NULL,
  `alamat` varchar(255) DEFAULT NULL,
  `agama` varchar(15) DEFAULT NULL,
  `no_telp` varchar(15) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `universitas` varchar(50) DEFAULT NULL,
  `fakultas` varchar(50) DEFAULT NULL,
  `jurusan` varchar(50) DEFAULT NULL,
  `tahun_lulus` char(4) DEFAULT NULL,
  `ipk` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `peserta`
--

INSERT INTO `peserta` (`nik`, `kab_asal`, `kab_penempatan`, `nama_lengkap`, `ttl`, `jenis_kelamin`, `alamat`, `agama`, `no_telp`, `email`, `universitas`, `fakultas`, `jurusan`, `tahun_lulus`, `ipk`) VALUES
('3322100908761', 'Wonosobo', 'Grobogan', 'Wanti Warniya', 'Wonosobo , 19-09-1999', 'Perempuan', 'Jalan Wonosobo	', 'Katholik', '087765432143', 'wanti@gmail.com', 'UNDIP', 'Ekonomika dan Bisnis', 'Manajemen', '2020', 3.7),
('3322100908762', 'Wonosobo', 'Grobogan', 'Warto Warniyem', 'Wonosobo , 19-10-2000', 'Laki-Laki', 'Jalan Wonosobo	', 'Islam', '087765432142', 'warto@gmail.com', 'UNDIP', 'Ekonomika dan Bisnis', 'Manajemen', '2020', 3.3),
('3322185812876', 'Grobogan', 'Grobogan', 'Yanto Supratman', 'Grobogan , 18-09-2000', 'Laki-Laki', 'Jalan Grobogan', 'Islam', '085888876654', 'yanto@gmail.com', 'UNDIP', 'Psikologi', 'Psikologi', '2021', 3.6),
('3322187908761', 'Blora', 'Grobogan', 'Sugiono Slamet', 'Blora , 19-02-2000', 'Laki-Laki', 'Jalan Blora	', 'Hindu', '089821193312', 'sugi@gmail.com', 'UNDIP', 'Perikanan dan Ilmu Kelautan', 'Akuakultur', '2021', 3.1),
('33221897873', 'Magelang', 'Grobogan', 'Suksma Jana', 'Magelang , 01-09-2000', 'Perempuan', 'Jalan Magelang', 'Islam', '089876782187', 'suksma@gmail.com', 'UNDIP', 'Ekonomi dan Bisnis', 'Manajemen', '2021', 3.2),
('3322190312341', 'Cilacap', 'Grobogan', 'Yarto Bagus', 'Cilacap , 29-02-2000', 'Laki-Laki', 'Jalan Cilacap	', 'Katholik', '088761231908', 'yarto@gmail.com', 'UNNES', 'Ilmu Pendidikan dan Psikologi', 'Pendidikan Luar Sekolah', '2021', 2.8),
('3322198109902', 'Grobogan', 'Banyumas', 'Tukijan Putro', 'Banyumas , 10-01-2000', 'Laki-Laki', 'Jalan Grobogan ', 'Katholik', '089876543213', 'tuki@gmail.com', 'UNDIP', 'Ekonomika dan Bisnis', 'Ekonomi', '2021', 3.5),
('3322198706531', 'Magelang', 'Grobogan', 'Warto Vario', 'Magelang , 20-02-2000', 'Laki-Laki', 'Jalan Magelang', 'Islam', '085321345321', 'warto@gmail.com', 'UNNES', 'Teknik', 'Sipil', '2021', 2.9),
('3322435675432', 'Banyumas', 'Banyumas', 'Sri Wanto', 'Banyumas , 29-01-2000', 'Laki-Laki', 'Jalan Banyumas	', 'Protestan', '081324544217', 'wanto@gmail.com', 'UNDIP', 'Peternakan dan Pertanian', 'Agribisnis', '2021', 3.1),
('3322451178908', 'Blora', 'Grobogan', 'Sindoro', 'Blora , 05-01-2000', 'Laki-Laki', 'Jalan Blora', 'Islam', '08987976281', 'doro@gmail.com', 'UNNES', 'Ilmu Sosial', 'Geografi', '2021', 3.3),
('3322564785231', 'Cilacap', 'Banyumas', 'Tukiman', 'Cilacap , 17-02-2000', 'Laki-Laki', 'Jalan Cilacap', 'Islam', '088975432143', 'tukiman@gmail.com', 'UNNES', 'Ilmu Sosial', 'Geografi', '2021', 3.2),
('3322578900765', 'Magelang', 'Banyumas', 'Yanti Wati', 'Magelang , 19-01-12', 'Perempuan', 'Jalan Magelang', 'Islam', '088765554221', 'yanti@gmail.com', 'UNNES', 'Bahasa dan Seni', 'Sastra Indonesia', '2021', 3.2),
('3322777655432', 'Wonogiri', 'Banyumas', 'Sri Wadani', 'Wonogiri , 01-04-2000', 'Perempuan', 'Jalan Wonogiri', 'Islam', '085552123443', 'wadani@gmail.com', 'UNNES', 'Ilmu Sosial', 'Ilmu Politik', '2021', 3.5);

-- --------------------------------------------------------

--
-- Table structure for table `seleksi_administrasi`
--

CREATE TABLE `seleksi_administrasi` (
  `no_seleksi` varchar(50) NOT NULL,
  `tgl_seleksi` date NOT NULL,
  `nik` char(16) NOT NULL,
  `nama_lengkap` varchar(255) NOT NULL,
  `keterangan` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `seleksi_administrasi`
--

INSERT INTO `seleksi_administrasi` (`no_seleksi`, `tgl_seleksi`, `nik`, `nama_lengkap`, `keterangan`) VALUES
('1', '2023-07-04', '3322100908761', 'Wanti Warniya', 'LOLOS'),
('10', '2023-07-04', '3322578900765', 'Yanti Wati', 'TIDAK LOLOS'),
('11', '2023-07-04', '3322777655432', 'Sri Wadani', 'LOLOS'),
('12', '2023-07-05', '3322100908762', 'Warto Warniyem', 'LOLOS'),
('13', '2023-07-05', '33221897873', 'Suksma Jana', 'LOLOS'),
('2', '2023-07-04', '3322185812876', 'Yanto Supratman', 'LOLOS'),
('3', '2023-07-04', '3322187908761', 'Sugiono Slamet', 'TIDAK LOLOS'),
('4', '2023-07-04', '3322190312341', 'Yarto Bagus', 'TIDAK LOLOS'),
('5', '2023-07-04', '3322198109902', 'Tukijan Putro', 'LOLOS'),
('6', '2023-07-04', '3322198706531', 'Warto Vario', 'LOLOS'),
('7', '2023-07-04', '3322435675432', 'Sri Wanto', 'LOLOS'),
('8', '2023-07-04', '3322451178908', 'Sindoro', 'LOLOS'),
('9', '2023-07-04', '3322564785231', 'Tukiman', 'LOLOS');

-- --------------------------------------------------------

--
-- Table structure for table `seleksi_tertulis`
--

CREATE TABLE `seleksi_tertulis` (
  `no_seleksi` varchar(50) NOT NULL,
  `tgl_seleksi` date NOT NULL,
  `nama_lengkap` varchar(255) NOT NULL,
  `kab_penempatan` varchar(255) NOT NULL,
  `point` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `seleksi_tertulis`
--

INSERT INTO `seleksi_tertulis` (`no_seleksi`, `tgl_seleksi`, `nama_lengkap`, `kab_penempatan`, `point`) VALUES
('1', '2023-07-05', 'Wanti Warniya', 'Grobogan', 58),
('11', '2023-07-06', 'Sri Wadani', 'Banyumas', 51),
('12', '2023-07-06', 'Warto Warniyem', 'Grobogan', 26),
('13', '2023-07-05', 'Suksma Jana', 'Grobogan', 31),
('2', '2023-07-05', 'Yanto Supratman', 'Grobogan', 58),
('5', '2023-07-05', 'Tukijan Putro', 'Banyumas', 50),
('6', '2023-07-06', 'Warto Vario', 'Grobogan', 41),
('7', '2023-07-06', 'Sri Wanto', 'Banyumas', 53),
('8', '2023-07-06', 'Sindoro', 'Grobogan', 61),
('9', '2023-07-06', 'Tukiman', 'Banyumas', 60);

-- --------------------------------------------------------

--
-- Table structure for table `seleksi_wawancara`
--

CREATE TABLE `seleksi_wawancara` (
  `no_seleksi` varchar(50) NOT NULL,
  `tgl_seleksi` date NOT NULL,
  `nama_lengkap` varchar(255) NOT NULL,
  `kab_penempatan` varchar(255) NOT NULL,
  `point` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `seleksi_wawancara`
--

INSERT INTO `seleksi_wawancara` (`no_seleksi`, `tgl_seleksi`, `nama_lengkap`, `kab_penempatan`, `point`) VALUES
('1', '2023-07-04', 'Wanti Warniya', 'Grobogan', 44),
('11', '2023-07-04', 'Sri Wadani', 'Banyumas', 52),
('12', '2023-07-07', 'Warto Warniyem', 'Grobogan', 16),
('13', '2023-07-05', 'Suksma Jana', 'Grobogan', 36),
('2', '2023-07-06', 'Yanto Supratman', 'Grobogan', 62),
('5', '2023-07-06', 'Tukijan Putro', 'Banyumas', 54),
('6', '2023-07-07', 'Warto Vario', 'Grobogan', 62),
('7', '2023-07-07', 'Sri Wanto', 'Banyumas', 46),
('8', '2023-07-07', 'Sindoro', 'Grobogan', 62),
('9', '2023-07-07', 'Tukiman', 'Banyumas', 70);

-- --------------------------------------------------------

--
-- Table structure for table `tertulis`
--

CREATE TABLE `tertulis` (
  `no_seleksi` varchar(50) NOT NULL,
  `nama_lengkap` varchar(255) NOT NULL,
  `kab_penempatan` varchar(255) NOT NULL,
  `point` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tertulis`
--

INSERT INTO `tertulis` (`no_seleksi`, `nama_lengkap`, `kab_penempatan`, `point`) VALUES
('1', 'Wanti Warniya', 'Grobogan', 58),
('11', 'Sri Wadani', 'Banyumas', 51),
('12', 'Warto Warniyem', 'Grobogan', 26),
('13', 'Suksma Jana', 'Grobogan', 31),
('2', 'Yanto Supratman', 'Grobogan', 58),
('5', 'Tukijan Putro', 'Banyumas', 50),
('6', 'Warto Vario', 'Grobogan', 41),
('7', 'Sri Wanto', 'Banyumas', 53),
('8', 'Sindoro', 'Grobogan', 61),
('9', 'Tukiman', 'Banyumas', 60);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `telp` varchar(15) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `username`, `email`, `telp`, `password`) VALUES
(1, 'admin', 'admin@gmail.com', '1234', 'admin'),
(2, 'putra', 'putra@gmail.com', '1234', '1234');

-- --------------------------------------------------------

--
-- Table structure for table `wawancara`
--

CREATE TABLE `wawancara` (
  `no_seleksi` varchar(50) NOT NULL,
  `nama_lengkap` varchar(255) NOT NULL,
  `kab_penempatan` varchar(255) NOT NULL,
  `point` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `wawancara`
--

INSERT INTO `wawancara` (`no_seleksi`, `nama_lengkap`, `kab_penempatan`, `point`) VALUES
('1', 'Wanti Warniya', 'Grobogan', 44),
('11', 'Sri Wadani', 'Banyumas', 52),
('12', 'Warto Warniyem', 'Grobogan', 16),
('13', 'Suksma Jana', 'Grobogan', 36),
('2', 'Yanto Supratman', 'Grobogan', 62),
('5', 'Tukijan Putro', 'Banyumas', 54),
('6', 'Warto Vario', 'Grobogan', 62),
('7', 'Sri Wanto', 'Banyumas', 46),
('8', 'Sindoro', 'Grobogan', 62),
('9', 'Tukiman', 'Banyumas', 70);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `administrasi`
--
ALTER TABLE `administrasi`
  ADD PRIMARY KEY (`no_seleksi`);

--
-- Indexes for table `kabupaten`
--
ALTER TABLE `kabupaten`
  ADD PRIMARY KEY (`kode_kab`);

--
-- Indexes for table `kecamatan`
--
ALTER TABLE `kecamatan`
  ADD PRIMARY KEY (`kode_kec`);

--
-- Indexes for table `kelurahan`
--
ALTER TABLE `kelurahan`
  ADD PRIMARY KEY (`kode_kel`);

--
-- Indexes for table `peserta`
--
ALTER TABLE `peserta`
  ADD PRIMARY KEY (`nik`);

--
-- Indexes for table `seleksi_administrasi`
--
ALTER TABLE `seleksi_administrasi`
  ADD PRIMARY KEY (`no_seleksi`);

--
-- Indexes for table `seleksi_tertulis`
--
ALTER TABLE `seleksi_tertulis`
  ADD PRIMARY KEY (`no_seleksi`);

--
-- Indexes for table `seleksi_wawancara`
--
ALTER TABLE `seleksi_wawancara`
  ADD PRIMARY KEY (`no_seleksi`);

--
-- Indexes for table `tertulis`
--
ALTER TABLE `tertulis`
  ADD PRIMARY KEY (`no_seleksi`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `wawancara`
--
ALTER TABLE `wawancara`
  ADD PRIMARY KEY (`no_seleksi`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
