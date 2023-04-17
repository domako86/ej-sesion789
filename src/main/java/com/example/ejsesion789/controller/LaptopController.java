package com.example.ejsesion789.controller;

import com.example.ejsesion789.entities.Laptop;
import com.example.ejsesion789.repository.LaptopRepository;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.PresentationDirection;
import java.util.List;
import java.util.Optional;

@RestController
public class LaptopController {

    private LaptopRepository laptopRepository;

    public LaptopController(LaptopRepository laptopRepository){
        this.laptopRepository = laptopRepository;
    }

    @GetMapping("/api/laptops")
    public List<Laptop> findAll(){
        return laptopRepository.findAll();
    }

    @GetMapping("/api/laptops/{id}")
    public ResponseEntity<Laptop> findOneById(@PathVariable Long id){
        Optional<Laptop> laptopOptional = laptopRepository.findById(id);
        if(laptopOptional.isPresent()){
            return ResponseEntity.ok(laptopOptional.get());
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/api/laptops")
    public ResponseEntity<Object> create (@RequestBody Laptop laptop){
        //Si el ID no es null, es una actualización.
        if(laptop.getId() != null){
            return ResponseEntity.badRequest().build();
        }
        Laptop laptopCreated = laptopRepository.save(laptop);
        return ResponseEntity.ok(laptopCreated);
    }

    @PutMapping("/api/laptops")
    public ResponseEntity<Object> update(@RequestBody Laptop laptop){
        //Si no tiene ID es una creacion y no un update
        if(laptop.getId() == null){
            return ResponseEntity.badRequest().build();
        }

        //Si no existe el ID no se puede actualizar porque no está en BD
        if(!laptopRepository.existsById(laptop.getId())){
            return ResponseEntity.notFound().build();
        }

        Laptop laptopUpdated = laptopRepository.save(laptop);
        return ResponseEntity.ok(laptopUpdated);
    }

    @DeleteMapping("/api/laptops/{id}")
    public ResponseEntity<Laptop> delete(@PathVariable Long id){
        //Comprobamos si existe
        if(!laptopRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }

        laptopRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/api/laptops")
    public ResponseEntity<Laptop> deleteAll(){
        laptopRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }

}
