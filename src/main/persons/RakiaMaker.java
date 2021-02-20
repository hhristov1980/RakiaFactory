package main.persons;

import main.boiler.Boiler;
import main.products.Fruit;
import main.products.Product;
import main.products.Rakia;
import main.rakiaFactory.RakiaFactory;
import main.util.Randomizer;

import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class RakiaMaker extends Person{
    int littersProduced;


    public RakiaMaker(String name, int age, Product.ProductType typeToProduce, RakiaFactory rakiaFactory) {
        super(name, age, typeToProduce, rakiaFactory);
    }

    @Override
    public int getTimeInMillis() {
        return 1000;
    }

    @Override
    public void run() {
        while (true){
            for(Map.Entry<Product.ProductType, BlockingQueue<Rakia>> e:rakiaFactory.getRakiaStore().entrySet()){
                if(e.getValue().size()>=30){
                    return;
                }
            }
            boolean produced = false;
            produced = rakiaFactory.addRakia(typeToProduce);
            if(produced){
                littersProduced++;
            }
            try {
                Thread.sleep(getTimeInMillis());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
