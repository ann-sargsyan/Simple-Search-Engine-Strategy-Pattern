package org.search.strategy;

import org.search.person.Person;
import org.search.repository.PersonRepository;
import org.search.repository.PersonInvertedRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class SearchStrategy {

    public List<Person> search(String[] details, PersonRepository personRepository, PersonInvertedRepository personInvertedRepository) {
        List<List<Person>> allResults = Arrays.stream(details)
                .map(detail -> findMatchingByDetailAndStrategyType(detail, personRepository, personInvertedRepository))
                .collect(Collectors.toList());

        return getResultBasedOnStrategy(allResults);
    }

    protected abstract List<Person> findMatchingByDetailAndStrategyType(String detail, PersonRepository personRepository, PersonInvertedRepository personInvertedRepository);

    protected abstract List<Person> getResultBasedOnStrategy(List<List<Person>> allResults);
}
