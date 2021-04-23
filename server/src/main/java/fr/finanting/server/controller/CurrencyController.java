package fr.finanting.server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.finanting.server.dto.CurrencyDTO;
import fr.finanting.server.exception.CurrencyIsoCodeAlreadyExist;
import fr.finanting.server.exception.CurrencyNotExistException;
import fr.finanting.server.exception.NoDefaultCurrencyException;
import fr.finanting.server.parameter.CreateCurrencyParameter;
import fr.finanting.server.parameter.UpdateCurrencyParameter;
import fr.finanting.server.service.CurrencyService;

@RestController
@RequestMapping("currency")
public class CurrencyController {

    private CurrencyService currencyService;

    @Autowired
    public CurrencyController(final CurrencyService currencyService){
        this.currencyService = currencyService;
    }

    @PostMapping("/createCurrency")
    public void createCurrency(@RequestBody final CreateCurrencyParameter createCurrencyParameter)
        throws CurrencyIsoCodeAlreadyExist, NoDefaultCurrencyException {

        this.currencyService.createCurrency(createCurrencyParameter);

    }

    @PostMapping("/updateCurrency")
    public void updateCurrency(@RequestBody final UpdateCurrencyParameter updateCurrencyParameter)
        throws CurrencyIsoCodeAlreadyExist, NoDefaultCurrencyException, CurrencyNotExistException {

        this.currencyService.updateCurrency(updateCurrencyParameter);
        
    }

    @GetMapping("/getAllCurrencies")
    public List<CurrencyDTO> getAllCurrencies() {
        return this.currencyService.getAllCurrencies();
    }
    
}
