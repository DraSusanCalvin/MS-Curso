package it.formacion.alten.jimena.springboot.demo.inventory.api;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.formacion.alten.jimena.springboot.demo.inventory.api.adapters.InventoryResponse;

@Tag(name = "Inventory Endpoints", description = "Operaciones para gestionar el inventario")
@RequestMapping("/api/v1/inventory")
public interface InventoryApi {

    @GetMapping("/")
    @Operation(summary = "Devuelve todo el inventario", description = "Devuelve un listado de todo el inventario")
    @ApiResponse(responseCode = "200", description = "Operación OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class)))
    ResponseEntity<List<InventoryResponse>> getInventario();

    @GetMapping("/{productId}")
    @Operation(summary = "Obtener un elemento del inventario", description = "Obtener un elemento del inventario")
    @ApiResponse(responseCode = "200", description = "Operación OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class)))
    @ApiResponse(responseCode = "404", description = "Libro no encontrado")
    ResponseEntity<InventoryResponse> getStock(@PathVariable("productId") Long productId);
}
