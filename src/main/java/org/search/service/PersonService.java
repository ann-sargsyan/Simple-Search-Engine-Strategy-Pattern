package org.search.service;

import org.search.person.Person;
import org.search.repository.PersonInvertedRepository;
import org.search.repository.PersonRepository;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class PersonService {
    private static final int SIGN_OF_ZERO = 0;
    private static final int SIGN_OF_ONE = 1;
    private static final int SIGN_OF_TWO = 2;
    private static final int SIGN_OF_THREE = 3;
    private static final String SIGN_OF_SPACE = " ";
    private static final String MATCH_WHITESPACES = "\\s";
    private static final String NO_MATCHING_PEOPLE_MESSAGE = "No matching people found.";
    private static final String MATCHING_PEOPLE_MESSAGE = " persons found: ";
    private static final String INPUT_MESSAGE = "Enter a name or email to search all suitable people";

    private PersonRepository repository;

    private PersonInvertedRepository personInvertedRepository;

    private PersonSearch personSearch;

    public PersonService() {
        this.repository = new PersonRepository();
        this.personSearch = new PersonSearch();
        this.personInvertedRepository = new PersonInvertedRepository();
    }

    public void printAllPeople() {
        repository.getMapOfPeople().forEach((s, person) -> printDetails(person));
    }

    public void processPeopleDetails(List<String> details) {
        for (int i = 0; i < details.size(); i++) {
            var stringInput = details.get(i);

            Person person = createPersonWithDetailsFromInput(stringInput, i);
            repository.savePerson(person.firstName(), person.lastName(), person.email(), i);
        }
    }

    public void findMatchedPeople(Scanner scanner) {
        messageForSelectStrategy(scanner);
        String strategy = scanner.nextLine().trim().toUpperCase();
        validateInput(strategy);
        messageForSelectDetails();
        String inputLine = scanner.nextLine();
        String[] details = inputLine.split("\\s+");
        List<Person> result = personSearch.findPersonDetails(details, strategy, repository, personInvertedRepository);
        System.out.println(showCountOfMatches(result));
        result.forEach(this::printDetails);
    }

    private String showCountOfMatches(List<Person> result) {
        if (result.isEmpty()) {
            return NO_MATCHING_PEOPLE_MESSAGE;
        }
        return result.size() + MATCHING_PEOPLE_MESSAGE;
    }

    private void validateInput(String strategy) {
        if(!(strategy.equals("ALL") || strategy.equals("ANY") || strategy.equals("NONE"))){
            System.out.println("There is no strategy type that you chose.");
            System.exit(0);
        }
    }

    private Person createPersonWithDetailsFromInput(String input, int numberOfLine) {
        String[] personDetails = input.split(MATCH_WHITESPACES, SIGN_OF_THREE);

        String firstName = personDetails[SIGN_OF_ZERO];
        String lastName = personDetails[SIGN_OF_ONE];
        String email = personDetails.length > SIGN_OF_TWO ? personDetails[SIGN_OF_TWO] : null;

        personInvertDetails(firstName, lastName, email, numberOfLine);

        return new Person(firstName, lastName, email);
    }

    private void personInvertDetails(String firstName, String lastName, String email, int numberOfLine) {
        personInvertedRepository.invertPersonDetail(firstName, numberOfLine);
        personInvertedRepository.invertPersonDetail(lastName, numberOfLine);
        Optional.ofNullable(email)
                .ifPresent(e -> personInvertedRepository.invertPersonDetail(e, numberOfLine));
    }

    private void printDetails(Person person) {
        System.out.println(person.firstName() + SIGN_OF_SPACE + person.lastName() + SIGN_OF_SPACE + (person.email() != null ? SIGN_OF_SPACE + person.email() : SIGN_OF_SPACE));
    }

    private void messageForSelectDetails() {
        System.out.println(INPUT_MESSAGE);
    }

    private void messageForSelectStrategy(Scanner scanner){
        System.out.println("Select a matching strategy: ALL, ANY, NONE");
        scanner.nextLine();
    }
}
