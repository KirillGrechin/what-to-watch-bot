package Bot;

import CommandsParsers.BotCallbacksParser;
import CommandsParsers.BotCommandsParser;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.io.IOException;

public class Bot extends TelegramLongPollingBot {

    public static void main(String[] args) {
        disableWarnings();

        ApiContextInitializer.init();

        try {
            new TelegramBotsApi().registerBot(new Bot());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }

    public static void disableWarnings() {
        System.err.close();
        System.setErr(System.out);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            try {
                execute(new BotCommandsParser().parseCommand(
                        update.getMessage().getText().trim(),
                        update.getMessage().getChatId().toString()));
            } catch (TelegramApiException | IOException e) {
                e.printStackTrace();
            }
        } else if (update.hasCallbackQuery()) {
            try {
                execute(new BotCallbacksParser().parseCallback(
                        update.getCallbackQuery().getMessage().getChatId().toString(),
                        update.getCallbackQuery().getData().split(" ")));
            } catch (TelegramApiException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return Constants.USERNAME;
    }

    @Override
    public String getBotToken() {
        return Constants.TOKEN;
    }
}
