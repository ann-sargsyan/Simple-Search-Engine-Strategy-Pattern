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
    private static final String ALL = "ALL";
    private static final String ANY = "ANY";
    private static final String NONE = "NONE";
    private static final String WRONG_STRATEGY_INPUT_MESSAGE = "There is no such a type of strategy";

    public List<Person> findPeopleBySelectedDetailAndStrategy(String[] details, String strategyType, PersonRepository personRepository, PersonInvertedRepository personInvertedRepository) {
        SearchStrategy strategy = determineStrategyByInput(strategyType);
        return strategy.search(details, personRepository, personInvertedRepository);
    }

    @SneakyThrows
    private SearchStrategy determineStrategyByInput(String strategyType)  {
        switch (strategyType) {
            case ALL -> {
                return new AllSearchStrategy();
            }
            case ANY -> {
                return new AnySearchStrategy();
            }
            case NONE -> {
                return new NoneSearchStrategy();
            }
            default -> throw new ClassNotFoundException(WRONG_STRATEGY_INPUT_MESSAGE);
        }
    }
}
