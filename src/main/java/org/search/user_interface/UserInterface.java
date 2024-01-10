package org.search.user_interface;

import org.search.service.PersonService;

import java.util.List;
import java.util.Scanner;

public class UserInterface {
    private static final String MENU = "=== Menu ===";
    private static final String FIND_PERSON_OPTION = "1 Find a person";
    private static final String FIND_ALL_PEOPLE_OPTION = "2 Print all people";
    private static final String EXIT_OPTION = "3 Exit";
    private static final String GOODBYE_MESSAGE = "Bye! ";
    private Scanner scanner = new Scanner(System.in);
    private PersonService personService = new PersonService();

    public void start(List<String> line) {
        personService.processPeopleDetails(line);
        makeAChoose();
    }

    public void showMenu() {
        System.out.println(MENU);
        System.out.println(FIND_PERSON_OPTION);
        System.out.println(FIND_ALL_PEOPLE_OPTION);
        System.out.println(EXIT_OPTION);
    }

    public void makeAChoose() {
        boolean isNeedToExit = true;
        while (isNeedToExit) {
            showMenu();

            switch (scanner.nextInt()) {
                case 1:
                    personService.findMatchedPeople(scanner);
                    break;
                case 2:
                    personService.printAllPeople();
                    break;
                case 3:
                    System.out.println(GOODBYE_MESSAGE);
                    isNeedToExit = false;
            }
        }
    }
}
