package com.voronkov.service;

import com.voronkov.ProductRep;
import com.voronkov.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DBInjection {

    @Autowired
    private ProductRep productRep;

    @EventListener(ApplicationReadyEvent.class)
    public void fill(){
        for (int i = 1; i < 21; i++) {
            productRep.save(createProduct("Product: " + i,(int)(Math.random()*100)));
        }
    }


    private Product createProduct(String name,Integer price){
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        return product;
    }
}
