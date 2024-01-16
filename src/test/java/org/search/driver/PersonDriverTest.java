package org.search.driver;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PersonDriverTest {
    private static final String FILE_PATH = "/Users/ansargsyan/IdeaProjects/Simple Search Engine Strategy Pattern/src/test/resources/testfile.txt";
    private static final String WRONG_FILE_PATH = "/Users/ansargsyan/Documents/target";
    private static final String EXCEPTION_MESSAGE = "There is no correct file";

    @Test
     void convertFromFile() {
        IOException exception = Assertions.assertThrows(IOException.class, () -> {
            PersonDriver.convertFromFile(WRONG_FILE_PATH);
        });

        assertEquals(exception.getMessage(), EXCEPTION_MESSAGE);
    }

    @Test
    void readLinesFromFile() throws IOException {
        var lines = PersonDriver.convertFromFile(FILE_PATH);

       String expectedLines = "Dwight Joseph djo@gmail.com";
       assertEquals(expectedLines, lines.get(0));
    }

}
