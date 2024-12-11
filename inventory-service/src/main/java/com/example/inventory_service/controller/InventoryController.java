package com.example.inventory_service.controller;

import com.example.inventory_service.dto.InventoryResponse;
import com.example.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode){
//The @RequestParam annotation extracts the skuCode parameter values from the request URL and converts them into a List<String>.
        return inventoryService.isInStock(skuCode); //passing the extracted list of SKU codes to the service layer.
    }
}
