package org.search.repository;

import org.search.person.Person;

import java.util.HashMap;

public class PersonRepository {
    private static HashMap<Integer, Person> mapOfPeople;

    public PersonRepository() {
        mapOfPeople = new HashMap<>();
    }

    public void savePerson(String fistName, String lastName, String email, Integer numberOfLine) {
        mapOfPeople.put(numberOfLine, new Person(fistName, lastName, email));
    }

    public HashMap<Integer, Person> getMapOfPeople() {
        return mapOfPeople;
    }

    public void setMapOfPeople(HashMap<Integer, Person> mapOfPeople) {
        this.mapOfPeople = mapOfPeople;
    }
}