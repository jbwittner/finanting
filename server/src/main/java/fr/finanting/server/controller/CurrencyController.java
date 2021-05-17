package fr.finanting.server.controller;

import java.util.List;

import fr.finanting.server.codegen.api.CurrencyApi;
import fr.finanting.server.codegen.model.CurrencyDTO;
import fr.finanting.server.codegen.model.CurrencyParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import fr.finanting.server.service.CurrencyService;

@RestController
public class CurrencyController extends MotherController implements CurrencyApi {

    private CurrencyService currencyService;

    @Autowired
    public CurrencyController(final CurrencyService currencyService){
        this.currencyService = currencyService;
    }

    @Override
    public ResponseEntity<Void> createCurrency(CurrencyParameter body) {
        this.currencyService.createCurrency(body);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteCurrency(Integer currencyId) {
        this.currencyService.deleteCurrency(currencyId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<CurrencyDTO>> getAllCurrencies() {
        List<CurrencyDTO> currencyDTOList = this.currencyService.getAllCurrencies();
        return new ResponseEntity<>(currencyDTOList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> updateCurrency(Integer currencyId, CurrencyParameter body) {
        this.currencyService.updateCurrency(currencyId, body);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
}
