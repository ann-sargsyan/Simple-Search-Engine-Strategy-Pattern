package org.search.repository;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class PersonInvertedRepository {

    private PersonRepository repository;

    @Getter
    private static TreeMap<String, List<Integer>> invertedPeopleData;

    public PersonInvertedRepository() {
        this.repository = new PersonRepository();
        invertedPeopleData = new TreeMap<>();
    }

    public void invertPersonDetail(String detail, int numberOfLine) {
        detail = detail.toLowerCase();
        if (!invertedPeopleData.containsKey(detail)) {
            invertedPeopleData.put(detail, new ArrayList<>());
        }
        invertedPeopleData.get(detail).add(numberOfLine);
    }

    public static void makeEmpty(){
        invertedPeopleData.clear();
    }

    public TreeMap<String, List<Integer>> getInvertedPeopleData() {
        return invertedPeopleData;
    }

    public static void setInvertedPeopleData(TreeMap<String, List<Integer>> invertedPeopleData) {
        PersonInvertedRepository.invertedPeopleData = invertedPeopleData;
    }
}
