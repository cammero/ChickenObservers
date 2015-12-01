package com.cameo;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class TestObserver {

    public static void main(String[] args) {

        //
        EggAlert eggMonitor = new EggAlert();
        EggCounter eggCounts = new EggCounter();

        Chicken a = new Chicken("Mavis");
        //adds observers to chicken a
        a.addObserver(eggMonitor);
        a.addObserver(eggCounts);

        Chicken b = new Chicken("Betty");
        //adds observers to chicken b
        b.addObserver(eggMonitor);
        b.addObserver(eggCounts);

        a.layEgg();
        b.layEgg();
        a.layEgg();
        //TODO fix the order in which EggAlert and EggCounter display
    }
}

class Chicken extends Observable {

    String name;
    boolean laidEgg = false;

    Chicken(String name){
        this.name = name;
    }

    public void layEgg(){
        this.laidEgg = true;
        //method of Observable interface, returns true if class changes,
        // in this case, the layEgg() method is called
        this.setChanged();
        //method of Observable interface, notifies set of Observers of change,
        // in this case EggMonitor and EggCounts
        this.notifyObservers();
        this.laidEgg = false;
    }

    public String toString() {
        return this.name;
    }

    public boolean laidEgg(){
        return laidEgg;
    }
}

class EggAlert implements Observer {

    //update method is called whenever chicken has changed
    public void update(Observable chicken, Object arg) {
        if (((Chicken)chicken).laidEgg() ){
            System.out.println(chicken + " has laid an egg \n");
        }
    }
}

class EggCounter implements Observer{
    //hashmap to store chickens and their egg count
    public HashMap<Chicken, Integer> listOfChickenAndNumOfEggs = new HashMap<>();

    //update method is called whenever chicken has changed
    public void update(Observable chicken, Object arg) {
        if (((Chicken)chicken).laidEgg()) {
            //search list, if the chicken has previously laid an egg, increment number of eggs
            if (listOfChickenAndNumOfEggs.keySet().contains(chicken)) {
                Integer numEggs = listOfChickenAndNumOfEggs.get(chicken);
                numEggs++;
                listOfChickenAndNumOfEggs.put((Chicken) chicken, numEggs);
            } else { //chicken has layed her first egg, add chicken and 1 egg to list
                listOfChickenAndNumOfEggs.put((Chicken) chicken, 1);
            }
        }
        System.out.println("Current count of eggs: ");
        for (Chicken ch : listOfChickenAndNumOfEggs.keySet()){
            System.out.println(ch + ": " + listOfChickenAndNumOfEggs.get(ch));
        }
    }
}