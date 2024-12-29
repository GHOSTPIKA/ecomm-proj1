package com.chesedam.ecomm_proj0.controller;
import java.io.IOException;
import java.util.List;

import com.chesedam.ecomm_proj0.model.Product;
import com.chesedam.ecomm_proj0.services.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductServices services;

    @RequestMapping("/")
    public String greet() {
        return "I hope she meets me today";
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        return new ResponseEntity<>(services.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id) {
        Product product = services.getProductById(id);
        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart("product") Product product,
                                        @RequestPart("imageFile") MultipartFile imageFile) {
        try {
            Product product1 = services.addProduct(product, imageFile);
            return new ResponseEntity<>(product1, HttpStatus.CREATED);
        } catch (Exception e) {// Log the full stack trace
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
@GetMapping("/product/{productId}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int productId) {
    Product product = services.getProductById(productId);
    byte[] imageFile= product.getImageData();
    return ResponseEntity.ok()
            .contentType(MediaType.valueOf(product.getImageType()))
            .body(imageFile);
}
@PutMapping("/product/{id}")
    public ResponseEntity updateProduct(@PathVariable int id,@RequestPart("product") Product product,
                                        @RequestPart("imageFile") MultipartFile imageFile) {
    Product product1 = null;
    try {
        product1 = services.updateProduct(id, product, imageFile);
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
    if (product1 != null) {
        return new ResponseEntity<>("updated", HttpStatus.OK);
    } else {
        return new ResponseEntity<>("faile to update", HttpStatus.BAD_REQUEST);
    }

}
@DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id){
        Product product1=services.getProductById(id);
        if(product1!=null){
            services.deleteProduct(id);
            return new ResponseEntity<>("Deleted",HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>("Not Found",HttpStatus.NOT_FOUND);
        }

}
@GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String Keyword){
        List<Product> products=services.searchProducts(Keyword);
        return new ResponseEntity<>(products,HttpStatus.OK);
}

}
