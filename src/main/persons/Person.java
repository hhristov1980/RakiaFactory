package main.persons;

import main.products.Product;
import main.rakiaFactory.RakiaFactory;

public abstract class Person extends Thread{
    protected String name;
    protected Product.ProductType typeToProduce;
    private int age;
    protected RakiaFactory rakiaFactory;

    public Person(String name, int age, Product.ProductType typeToProduce,RakiaFactory rakiaFactory){
        if(name.length()>0){
            this.name = name;
        }
        if(age>=18&&age<100){
            this.age = age;
        }
        this.typeToProduce = typeToProduce;
        this.rakiaFactory = rakiaFactory;
    }

    public abstract int getTimeInMillis();

    @Override
    public abstract void run();


}
