package fr.finanting.server.repository;

import java.util.Optional;

import fr.finanting.server.model.Currency;

public interface CurrencyRepository extends AbstractRepository<Currency, Integer>{

    Boolean existsByIsoCode(String isoCode);

    Boolean existsByDefaultCurrency(Boolean defaultCurrency);

    Optional<Currency> findByDefaultCurrency(Boolean defaultCurrency);

    Optional<Currency> findByIsoCode(String isoCode);

}
