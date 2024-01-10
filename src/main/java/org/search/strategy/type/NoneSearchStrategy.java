package org.search.strategy.type;

import org.search.person.Person;
import org.search.repository.PersonInvertedRepository;
import org.search.repository.PersonRepository;
import org.search.strategy.SearchStrategy;

import java.util.*;
import java.util.stream.Collectors;

public class NoneSearchStrategy extends SearchStrategy {

    @Override
    protected List<Person> findMatchingByDetailAndStrategyType(String detail, PersonRepository personRepository, PersonInvertedRepository personInvertedRepository) {
        TreeMap<String, List<Integer>> invertedPeopleData = personInvertedRepository.getInvertedPeopleData();
        Collection<Person> personList = personRepository.getMapOfPeople().values();

        if (invertedPeopleData.containsKey(detail)) {
            for (Integer line : invertedPeopleData.get(detail)) {
                personList.remove(personRepository.getMapOfPeople().get(line));
            }
        }
        return personList.stream().toList();
    }

    @Override
    protected List<Person> getResultBasedOnStrategy(List<List<Person>> allResults) {
        return allResults.stream()
                .skip(1) // Skip the first list to have a basis for comparison
                .flatMap(personList -> personList.stream())
                .distinct()
                .filter(person -> allResults.stream().allMatch(list -> list.contains(person)))
                .collect(Collectors.toList());
    }

}
