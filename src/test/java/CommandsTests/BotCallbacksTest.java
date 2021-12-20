package CommandsTests;

import Bot.Constants;
import CommandsParsers.BotCallbacksParser;
import CommandsParsers.FilmsCommandsParser;
import JsonParsers.ChatsJsonParser;
import JsonParsers.FilmsJsonParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BotCallbacksTest {
    BotCallbacksParser callbacksParser;
    Long chatId;

    @BeforeEach
    void init() {
        callbacksParser = new BotCallbacksParser();
        chatId = 12356789L;
    }

    @Test
    void addRemoveFilmsCallbackTest() throws IOException {
        Map<String, String> genresMap = FilmsCommandsParser.getCommandsMap();

        int size = new ChatsJsonParser().getChatFilmsSize(chatId.toString()), count = 0;

        for (Map.Entry<String, String> command : genresMap.entrySet()) {
            for (String film : new FilmsJsonParser(command.getValue()).getFilmsIds()) {

                String data = command.getValue() + " " + film;

                assertEquals(callbacksParser.parseCallback(
                        chatId.toString(), data.split(" ")).getChatId(), chatId.toString());

                count++;
            }
        }
        assertEquals(new ChatsJsonParser().getChatFilmsSize(chatId.toString()), Math.abs(size - count));
    }

    @Test
    void addRemoveFilmCallbackTest() throws IOException {
        String data = "/horrors /hf1";

        if (new ChatsJsonParser().isFilmExist(chatId.toString(), "/hf1"))
        {
            assertEquals(callbacksParser.parseCallback(chatId.toString(),
                   data.split(" ")).getText(), Constants.REMOVE_FILM);
        } else {
            assertEquals(callbacksParser.parseCallback(chatId.toString(),
                    data.split(" ")).getText(), Constants.ADD_FILM);
        }
    }

    @Test
    void wrongCallbackTest() throws IOException {
        assertEquals(callbacksParser.parseCallback(chatId.toString(),
                "banana".split(" ")).getText(), Constants.WRONG_MESSAGE);
    }
}
