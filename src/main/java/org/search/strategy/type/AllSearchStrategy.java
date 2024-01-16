package org.search.strategy.type;

import org.search.person.Person;
import org.search.repository.PersonInvertedRepository;
import org.search.repository.PersonRepository;
import org.search.strategy.SearchStrategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class AllSearchStrategy extends SearchStrategy {

    @Override
    protected List<Person> findMatchingByDetailAndStrategyType(String detail, PersonRepository personRepository, PersonInvertedRepository personInvertedRepository) {
        List<Person> personList = new ArrayList<>();
        TreeMap<String, List<Integer>> invertedPeopleData = personInvertedRepository.getInvertedPeopleData();

        if (invertedPeopleData.containsKey(detail)) {
            for (Integer line : invertedPeopleData.get(detail)) {
                personList.add(personRepository.getMapOfPeople().get(line));
            }
        }
        return personList;
    }

    @Override
    protected List<Person> getResultBasedOnStrategy(List<List<Person>> allResults) {
        return allResults.stream()
                .flatMap(Collection::stream)
                .distinct()
                .filter(person -> allResults.stream().allMatch(list -> list.contains(person) || list == allResults.get(0)))
                .collect(Collectors.toList());

    }
}
