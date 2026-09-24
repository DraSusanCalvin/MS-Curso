package it.formacion.alten.jimena.springboot.demo.inventory.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import it.formacion.alten.jimena.springboot.demo.inventory.entities.Inventory;
import it.formacion.alten.jimena.springboot.demo.inventory.repository.InventoryRepository;

@Configuration 
public class InventoryDataInitializer {

    private static final Logger logger = LoggerFactory.getLogger(InventoryDataInitializer.class);

    @Bean 
    CommandLineRunner cargarInventario(InventoryRepository repo){
        return args -> {
            if(repo.count() == 0){
                repo.save(new Inventory(1L, 20));
                repo.save(new Inventory(2L, 10));
                repo.save(new Inventory(3L, 20));
                logger.info("Inventario cargado");
            }
        };
    }

}
