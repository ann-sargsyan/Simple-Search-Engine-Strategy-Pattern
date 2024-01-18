package org.search.user_interface;

import org.search.repository.PersonInvertedRepository;
import org.search.repository.PersonRepository;
import org.search.service.PersonSearch;
import org.search.service.PersonService;
import java.util.List;
import java.util.Scanner;

public class UserInterface {
    private static final String MENU = "=== Menu ===";
    private static final String FIND_PERSON_OPTION = "1 Find a person";
    private static final String FIND_ALL_PEOPLE_OPTION = "2 Print all people";
    private static final String EXIT_OPTION = "3 Exit";
    private static final String GOODBYE_MESSAGE = "Bye! ";
    private final PersonService personService = new PersonService(new PersonRepository(), new PersonInvertedRepository(), new PersonSearch());

    public void start(List<String> line, Scanner scanner) {
        personService.processPeopleDetails(line);
        makeAChoose(scanner);
    }

    public void showMenu() {
        System.out.println(MENU);
        System.out.println(FIND_PERSON_OPTION);
        System.out.println(FIND_ALL_PEOPLE_OPTION);
        System.out.println(EXIT_OPTION);
    }

    public void makeAChoose(Scanner scanner) {
        boolean isNeedToExit = true;
        while (isNeedToExit) {
            showMenu();
            switch (scanner.nextInt()) {
                case 1 -> personService.processDetailsForFindingPeople(scanner);
                case 2 -> personService.printAllPeople();
                case 3 -> {
                    System.out.println(GOODBYE_MESSAGE);
                    isNeedToExit = false;
                }
            }
        }
    }
}
