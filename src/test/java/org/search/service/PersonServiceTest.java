package org.search.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.search.driver.PersonDriver;
import org.search.person.Person;
import org.search.repository.PersonInvertedRepository;
import org.search.repository.PersonRepository;

import java.io.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {
    private static final String FILE_PATH = "/Users/ansargsyan/IdeaProjects/Simple Search Engine Strategy Pattern/src/test/resources/testfile.txt";
    private static final String MATCHING_PEOPLE_MESSAGE = " persons found: ";
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private List<String> listOfDetails;

    PersonService personService;

    PersonRepository personRepository;

    PersonInvertedRepository personInvertedRepository;

    PersonServiceTest() throws IOException {
        personRepository = new PersonRepository();
        personInvertedRepository = new PersonInvertedRepository();
        personService = new PersonService(personRepository, personInvertedRepository, new PersonSearch());
        listOfDetails = PersonDriver.convertFromFile(FILE_PATH);
    }

    @BeforeEach
    void setUp() {
        personInvertedRepository.getInvertedPeopleData().clear();
        personRepository.getMapOfPeople().clear();
        personService.processPeopleDetails(listOfDetails);
    }

    @Test
    void printAllPeople() {
        // GIVEN
        System.setOut(new PrintStream(outputStreamCaptor));

        // WHEN
        personService.printAllPeople();
        String actualResult = "Dwight Joseph  djo@gmail.com\nRene Webb  webb@gmail.com\nKatie Jacobs  \nErick Harrington  harrington@gmail.com\nMyrtle Medina  \nErick Burgess";

        // THEN
        assertEquals(actualResult, outputStreamCaptor.toString().trim());
    }

    @Test
    void processPeopleDetails() {
        //> THEN
        assertNotNull(personRepository.getMapOfPeople());
    }

    @Test
    void findMatchedPeopleWithStrategyAll() {
        //> GIVEN
        String strategy = "ALL";
        String[] selectedDetails = new String[]{"erick ", "harrington"};

        //> WHEN
        List<Person> personList = personService.findMatchedPeople(selectedDetails, strategy);
        Person firstPerson = personList.get(0);
        String actualResult = firstPerson.firstName() + " " + firstPerson.lastName() + " " + firstPerson.email();

        //> THEN
        assertEquals("Erick Harrington harrington@gmail.com", actualResult);
    }
    @Test
    void findMatchedPeopleWithStrategyAny() {
        //> GIVEN
        String strategy = "ANY";
        String[] selectedDetails = new String[]{"erick ", "harrington"};

        //> WHEN
        List<Person> personList = personService.findMatchedPeople(selectedDetails, strategy);
        Person firstPerson = personList.get(0);
        String actualResult = firstPerson.firstName() + " " + firstPerson.lastName() + " " + firstPerson.email();

        //> THEN
        assertEquals("Erick Harrington harrington@gmail.com", actualResult);
    }
    @Test
    void findMatchedPeopleWithStrategyNone() {
        //> GIVEN
        String strategy = "NONE";
        String[] selectedDetails = new String[]{"erick ", "harrington"};

        //> WHEN
        List<Person> personList = personService.findMatchedPeople(selectedDetails, strategy);
        Person firstPerson = personList.get(0);
        String actualResult = firstPerson.firstName() + " " + firstPerson.lastName() + " " + firstPerson.email();

        //> THEN
        assertEquals("Dwight Joseph djo@gmail.com", actualResult);
    }

    @Test
    void getDetailsFromInput() throws ReflectiveOperationException {
        //> GIVEN
        String input = "Erick Harrington";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);

        Method privateMethod = PersonService.class.getDeclaredMethod("getDetailsFromInput", Scanner.class);
        privateMethod.setAccessible(true);

        //> WHEN
        String[] actualResult = (String[]) privateMethod.invoke(personService, scanner);

        //> THEN
        assertArrayEquals(new String[]{"Erick", "Harrington"}, actualResult);

    }


    @Test
    void getStrategyTypeAll() throws ReflectiveOperationException {
        //> GIVEN
        String input = "\nALL";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);

        Method privateMethod = PersonService.class.getDeclaredMethod("getStrategyTypeFromInput", Scanner.class);
        privateMethod.setAccessible(true);

        //> WHEN
        String actualResult = (String) privateMethod.invoke(personService, scanner);

        //> THEN
        assertEquals("ALL", actualResult);
    }

    @Test
    void showCountOfMatches() throws ReflectiveOperationException {
        //> GIVEN
        List<Person> personList = new ArrayList<>();
        personList.add(new Person("Erick","Harrington", "harrington@gmail.com"));
        personList.add(new Person("Rebe","Webb","webb@gmail.com"));

        Method privateMethod = PersonService.class.getDeclaredMethod("showCountOfMatches", List.class);
        privateMethod.setAccessible(true);

        //> WHEN
        String actualResult = (String) privateMethod.invoke(personService, personList);
        String expectedResult = 2 + MATCHING_PEOPLE_MESSAGE;

        //> THEN
        assertEquals(expectedResult, actualResult);

    }

}
