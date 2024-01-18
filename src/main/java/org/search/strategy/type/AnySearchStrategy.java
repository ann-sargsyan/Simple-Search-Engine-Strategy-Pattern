package org.search.strategy.type;

import org.search.person.Person;
import org.search.repository.PersonInvertedRepository;
import org.search.repository.PersonRepository;
import org.search.strategy.SearchStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class AnySearchStrategy extends SearchStrategy {

    @Override
    protected List<Person> findMatchingByDetailAndStrategyType(String detail, PersonRepository personRepository, PersonInvertedRepository personInvertedRepository) {
        List<Person> personList = new ArrayList<>();
        TreeMap<String, List<Integer>> invertedPeopleData = personInvertedRepository.getInvertedPeopleData();

        if (invertedPeopleData.containsKey(detail)) {
            invertedPeopleData.get(detail).stream()
                    .map(line -> personRepository.getMapOfPeople().get(line))
                    .forEach(personList::add);
        }
        return personList;
    }

    @Override
    protected List<Person> getResultBasedOnStrategy(List<List<Person>> allResults) {
        return allResults.stream()
                .flatMap(List::stream)
                .distinct()
                .collect(Collectors.toList());
    }
}
