package ownClasses.domain.domainControllers.Drivers;

import sharedClasses.domain.nodes.*;

import java.util.HashMap;
import java.util.TreeMap;
import java.util.Set;

public class Conference_driver {

    //AQUEST DRIVER PASSA TOTES LES PROVES CORRECTAMENT
    public static void main(String[] args) {

        Paper p2 = new Paper("Hi", 1);
        Paper p1 = new Paper("Hola", 2);
        Conference c = new Conference("CSS", 3);

        c.addExposedPaper(p1);
        c.addExposedPaper(p2);


        System.out.println("Working with the conference: " + c.getName());

        System.out.println("Get the papers exposed at the confernce by id:");
        HashMap<Integer,Paper> exposedPapersById = new HashMap<>();
        exposedPapersById = c.getExposedPapersById(exposedPapersById);
        Set<Integer> keyMap = exposedPapersById.keySet();
        for (int i : keyMap){
            System.out.println(exposedPapersById.get(i).getId() +") "+ exposedPapersById.get(i).getName());
        }

        System.out.println("Get the papers exposed at the confernce by name:");
        HashMap<String,Paper> exposedPapersByName = new HashMap<>();
        exposedPapersByName = c.getExposedPapersByName(exposedPapersByName );
        Set<String> keyMap2 = exposedPapersByName.keySet();
        for (String s : keyMap2){
            Paper p = exposedPapersByName.get(s);
            System.out.println(p.getId() +") "+ p.getName());
        }

        System.out.println("Set the conference year and continent and change the name of the confernce.");
        c.setContinent("EU");
        c.setYear(2016);
        c.setName("UPC");
        System.out.println("the conference is now named `" + c.getName() +"´, takes place in the continent "
                + c.getContinent() + " the year " + c.getYear() +".");

        System.out.println("Removal of the papers by name");
        c.removeExposedPaperBy(p2);
        System.out.println("Get the papers remaining exposed at the confernce by id:");
        HashMap<Integer,Paper> exposedPapersById2 = new HashMap<>();
        exposedPapersById2 = c.getExposedPapersById(exposedPapersById2);
        Set<Integer> keyMap3 = exposedPapersById2.keySet();
        for (int i : keyMap3){
            System.out.println(exposedPapersById.get(i).getId() +") "+ exposedPapersById.get(i).getName());
        }

    }

}
