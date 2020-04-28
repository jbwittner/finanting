--
-- Loading data from table `ACCOUNT_TYPE`
--

INSERT INTO `ACCOUNT_TYPE` (`ID`, `TYPE`) VALUES
(1, 'SAVING'),
(2, 'BANKING'),
(3, 'PAYPAL');
COMMIT;

--
-- Loading data from table `ACCOUNT`
--

INSERT INTO `ACCOUNT` (`ID`, `NAME`, `ACCOUNT_TYPE`) VALUES
(1, 'PERSO_1', 1),
(2, 'PERSO_2', 1),
(3, 'PERSO_3', 1),
(4, 'COMMON_1', 2),
(5, 'COMMON_2', 3),
(6, 'SPECIAL', 2);
COMMIT;
