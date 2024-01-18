package org.search.service;

import lombok.RequiredArgsConstructor;
import org.search.person.Person;
import org.search.repository.PersonInvertedRepository;
import org.search.repository.PersonRepository;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.StringJoiner;

@RequiredArgsConstructor
public class PersonService {
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;
    private static final String MATCH_WHITESPACE = "\\s";

    private static final String MATCH_WHITESPACES = "\\s+";
    private static final String NO_MATCHING_PEOPLE_MESSAGE = "No matching people found.";
    private static final String MATCHING_PEOPLE_MESSAGE = " persons found: ";
    private static final String MESSAGE_FOR_SELECT_STRATEGY = "Select a matching strategy: ALL, ANY, NONE";
    private static final String INPUT_MESSAGE = "Enter a name or email to search all suitable people";

    private final PersonRepository repository;

    private final PersonInvertedRepository personInvertedRepository;

    private final PersonSearch personSearch;

    public void printAllPeople() {
        repository.getMapOfPeople().forEach((s, person) -> System.out.println(getDetails(person)));
    }

    public void processPeopleDetails(List<String> details) {
        for (int i = 0; i < details.size(); i++) {
            var stringInput = details.get(i);

            Person person = createPersonWithDetailsFromInput(stringInput, i);
            repository.savePerson(person.firstName(), person.lastName(), person.email(), i);
        }
    }

    public void processDetailsForFindingPeople(Scanner scanner) {
        String strategy = getStrategyTypeFromInput(scanner);
        String[] details = getDetailsFromInput(scanner);
        List<Person> result = findMatchedPeople(details, strategy);
        System.out.println(showCountOfMatches(result));
        result.forEach(person -> System.out.println(getDetails(person)));
    }

    public List<Person> findMatchedPeople(String[] details, String strategy) {
        return personSearch.findPersonDetails(details, strategy, repository, personInvertedRepository);
    }

    private String[] getDetailsFromInput(Scanner scanner) {
        messageForSelectDetails();
        String inputLine = scanner.nextLine();
        return inputLine.split(MATCH_WHITESPACES);
    }

    private String getStrategyTypeFromInput(Scanner scanner) {
        messageForSelectStrategy(scanner);
        return scanner.nextLine().trim().toUpperCase();
    }

    private String showCountOfMatches(List<Person> result) {
        return result.isEmpty() ? NO_MATCHING_PEOPLE_MESSAGE : result.size() + MATCHING_PEOPLE_MESSAGE;
    }

    private Person createPersonWithDetailsFromInput(String input, int numberOfLine) {
        String[] personDetails = input.split(MATCH_WHITESPACE, THREE);

        String firstName = personDetails[ZERO];
        String lastName = personDetails[ONE];
        String email = personDetails.length > TWO ? personDetails[TWO] : null;

        personInvertDetails(firstName, lastName, email, numberOfLine);

        return new Person(firstName, lastName, email);
    }

    private void personInvertDetails(String firstName, String lastName, String email, int numberOfLine) {
        personInvertedRepository.invertPersonDetail(firstName, numberOfLine);
        personInvertedRepository.invertPersonDetail(lastName, numberOfLine);
        Optional.ofNullable(email)
                .ifPresent(e -> personInvertedRepository.invertPersonDetail(e, numberOfLine));
    }

    private String getDetails(Person person) {
        StringJoiner joinPersonDetails = new StringJoiner(" ");
        return joinPersonDetails.add(person.firstName()).add(person.lastName())
                .add(Optional.ofNullable(person.email()).map(email -> " " + email).orElse("")).toString();
    }

    private void messageForSelectDetails() {
        System.out.println(INPUT_MESSAGE);
    }

    private void messageForSelectStrategy(Scanner scanner) {
        System.out.println(MESSAGE_FOR_SELECT_STRATEGY);
        scanner.nextLine();
    }
}
