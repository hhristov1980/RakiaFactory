package main.products;

public abstract class Product {
    private ProductType type;

    public Product(ProductType type){
        this.type = type;
    }


    public enum ProductType {
        APRICOT, GRAPE, PLUM
    }

    public ProductType getType() {
        return type;
    }
}
