package it.formacion.alten.jimena.springboot.demo.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.formacion.alten.jimena.springboot.demo.inventory.entities.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

}
