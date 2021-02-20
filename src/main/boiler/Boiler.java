package main.boiler;

import main.products.Fruit;
import main.products.Product;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Boiler {
    private Product.ProductType type;
    private BlockingQueue<Fruit> rakiaTank;

    public Boiler(Product.ProductType type){
        this.type = type;
        rakiaTank = new LinkedBlockingQueue<>(10);
    }

    public Product.ProductType getType() {
        return type;
    }

    public BlockingQueue<Fruit> getRakiaTank() {
        return rakiaTank;
    }
}
