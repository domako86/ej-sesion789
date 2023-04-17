package com.example.ejsesion789.controller;

import com.example.ejsesion789.entities.Laptop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;


import java.util.Arrays;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LaptopControllerTest {

    private TestRestTemplate testRestTemplate;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        restTemplateBuilder = restTemplateBuilder.rootUri("http://localhost:" + port);
        testRestTemplate = new TestRestTemplate(restTemplateBuilder);
    }

    @Test
    void findAll() {
        ResponseEntity<Laptop[]> response =  testRestTemplate.getForEntity("/api/laptops", Laptop[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<Laptop> laptops = Arrays.asList(response.getBody());
        System.out.println("Tamaño lista de laptops test findAll(): " + laptops.size());
    }

    @Test
    void findOneById() {
        ResponseEntity<Laptop> response = testRestTemplate.getForEntity("/api/laptops/3", Laptop.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void create() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        String json = """
                {
                    "marca": "Portatil Spring Test",
                    "modelo": "Testing",
                    "precio": 1000.0,
                    "fecha": null
                }
                """;
        //Creamos una petición http donde enviamos el json junto con las cabeceras
        HttpEntity<String> request = new HttpEntity<>(json, headers);

        ResponseEntity<Laptop> response = testRestTemplate.exchange("/api/laptops", HttpMethod.POST, request, Laptop.class);

        //una vez tenemos la respuesta con el portatil que queremos insertar en BD mediante POST hacemos los asserts
        Laptop result = response.getBody();
        // 1L --> Es el ID 1 porque el main nose ha ejecutado y por lo tanto no hay libros en BD
        assertEquals(1L, result.getId());
        assertEquals("Portatil Spring Test", result.getMarca());
    }


}