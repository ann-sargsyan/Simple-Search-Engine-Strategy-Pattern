package org.search.main;


import org.search.user_interface.UserInterface;
import org.search.driver.PersonDriver;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        UserInterface userInterface = new UserInterface();
        List<String> lines = PersonDriver.convertFromFile("/Users/ansargsyan/Documents/target.txt");
        userInterface.start(lines, scanner);
    }

}
