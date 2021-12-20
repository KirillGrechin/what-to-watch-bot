package CommandsTests;

import CommandsParsers.FilmsCommandsParser;
import JsonParsers.FilmsJsonParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class FilmsCommandsTest {
    private Map<String, String> commandsMap;

    @BeforeEach
    void init() {
        commandsMap = FilmsCommandsParser.getCommandsMap();
    }

    @Test
    void containsGenreCommandTest() {
        for (Map.Entry<String, String> command : commandsMap.entrySet()) {
            assertTrue(FilmsCommandsParser.containsGenre(command.getValue()));
        }
    }

    @Test
    void containsFilmCommandTest() throws IOException {
        for (Map.Entry<String, String> command : commandsMap.entrySet()) {
            for (String film : new FilmsJsonParser(command.getValue()).getFilmsIds()) {
                assertTrue(FilmsCommandsParser.containsFilm(film));
            }
        }
    }

    @Test
    void getFilmGenreCommandTest() {
        assertAll(
                () -> assertEquals(FilmsCommandsParser.getFilmGenre("/hf1"), "/horrors"),
                () -> assertEquals(FilmsCommandsParser.getFilmGenre("/def2"), "/detectives"),
                () -> assertNull(FilmsCommandsParser.getFilmGenre("banana"))
        );
    }
}
