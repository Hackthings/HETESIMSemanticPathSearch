package main.java.ownClasses.domain.domainControllers.Drivers;


import main.java.sharedClasses.domain.nodes.Conference;
import main.java.sharedClasses.domain.nodes.Paper;

import java.util.HashMap;
import java.util.Set;

public class Conference_driver {

    public static void main(String[] args) {

/*
        Paper p2 = new Paper("Hi", 1);
        Paper p1 = new Paper("Hola", 2);
        Conference c = new Conference("CSS", 3);

        c.addExposedPaper(p1);
        c.addExposedPaper(p2);


        System.out.println("Working with the conference: " + c.getName());

        System.out.println("Get the papers exposed at the confernce by id:");
        HashMap<Integer,Paper> exposedPapersById = c.getExposedPapersById();
        Set<Integer> keyMap = exposedPapersById.keySet();
        for (Integer i : keyMap){
            System.out.println(exposedPapersById.get(i).getId() +") "+ exposedPapersById.get(i).getName());
        }

        System.out.println("Get the papers exposed at the confernce by name:");
        HashMap<String,Paper> exposedPapersByName = c.getExposedPapersByName();
        Set<String> keyMap2 = exposedPapersByName.keySet();
        for (String s : keyMap2){
            System.out.println(exposedPapersById.get(s).getId() +") "+ exposedPapersById.get(s).getName());
        }

        System.out.println("Get paper `Hola´ by his id(2).");
        Paper aux = c.getPaperById(2);
        System.out.println("id/name: " + aux.getId() + " " + aux.getName());
        System.out.println("Get paper with the id 5 by his name(`Hi´).");
        aux = c.getPaperByName("Hi");
        System.out.println("id/name: " + aux.getId() + " " + aux.getName() +".");

        System.out.println("Set the conference year and continent and change the name of the confernce.");
        c.setContinent("EU");
        c.setYear(2016);
        c.setName("UPC");
        System.out.println("the conference is now named `" + c.getName() +"´, takes place in the continent "
                + c.getContinent() + " the year " + c.getYear() +".");

        System.out.println("Removal of the papers by name");
        c.removeExposedPaperBy(p2);
        aux = c.getPaperByName("Hi");
        if (aux == null) System.out.println("The paper named `Hi´ does not exists anymore");
        aux = p1;
        c.removeExposedPaperBy(p1);
        aux = c.getPaperById(2);
        if (aux == null) System.out.println("The paper with id 2 does not exists anymore");*/


    }

}
