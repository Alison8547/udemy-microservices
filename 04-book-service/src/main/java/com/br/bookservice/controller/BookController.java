package com.br.bookservice.controller;

import com.br.bookservice.model.Book;
import com.br.bookservice.proxy.CambioProxy;
import com.br.bookservice.repository.BookRepository;
import com.br.bookservice.response.Cambio;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/book-service")
public class BookController {

    private final Environment environment;
    private final BookRepository bookRepository;
    private final CambioProxy proxy;

    @GetMapping(value = "/{id}/{currency}")
    public Book findBook(@PathVariable(name = "id") Long id, @PathVariable(name = "currency") String currency) {
        String property = environment.getProperty("local.server.port");
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found!"));

        Cambio cambio = proxy.getCambio(book.getPrice(), "USD", currency);

        book.setPrice(cambio.getConvertedValue());
        book.setCurrency(currency);
        book.setEnvironment("Book port " + property + " Cambio port " + cambio.getEnvironment());
        return book;
    }


  /*
        Conectando api com o RestTemplate

    @GetMapping(value = "/{id}/{currency}")
    public Book findBook(@PathVariable(name = "id") Long id, @PathVariable(name = "currency") String currency) {
        String property = environment.getProperty("local.server.port");
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found!"));

        Map<String, String> params = new HashMap<>();
        params.put("amount", book.getPrice().toString());
        params.put("from", "USD");
        params.put("to", currency);

        Cambio cambio = new RestTemplate().getForEntity("http://localhost:8000/cambio-service/{amount}/{from}/{to}", Cambio.class, params).getBody();

        book.setPrice(cambio.getConvertedValue());
        book.setCurrency(currency);
        book.setEnvironment(property);
        return book;
    }

   */

}
