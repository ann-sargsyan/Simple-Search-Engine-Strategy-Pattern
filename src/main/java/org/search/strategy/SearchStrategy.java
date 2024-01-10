package org.search.strategy;

import org.search.person.Person;
import org.search.repository.PersonRepository;
import org.search.repository.PersonInvertedRepository;

import java.util.ArrayList;
import java.util.List;

public abstract class SearchStrategy {

    public List<Person> search(String[] details, PersonRepository personRepository, PersonInvertedRepository personInvertedRepository) {
        List<List<Person>> allResults = new ArrayList<>();
        for (String detail : details) {
            allResults.add(findMatchingByDetailAndStrategyType(detail, personRepository, personInvertedRepository));
        }

        return getResultBasedOnStrategy(allResults);
    }

    protected  abstract List<Person> findMatchingByDetailAndStrategyType(String detail, PersonRepository personRepository, PersonInvertedRepository personInvertedRepository);

    protected abstract List<Person> getResultBasedOnStrategy(List<List<Person>> allResults);
}
