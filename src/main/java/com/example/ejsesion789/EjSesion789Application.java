package com.example.ejsesion789;

import com.example.ejsesion789.entities.Laptop;
import com.example.ejsesion789.repository.LaptopRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;

@SpringBootApplication
public class EjSesion789Application {

	public static void main(String[] args) {

		//SpringApplication.run(EjSesion789Application.class, args);

		ApplicationContext context =SpringApplication.run(EjSesion789Application.class, args);
		LaptopRepository repository = context.getBean(LaptopRepository.class);

		System.out.println("Nº de libros antes de guardar: " + repository.findAll().size());


		Laptop laptop1 = new Laptop(null, "Acer", "Bussiness", 599.99, LocalDate.of(2020,01, 01));
		Laptop laptop2 = new Laptop(null, "Dell", "Pro", 999.99, LocalDate.of(2022,12, 01));

		repository.save(laptop1);
		repository.save(laptop2);

		System.out.println("Nº de libros después de guardar: " + repository.findAll().size());



	}

}
