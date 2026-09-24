package it.formacion.alten.jimena.springboot.demo.inventory.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import it.formacion.alten.jimena.springboot.demo.inventory.entities.Inventory;
import it.formacion.alten.jimena.springboot.demo.inventory.repository.InventoryRepository;

@Configuration 
public class InventoryDataInitializer {

    @Bean
    public CommandLineRunner inventoryDataInit(InventoryRepository repo){
       return  args -> {
        if(repo.count() == 0){
            repo.save(new Inventory(1L,20));
            repo.save(new Inventory(2L,10) );
            repo.save(new Inventory(1L,20));

        }
       };

    }

}
