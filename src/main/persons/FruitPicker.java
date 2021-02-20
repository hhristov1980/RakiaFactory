package main.persons;

import main.products.Fruit;
import main.products.Product;
import main.products.Rakia;
import main.rakiaFactory.RakiaFactory;

import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class FruitPicker extends Person{


    public FruitPicker(String name, int age, Product.ProductType typeToProduce, RakiaFactory rakiaFactory) {
        super(name, age, typeToProduce, rakiaFactory);
    }

    @Override
    public int getTimeInMillis() {
        return 200;
    }

    @Override
    public void run() {
       while (true) {
           for(Map.Entry<Product.ProductType, BlockingQueue<Rakia>> e:rakiaFactory.getRakiaStore().entrySet()){
               if(e.getValue().size()>=30){
                   return;
               }
           }
           rakiaFactory.fillBoiler(new Fruit(typeToProduce));
           System.out.println(name+" picked 1 kg "+typeToProduce);
           try {
               Thread.sleep(getTimeInMillis());
           } catch (InterruptedException e) {
               e.printStackTrace();
           }

       }
    }
}
