package org.search.service;

import org.search.SearchEngine;
import org.search.person.Person;
import org.search.repository.PersonInvertedRepository;
import org.search.repository.PersonRepository;

import java.util.List;

public class PersonSearch {

    private SearchEngine searchEngine;

    public PersonSearch() {
        this.searchEngine = new SearchEngine();
    }

    public List<Person> findPersonDetails(String[] details, String strategy, PersonRepository personRepository, PersonInvertedRepository personInvertedRepository) {
        return searchEngine.findPeopleBySelectedDetailAndStrategy(details, strategy, personRepository, personInvertedRepository);
    }
}
