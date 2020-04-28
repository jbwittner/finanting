--
-- Loading data from table `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_not_cached_value`, `minimum_value`, `maximum_value`, `start_value`, `increment`, `cache_size`, `cycle_option`, `cycle_count`) VALUES
(1, 1, 9223372036854775806, 1, 1, 1000, 0, 0);
COMMIT;

--
-- Loading data from table `ACCOUNT_TYPE`
--

INSERT INTO `ACCOUNT_TYPE` (`ID`, `TYPE`) VALUES
(1, 'SAVING'),
(2, 'BANKING'),
(3, 'PAYPAL');
COMMIT;