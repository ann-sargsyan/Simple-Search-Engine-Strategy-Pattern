package org.search.user_interface;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.search.service.PersonService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class UserInterfaceTest {
    private static final String MENU = "=== Menu ===";
    private static final String FIND_PERSON_OPTION = "1 Find a person";
    private static final String FIND_ALL_PEOPLE_OPTION = "2 Print all people";
    private static final String EXIT_OPTION = "3 Exit";
    private static final String FIRST_THIRD_OPTION = "1\n3\n";
    private static final String SECOND_THIRD_OPTION = "2\n3\n";

    @Mock
    private PersonService personService;
    @Spy
    @InjectMocks
    private UserInterface userInterface;

    private InputStream inputStream;
    Scanner scanner;


    @Test
     void showMenu() {
        //> GIVEN
        PrintStream mockPrintStream = mock(PrintStream.class);
        System.setOut(mockPrintStream);

        //> WHEN
        userInterface.showMenu();

        //> THEN
        Mockito.verify(mockPrintStream).println(MENU);
        Mockito.verify(mockPrintStream).println(FIND_PERSON_OPTION);
        Mockito.verify(mockPrintStream).println(FIND_ALL_PEOPLE_OPTION);
        Mockito.verify(mockPrintStream).println(EXIT_OPTION);

    }

    @Test
    void makeAChooseFindPersonOption() {
        //> GIVEN
        String input = FIRST_THIRD_OPTION;
        inputStream = new ByteArrayInputStream(input.getBytes());
        scanner = new Scanner(inputStream);

        //> WHEN
        userInterface.makeAChoose(scanner);

        //> THEN
        Mockito.verify(personService).processDetailsForFindingPeople(scanner);
    }

    @Test
    void makeAChooseFindAllPeopleOption(){
        //> GIVEN
        String input = SECOND_THIRD_OPTION;
        inputStream = new ByteArrayInputStream(input.getBytes());
        scanner = new Scanner(inputStream);

        //> WHEN
        userInterface.makeAChoose(scanner);

        //> THEN
        Mockito.verify(personService).printAllPeople();
    }
}
