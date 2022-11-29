package com.voronkov.api;

import com.voronkov.ProductRep;
import com.voronkov.model.Product;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customer")
public class ProductController {

    @Autowired
    ProductRep productRep;


//    Я не понял как инициализировать переменные ниже, что бы не дублировать код в методах
//    пробовал и метод init() и аннотацией @PostConstruct, не работает.
//    я так понимаю что обращение к productRep идет только в методах.

//    Еще я подумал что можно сделать один метод findAll с входящим параметром
//    и условием if/else где код расписал бы
//    но решил оставить так.
//    и к тому же не умерен что это корректная реализация.

    private int minPrice;
    private int maxPrice;

    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        return productRep.findById(id).orElseThrow();
    }

    @GetMapping("/upmin")
    public List<Product> getAllUpMin() {
        minPrice = productRep.findAll().stream().min(Comparator.comparing(Product::getPrice)).orElse(null).getPrice();
        return productRep.findAll().stream().
                filter(n -> n.getPrice() > minPrice).collect(Collectors.toList());
    }

    @GetMapping("/lowmax")
    public List<Product> getAllLowMax() {
        maxPrice = productRep.findAll().stream().max(Comparator.comparing(Product::getPrice)).orElse(null).getPrice();
        return productRep.findAll().stream().
                filter(n -> n.getPrice() < maxPrice).collect(Collectors.toList());
    }

    @GetMapping("/betweenmaxmin")
    public List<Product> getAllBetweenMaxMin() {
        minPrice = productRep.findAll().stream().min(Comparator.comparing(Product::getPrice)).orElse(null).getPrice();
        maxPrice = productRep.findAll().stream().max(Comparator.comparing(Product::getPrice)).orElse(null).getPrice();
        return productRep.findAll().stream().
                filter(n -> n.getPrice() > minPrice && n.getPrice()<maxPrice).collect(Collectors.toList());
    }

    @GetMapping
    public List<Product> getAll() {
        return productRep.findAll();
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        productRep.deleteById(id);
        return "User: " + id + " has been deleted.";
    }

    @PostMapping("/create")
    public void createByName(@RequestParam String name, @RequestParam Integer price) {
        productRep.save(new Product(name, price));
    }


}
