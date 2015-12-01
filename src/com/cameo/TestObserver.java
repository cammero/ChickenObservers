package com.cameo;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class TestObserver {

    public static void main(String[] args) {

        //
        EggAlert eggMonitor = new EggAlert();
        EggCounter eggCounts = new EggCounter();

        //Chicken instances created
        //eggCounts and eggMonitor observers added to each Chicken
        Chicken a = new Chicken("Mavis");
        //add each observer to chicken a
        a.addObserver(eggCounts);
        a.addObserver(eggMonitor);

        Chicken b = new Chicken("Betty");
        b.addObserver(eggCounts);
        b.addObserver(eggMonitor);

        Chicken c = new Chicken("Erma");
        c.addObserver(eggCounts);
        c.addObserver(eggMonitor);

        Chicken d = new Chicken("Viola");
        d.addObserver(eggCounts);
        d.addObserver(eggMonitor);

        //when layEgg() method is called, it changes laidEgg variable of each Chicken object to true
        a.layEgg();
        b.layEgg();
        a.layEgg();
        c.layEgg();
        d.layEgg();
        d.layEgg();
        a.layEgg();
        c.layEgg();
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

        //method of Observable interface, marks Chicken as having been changed
        this.setChanged();

        //method of Observable interface, notifies set of Observers of change,
        // in this case EggMonitor and EggCounts
        this.notifyObservers();

        //variable changed back to state prior to change
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
            System.out.println("\n" + chicken + " has laid an egg");
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
            } else { //chicken has laid her first egg, add chicken and egg count of 1 to list
                listOfChickenAndNumOfEggs.put((Chicken) chicken, 1);
            }
        }
        System.out.println("Current count of eggs: ");
        for (Chicken ch : listOfChickenAndNumOfEggs.keySet()){
            System.out.println(ch + ": " + listOfChickenAndNumOfEggs.get(ch));
        }
    }
}