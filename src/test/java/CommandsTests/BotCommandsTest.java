package CommandsTests;

import Bot.Constants;
import CommandsParsers.BotCommandsParser;
import CommandsParsers.FilmsCommandsParser;
import JsonParsers.ChatsJsonParser;
import JsonParsers.FilmsJsonParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.api.methods.send.SendMessage;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BotCommandsTest {
    private BotCommandsParser commandsParser;
    private SendMessage requestMessage;
    private SendMessage responseMessage;

    @BeforeEach
    void init() {
        commandsParser = new BotCommandsParser();
        requestMessage = new SendMessage().setChatId(12345L);
        responseMessage = new SendMessage();
    }

    @Test
    void startCommandTest() throws IOException {
        responseMessage = getResponseMessage("/start");

        assertAll(
                () -> assertEquals(requestMessage.getText(), Constants.START_COMMAND),
                () -> assertEquals(responseMessage.getText(), Constants.START_MESSAGE),
                () -> assertEquals(responseMessage.getChatId(), requestMessage.getChatId())
        );
    }

    @Test
    void genresCommandTest() throws IOException {
        responseMessage = getResponseMessage("Жанры");

        assertAll(
                () -> assertEquals(requestMessage.getText(), Constants.GENRES_COMMAND),
                () -> assertEquals(responseMessage.getText(), FilmsCommandsParser.getGenres()),
                () -> assertEquals(responseMessage.getChatId(), requestMessage.getChatId())
        );
    }

    @Test
    void getGenreCommandTest() throws IOException {
        Map<String, String> genresMap = FilmsCommandsParser.getCommandsMap();

        for (Map.Entry<String, String> command : genresMap.entrySet()) {

            responseMessage = getResponseMessage(command.getValue());

            assertAll(
                    () -> assertEquals(requestMessage.getText(), command.getValue()),
                    () -> assertEquals(responseMessage.getText(), new FilmsJsonParser(command.getValue()).getFilms()),
                    () -> assertEquals(responseMessage.getChatId(), requestMessage.getChatId())
            );
        }
    }

    @Test
    void getFilmCommandTest() throws IOException {
        Map<String, String> genresMap = FilmsCommandsParser.getCommandsMap();

        for (Map.Entry<String, String> command : genresMap.entrySet()) {

            for (String film : new FilmsJsonParser(command.getValue()).getFilmsIds()) {

                responseMessage = getResponseMessage(film);

                assertAll(
                        () -> assertEquals(requestMessage.getText(), film),
                        () -> assertEquals(responseMessage.getText(), new FilmsJsonParser(command.
                                getValue()).getFilmInfo(film)),
                        () -> assertEquals(responseMessage.getChatId(), requestMessage.getChatId())
                );
            }
        }
    }

    @Test
    void getJournalFilmsCommandTest() throws IOException {
        responseMessage = getResponseMessage("Мой " + Constants.JOURNAL_EMOJI);

        assertAll(
                () -> assertEquals(requestMessage.getText(), Constants.JOURNAL_COMMAND),
                () -> assertEquals(responseMessage.getChatId(), requestMessage.getChatId()),
                () -> assertEquals(responseMessage.getText(), new ChatsJsonParser().
                        getChatFilms(requestMessage.getChatId()))
        );
        Long existChatId = 123214L;

        SendMessage existChatMessage = commandsParser.
                parseCommand(requestMessage.getText(), existChatId.toString());

        assertAll(
                () -> assertEquals(existChatMessage.getChatId(), existChatId.toString()),
                () -> assertEquals(existChatMessage.getText(), new ChatsJsonParser().
                        getChatFilms(existChatId.toString()))
        );
    }

    @Test
    void wrongCommandTest() throws IOException {
        responseMessage = getResponseMessage("banana");

        assertAll(
                () -> assertEquals(responseMessage.getText(), Constants.WRONG_MESSAGE),
                () -> assertEquals(responseMessage.getChatId(), requestMessage.getChatId())
        );
    }

    private SendMessage getResponseMessage(String command) throws IOException {
        return commandsParser.parseCommand(requestMessage.
                setText(command).getText(), requestMessage.getChatId());
    }
}
