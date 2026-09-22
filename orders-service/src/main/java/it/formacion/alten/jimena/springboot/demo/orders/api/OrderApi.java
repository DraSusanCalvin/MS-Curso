package it.formacion.alten.jimena.springboot.demo.orders.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import it.formacion.alten.jimena.springboot.demo.orders.api.adapters.OrderRequest;
import it.formacion.alten.jimena.springboot.demo.orders.api.adapters.OrderResponse;
import it.formacion.alten.jimena.springboot.demo.orders.api.adapters.UpdateOrderRequest;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Order Endpoints", description = "Operations for managing orders")
@RequestMapping("/api/v1/orders")
public interface OrderApi {

    @GetMapping("/")
    @Operation(summary = "Retrieve paginated list of orders", description = "Fetches a paginated list of orders, supporting sorting and filtering.")
    @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class), examples = @ExampleObject(value = """
            {
              "content": [
                {
                  "id": 1,
                  "status": "PENDING",
                  "items": [
                    { "productId": 1, "quantity": 2 },
                    { "productId": 3, "quantity": 1 }
                  ]
                },
                {
                  "id": 2,
                  "status": "CONFIRMED",
                  "items": [
                    { "productId": 5, "quantity": 1 }
                  ]
                }
              ],
              "pageable": {
                "pageNumber": 0,
                "pageSize": 10,
                "sort": {
                  "sorted": false,
                  "unsorted": true
                }
              },
              "totalPages": 3,
              "totalElements": 6,
              "last": false,
              "size": 10,
              "number": 0
            }
            """)))
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    ResponseEntity<Page<OrderResponse>> getOrders(@ParameterObject Pageable pageable);

    @GetMapping("/{id}")
    @Operation(summary = "Get an order by ID", description = "Fetches details of an order by its ID.")
    @ApiResponse(responseCode = "200", description = "Order found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderResponse.class), examples = @ExampleObject(value = """
            {
              "id": 1,
              "status": "PENDING",
              "items": [
                { "productId": 1, "quantity": 2 },
                { "productId": 3, "quantity": 1 }
              ]
            }
            """)))
    @ApiResponse(responseCode = "404", description = "Order not found", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"error\": \"Order Not Found\", \"message\": \"No order found with the given ID\"}")))
    ResponseEntity<OrderResponse> getOrder(@PathVariable("id") Long id);

    @PostMapping("/")
    @Operation(summary = "Create a new order", description = "Creates a new order containing multiple products.")
    @ApiResponse(responseCode = "201", description = "Order created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderResponse.class), examples = @ExampleObject(value = """
            {
              "id": 10,
              "status": "PLACED",
              "items": [
                { "productId": 1, "quantity": 2 },
                { "productId": 3, "quantity": 1 }
              ]
            }
            """)))
    @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"error\": \"Bad Request\", \"message\": \"Invalid order details\"}")))
    ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest request);

    @PutMapping("/{id}")
    @Operation(summary = "Update an order", description = "Modifies an existing order (update items). Cannot update if the order is canceled.")
    @ApiResponse(responseCode = "200", description = "Order updated successfully")
    @ApiResponse(responseCode = "404", description = "Order not found")
    @ApiResponse(responseCode = "400", description = "Cannot edit a canceled order")
    ResponseEntity<OrderResponse> updateOrder(@PathVariable("id") Long id, @RequestBody UpdateOrderRequest request);

    @PatchMapping("/{id}/cancel")
    @Operation(summary = "Cancel an order", description = "Marks an order as canceled. Cannot be undone.")
    @ApiResponse(responseCode = "200", description = "Order canceled successfully")
    @ApiResponse(responseCode = "404", description = "Order not found")
    ResponseEntity<OrderResponse> cancelOrder(@PathVariable("id") Long id);
}
