package Bot;

import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class Keyboards {

    public static ReplyKeyboardMarkup getMenuButtons() {
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(Constants.GENRES_COMMAND);
        keyboardRow.add(Constants.JOURNAL_COMMAND);
        keyboardRows.add(keyboardRow);
        return new ReplyKeyboardMarkup().setKeyboard(keyboardRows);
    }

    public static InlineKeyboardMarkup getJournalButtons(String data) {
        List<InlineKeyboardButton> buttonsList = new ArrayList<>();
        buttonsList.add(new InlineKeyboardButton().setText(Constants.JOURNAL_EMOJI)
                        .setCallbackData(data));
        List<List<InlineKeyboardButton>> rowsList = new ArrayList<>();
        rowsList.add(buttonsList);
        return new InlineKeyboardMarkup().setKeyboard(rowsList);
    }
}
