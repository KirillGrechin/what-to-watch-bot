package CommandsParsers;

import Bot.Constants;
import JsonParsers.ChatsJsonParser;
import org.telegram.telegrambots.api.methods.send.SendMessage;

import java.io.IOException;

public class BotCallbacksParser {

    public SendMessage parseCallback(String chatId, String[] split) throws IOException {

        if (FilmsCommandsParser.containsGenre(split[0]) && FilmsCommandsParser.containsFilm(split[1])) {
            return addRemoveFilm(split, chatId).setChatId(chatId);
        }
        return new SendMessage().setText(Constants.WRONG_MESSAGE).setChatId(chatId);
    }

    private SendMessage addRemoveFilm(String[] split, String chatId) throws IOException {
        return new SendMessage().setText(new ChatsJsonParser().addRemoveChatFilm(split[0], split[1], chatId));
    }
}
