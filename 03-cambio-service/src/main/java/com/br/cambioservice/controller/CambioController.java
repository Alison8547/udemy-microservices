package com.br.cambioservice.controller;

import com.br.cambioservice.model.Cambio;
import com.br.cambioservice.repository.CambioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Tag(name = "Cambio endpoints")
@RestController
@RequiredArgsConstructor
@RequestMapping("/cambio-service")
public class CambioController {

    private final CambioRepository cambioRepository;
    private final Environment environment;

    @Operation(summary = "convert an amount to local currency")
    @GetMapping(value = "/{amount}/{from}/{to}")
    public Cambio getCambio(@PathVariable(name = "amount") BigDecimal amount, @PathVariable(name = "from") String from, @PathVariable(name = "to") String to) {
        String property = environment.getProperty("local.server.port");

        Cambio cambio = cambioRepository.findByFromAndTo(from, to);
        if (cambio == null) throw new RuntimeException("Currency Unsupported");
        cambio.setEnvironment(property);
        BigDecimal conversionFactor = cambio.getConversionFactor();
        BigDecimal convertedValue = amount.multiply(conversionFactor);

        cambio.setConvertedValue(convertedValue.setScale(2, RoundingMode.CEILING));

        return cambio;
    }

}
