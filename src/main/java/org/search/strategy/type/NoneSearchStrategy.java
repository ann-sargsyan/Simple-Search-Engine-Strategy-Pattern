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
            invertedPeopleData.get(detail).stream()
                    .map(line -> personRepository.getMapOfPeople().get(line))
                    .forEach(personList::remove);
        }
        return personList.stream().toList();
    }

    @Override
    protected List<Person> getResultBasedOnStrategy(List<List<Person>> allResults) {
        return allResults.stream()
                .flatMap(Collection::stream)
                .distinct()
                .filter(person -> allResults.stream()
                        .allMatch(list -> list.contains(person) || list == allResults.get(0)))
                .collect(Collectors.toList());

    }
}