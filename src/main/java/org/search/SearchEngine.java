package org.search;

import lombok.SneakyThrows;
import org.search.person.Person;
import org.search.repository.PersonRepository;
import org.search.repository.PersonInvertedRepository;
import org.search.strategy.SearchStrategy;
import org.search.strategy.type.AllSearchStrategy;
import org.search.strategy.type.AnySearchStrategy;
import org.search.strategy.type.NoneSearchStrategy;

import java.util.List;

public class SearchEngine {
    public List<Person> findPeopleBySelectedDetailAndStrategy(String[] details, String strategyType, PersonRepository personRepository, PersonInvertedRepository personInvertedRepository) {
        SearchStrategy strategy = determineStrategyByInput(strategyType);
        return strategy.search(details, personRepository, personInvertedRepository);
    }

    @SneakyThrows
    private SearchStrategy determineStrategyByInput(String strategyType)  {
        if ("ALL".equals(strategyType)) {
           return new AllSearchStrategy();
        } else if ("ANY".equals(strategyType)){
            return new AnySearchStrategy();
        } else if("NONE".equals(strategyType)){
            return new NoneSearchStrategy();
        }
        throw new ClassNotFoundException("There is no such a type of strategy");
    }
}
