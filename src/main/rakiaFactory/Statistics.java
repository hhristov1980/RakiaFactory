package main.rakiaFactory;

import main.products.Fruit;
import main.products.Product;
import main.products.Rakia;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Statistics extends Thread{

    private RakiaFactory rakiaFactory;

    public Statistics(RakiaFactory rakiaFactory){
        this.rakiaFactory = rakiaFactory;
    }

    @Override
    public void run() {
        while (true) {
            printFruitStatistics();
            printRakiaStatistics();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
//private ConcurrentSkipListMap<Product.ProductType,BlockingQueue<Rakia>> rakiaStore;

    private void printFruitStatistics(){
        ArrayList<Map.Entry<Product.ProductType,BlockingQueue<Fruit>>>list = new ArrayList<>();
        int totalSum = 0;
        for(Map.Entry<Product.ProductType,BlockingQueue<Fruit>> e:rakiaFactory.getFruitStatistics().entrySet()){
            list.add(e);
            totalSum+=e.getValue().size();
        }
        if(!list.isEmpty()){
            Collections.sort(list,((o1, o2) -> Integer.compare(o2.getValue().size(),o1.getValue().size())));
            System.out.println("================= STATISTICS ===================");
            System.out.println("The most picked up fruit is: "+list.get(0).getKey()+" - "+list.get(0).getValue().size()+" kg.");

            System.out.println("Fruit harvest:");
            System.out.println("---------------");
            for(Map.Entry<Product.ProductType,BlockingQueue<Fruit>> map: list){
            System.out.println(map.getKey()+": "+map.getValue().size()+" kg./ "+Math.round(map.getValue().size()*100.0/totalSum)+" %.");
            }
            System.out.println("---------------");
            System.out.println("Total kilograms: "+totalSum);
            System.out.println("---------------");
        }
    }

    private void printRakiaStatistics(){
        ArrayList<Map.Entry<Product.ProductType,BlockingQueue<Rakia>>>list = new ArrayList<>();
        int totalSum = 0;
        for(Map.Entry<Product.ProductType,BlockingQueue<Rakia>> e:rakiaFactory.getRakiaStore().entrySet()){
            list.add(e);
            totalSum+=e.getValue().size();
        }
        if(!list.isEmpty()){
            Collections.sort(list,((o1, o2) -> Integer.compare(o2.getValue().size(),o1.getValue().size())));

            System.out.println("The most produced type of rakia is: "+list.get(0).getKey()+" - "+list.get(0).getValue().size()+" litters.");

            System.out.println("Rakia production:");
            System.out.println("---------------");
            for(Map.Entry<Product.ProductType,BlockingQueue<Rakia>> map: list){
                System.out.println(map.getKey()+": "+map.getValue().size()+" litters./"+Math.round(map.getValue().size()*100.0/totalSum)+" %.");
            }
            System.out.println("---------------");
            System.out.println("Total litters: "+totalSum);
            System.out.println("---------------");
            System.out.println("================= END OF STATISTICS ===================");
        }
    }

}
