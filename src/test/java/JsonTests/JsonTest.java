package JsonTests;

import JsonParsers.ChatsJsonParser;
import JsonParsers.FilmsJsonParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Bot.Constants;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class JsonTest {
    ChatsJsonParser jsonParser;

    @BeforeEach
    void init() throws IOException {
        jsonParser = new ChatsJsonParser();
    }

    @Test
    void filesExistTest() {
        assertAll(
                () -> assertTrue(Files.exists(Paths.get(FilmsJsonParser.FILMS_PATH))),
                () -> assertTrue(Files.exists(Paths.get(ChatsJsonParser.CHATS_PATH)))
        );
    }

    @Test
    void addRemoveChatFilmTest() throws IOException {
        String chatId = "12324", film = "/cf3", genre = "/comedies";

        int size = jsonParser.getChatFilmsSize("12324");

        if (jsonParser.isFilmExist(chatId, film))
        {
            assertEquals(jsonParser.addRemoveChatFilm(genre, film, chatId), Constants.REMOVE_FILM);
            assertEquals(jsonParser.getChatFilmsSize(chatId), size - 1);
        } else {
            assertEquals(jsonParser.addRemoveChatFilm(genre, film, chatId), Constants.ADD_FILM);
            assertEquals(jsonParser.getChatFilmsSize(chatId), size + 1);
        }
    }

    @Test
    void getChatFilmsTest() {
        assertEquals(jsonParser.getChatFilms("banana"), Constants.EMPTY_JOURNAL);
    }
}
