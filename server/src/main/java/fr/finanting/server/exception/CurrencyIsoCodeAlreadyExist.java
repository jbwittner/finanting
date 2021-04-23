package fr.finanting.server.exception;

public class CurrencyIsoCodeAlreadyExist extends FunctionalException {

    public CurrencyIsoCodeAlreadyExist(final String isoCode) {
        super("The ISO code '" + isoCode + "' are already exist" );
    }
}

