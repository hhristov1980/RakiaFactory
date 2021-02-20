package main.rakiaFactory;

import main.boiler.Boiler;
import main.products.Fruit;
import main.products.Product;
import main.products.Rakia;
import main.util.Randomizer;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.LinkedBlockingQueue;

public class RakiaFactory {
    private String name;
    private BlockingQueue<Boiler> boilers;
    private ConcurrentHashMap<Product.ProductType,BlockingQueue<Rakia>> rakiaStore;
    private ConcurrentHashMap<Product.ProductType,BlockingQueue<Fruit>> fruitStatistics;

    public RakiaFactory (String name){
        if(name.length()>0){
            this.name = name;
        }
        boilers = new LinkedBlockingQueue<>(5);
        rakiaStore = new ConcurrentHashMap<>();
        fruitStatistics = new ConcurrentHashMap<>();
    }



    public synchronized void fillBoiler(Fruit fruit){
        if(boilers.isEmpty()){
            Boiler boiler = new Boiler(fruit.getType());
            try {
                boiler.getRakiaTank().put(fruit);
                boilers.put(boiler);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            notifyAll();
        }
        else {
            boolean isPut = false;
            for(Boiler b: boilers){
                if(b.getType().equals(fruit.getType())){
                    if(b.getRakiaTank().size()<10){
                        try {
                            b.getRakiaTank().put(fruit);
                            if(!fruitStatistics.containsKey(fruit.getType())){
                                fruitStatistics.put(fruit.getType(),new LinkedBlockingQueue<>());
                            }
                            fruitStatistics.get(fruit.getType()).put(fruit);
//                            System.out.println("As of now, "+fruitStatistics.get(fruit.getType()).size()+" kg "+fruit.getType()+" was picked up for rakia making!");
                            isPut = true;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        notifyAll();
                    }
                }
            }
            if(!isPut){
                if(boilers.size()<5){
                    Boiler boiler = new Boiler(fruit.getType());
                    try {
                        boiler.getRakiaTank().put(fruit);
                        boilers.put(boiler);
                        if(!fruitStatistics.containsKey(fruit.getType())){
                            fruitStatistics.put(fruit.getType(),new LinkedBlockingQueue<>());
                        }
                        fruitStatistics.get(fruit.getType()).put(fruit);
//                        System.out.println("As of now, "+fruitStatistics.get(fruit.getType()).size()+" kg "+fruit.getType()+" was picked up for rakia making!");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    notifyAll();
                }
                else {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public synchronized boolean addRakia(Product.ProductType type){
        boolean foundBoiler = false;
        if(boilers.isEmpty()){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else {
            for(Boiler b: boilers){
                if(b.getRakiaTank().size()==10){
                    if(b.getType().equals(type)){
                        int litters = Randomizer.getRandomInt(1,10);
                        for(int i = 0; i<litters; i++){
                            Rakia rakia = new Rakia(type);
                            if(!rakiaStore.containsKey(rakia.getType())){
                                rakiaStore.put(rakia.getType(),new LinkedBlockingQueue<>());
                            }
                            try {
                                rakiaStore.get(rakia.getType()).put(rakia);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
//                        System.out.println(litters+" litters of "+type+" rakia was produced!");
//                        System.out.println("Now we have "+rakiaStore.get(type).size()+" litters rakia of "+type);
                        boilers.remove(b);
                        foundBoiler = true;
                        notifyAll();
                    }
                }
            }
            if(!foundBoiler){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return foundBoiler;
    }

    public ConcurrentHashMap<Product.ProductType, BlockingQueue<Fruit>> getFruitStatistics() {
        return fruitStatistics;
    }

    public ConcurrentHashMap<Product.ProductType, BlockingQueue<Rakia>> getRakiaStore() {
        return rakiaStore;
    }
    private void stop(){
        for(Map.Entry<Product.ProductType, BlockingQueue<Rakia>> e:rakiaStore.entrySet()){
            if(e.getValue().size()>=30){
                notifyAll();
            }
        }
    }
}
