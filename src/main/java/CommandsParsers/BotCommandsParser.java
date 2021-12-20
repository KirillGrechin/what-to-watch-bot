package CommandsParsers;

import Bot.Constants;
import Bot.Keyboards;
import JsonParsers.ChatsJsonParser;
import JsonParsers.FilmsJsonParser;
import org.telegram.telegrambots.api.methods.send.SendMessage;

import java.io.IOException;

public class BotCommandsParser {

    public SendMessage parseCommand(String message, String chatId) throws IOException {

        if (message.equals(Constants.START_COMMAND)) {
            return startCommand().setChatId(chatId);
        }
        if (message.equals(Constants.GENRES_COMMAND)) {
            return genresCommand().setChatId(chatId);
        }
        if (FilmsCommandsParser.containsGenre(message)) {
            return getGenreCommand(message).setChatId(chatId);
        }
        if (FilmsCommandsParser.containsFilm(message)) {
            return getFilmCommand(message).setChatId(chatId);
        }
        if (message.equals(Constants.JOURNAL_COMMAND)) {
            return getJournalFilms(chatId).setChatId(chatId);
        }
        return new SendMessage().setText(Constants.WRONG_MESSAGE).setChatId(chatId);
    }

    private SendMessage startCommand() {
        return new SendMessage().setText(Constants.START_MESSAGE)
                .setReplyMarkup(Keyboards.getMenuButtons());
    }

    private SendMessage genresCommand() {
        return new SendMessage().setText(FilmsCommandsParser.getGenres());
    }

    private SendMessage getGenreCommand(String message) throws IOException {
        return new SendMessage().setText(new FilmsJsonParser(message).getFilms());
    }

    private SendMessage getFilmCommand(String message) throws IOException {
        String command = FilmsCommandsParser.getFilmGenre(message);
        return new SendMessage().setText(new FilmsJsonParser(command).getFilmInfo(message))
                .setReplyMarkup(Keyboards.getJournalButtons(command + " " + message));
    }

    private SendMessage getJournalFilms(String chatId) throws IOException {
        return new SendMessage().setText(new ChatsJsonParser().getChatFilms(chatId));
    }
}
