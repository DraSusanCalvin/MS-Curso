package it.formacion.alten.jimena.springboot.demo.products.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.formacion.alten.jimena.springboot.demo.products.adapter.ProductRequest;
import it.formacion.alten.jimena.springboot.demo.products.adapter.ProductResponse;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Product Endpoints", description = "Operations for managing products")
@RequestMapping("/api/v1/products")
public interface ProductApi {

    @GetMapping("/")
    @Operation(summary = "Retrieve a paginated list of products", description = "Fetches a paginated list of available products. Allows filtering, sorting, and pagination.")

    @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class), examples = @ExampleObject(value = """
            {
              "content": [
                {"id": 1, "name": "Laptop", "description": "High-end gaming laptop", "price": 1200.0},
                {"id": 2, "name": "Smartphone", "description": "Latest model smartphone", "price": 800.0},
                {"id": 3, "name": "Wireless Headphones", "description": "Noise-canceling over-ear headphones", "price": 250.0},
                {"id": 4, "name": "Smartwatch", "description": "Fitness tracker with heart rate monitor", "price": 199.99},
                {"id": 5, "name": "Mechanical Keyboard", "description": "RGB backlit tactile gaming keyboard", "price": 125.5}
              ],
              "totalPages": 3,
              "totalElements": 6,
              "size": 2,
              "number": 0
            }
            """)))
    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"error\": \"Internal Server Error\", \"message\": \"Unexpected error occurred\"}")))
    ResponseEntity<Page<ProductResponse>> getProducts(@ParameterObject Pageable pageable);

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID", description = "Retrieves a product from the database by its ID.")

    @ApiResponse(responseCode = "200", description = "Product found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponse.class), examples = @ExampleObject(value = """
            {
              "id": 1,
              "name": "Laptop",
              "description": "High-end gaming laptop",
              "price": 1200.0
            }
            """)))
    @ApiResponse(responseCode = "404", description = "Product not found", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"error\": \"Product Not Found\", \"message\": \"No product found with the given ID\"}")))
    ResponseEntity<ProductResponse> getProduct(@PathVariable("id") Long id);

    @PostMapping("/")
    @Operation(summary = "Create a product", description = "Creates a new product entry in the system.")

    @ApiResponse(responseCode = "201", description = "Product created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponse.class), examples = @ExampleObject(value = """
            {
              "id": 10,
              "name": "Tablet",
              "description": "New generation tablet",
              "price": 500.0
            }
            """)))
    @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"error\": \"Bad Request\", \"message\": \"Invalid product details\"}")))
    ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest product);

    @PutMapping("/{id}")
    @Operation(summary = "Update a product", description = "Updates an existing product by its ID.")
    @ApiResponse(responseCode = "200", description = "Product updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponse.class), examples = @ExampleObject(value = """
            {
              "id": 1,
              "name": "Updated Laptop",
              "description": "Updated description",
              "price": 1300.0
            }
            """)))
    @ApiResponse(responseCode = "404", description = "Product not found", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"error\": \"Product Not Found\", \"message\": \"No product found with the given ID\"}")))
    ResponseEntity<ProductResponse> updateProduct(@PathVariable("id") Long id, @RequestBody ProductRequest product);

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product", description = "Deletes a product by its ID from the database.")
    @ApiResponse(responseCode = "204", description = "Product deleted successfully")
    @ApiResponse(responseCode = "404", description = "Product not found", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"error\": \"Product Not Found\", \"message\": \"No product found with the given ID\"}")))
    ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id);

    @PatchMapping("/{id}")
    @Operation(summary = "Patch a product", description = "Partially updates a product by its ID.")
    @ApiResponse(responseCode = "200", description = "Product updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponse.class), examples = @ExampleObject(value = """
            {
              "id": 1,
              "name": "Updated Laptop",
              "description": "Updated description",
              "price": 1300.0
            }
            """)))
    @ApiResponse(responseCode = "404", description = "Product not found", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"error\": \"Product Not Found\", \"message\": \"No product found with the given ID\"}")))
    ResponseEntity<ProductResponse> patchProduct(@PathVariable("id") Long id, @RequestBody ProductRequest product);
}

