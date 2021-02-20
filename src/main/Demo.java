package main;

import main.persons.FruitPicker;
import main.persons.Person;
import main.persons.RakiaMaker;
import main.products.Product;
import main.rakiaFactory.RakiaFactory;
import main.rakiaFactory.Statistics;
import main.util.Constants;
import main.util.Randomizer;

import java.util.ArrayList;

public class Demo {
    public static void main(String[] args) {
        RakiaFactory rakiaFactory = new RakiaFactory("IT Rakia");
        Statistics stat = new Statistics(rakiaFactory);
        ArrayList<FruitPicker> pickers = new ArrayList<>();
        ArrayList<RakiaMaker> makers = new ArrayList<>();
        for(int i = 0; i<7; i++){
            Product.ProductType type = Product.ProductType.values()[Randomizer.getRandomInt(0,Product.ProductType.values().length-1)];
            String name = Constants.names[Randomizer.getRandomInt(0,Constants.names.length-1)];
            int age = Randomizer.getRandomInt(18,100);
            FruitPicker picker = new FruitPicker(name,age,type,rakiaFactory);
            pickers.add(picker);
        }
        for(int i = 0; i<5; i++){
            Product.ProductType type = Product.ProductType.values()[Randomizer.getRandomInt(0,Product.ProductType.values().length-1)];
            String name = Constants.names[Randomizer.getRandomInt(0,Constants.names.length-1)];
            int age = Randomizer.getRandomInt(18,100);
            RakiaMaker maker = new RakiaMaker(name,age,type,rakiaFactory);
            makers.add(maker);
        }
        stat.setDaemon(true);
        stat.start();
        for(FruitPicker fp: pickers){
            fp.start();
        }
        for(RakiaMaker r: makers){
            r.start();
        }


    }
}
