package JsonParsers;

import Bot.Constants;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ChatsJsonParser {

    public static final String CHATS_PATH = "Chats.json";

    private final JSONArray chatsArray;

    public ChatsJsonParser() throws IOException {
        chatsArray = new JSONArray(Files.readString(Path.of(CHATS_PATH)));
    }

    public String addRemoveChatFilm(String genre, String filmId, String chatId) throws IOException {
        JSONObject film = new FilmsJsonParser(genre).getJSONObjectFilm(filmId);
        Integer index = JsonUtilities.getJSONObjectIndex(chatsArray, chatId);
        String resultString = Constants.ADD_FILM;
        boolean isChatExist = false;
        boolean isFilmExist = false;

        if (!index.equals(Integer.MIN_VALUE)) {
            JSONArray jsonArray = chatsArray.getJSONObject(index).getJSONArray("films");
            index = JsonUtilities.getJSONObjectIndex(jsonArray, film.getString("id"));
            isChatExist = true;

            if (!index.equals(Integer.MIN_VALUE)) {
                jsonArray.remove(index);
                resultString = Constants.REMOVE_FILM;
                isFilmExist = true;
            }
            if (!isFilmExist) {
                jsonArray.put(film);
            }
        }
        if (!isChatExist) {
            chatsArray.put(new JSONObject().put("id", chatId).put("films", new JSONArray().put(film)));
        }
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(CHATS_PATH));
        bufferedWriter.write(chatsArray.toString(3));
        bufferedWriter.close();

        return resultString;
    }

    public String getChatFilms(String chatId) {
        Integer index = JsonUtilities.getJSONObjectIndex(chatsArray, chatId);
        String resultString = Constants.EMPTY_JOURNAL;

        if (!index.equals(Integer.MIN_VALUE)) {
            JSONArray jsonArray = chatsArray.getJSONObject(index).getJSONArray("films");

            if (!jsonArray.isEmpty()) {
                resultString = "";
                for (int j = 0; j < jsonArray.length(); j++) {
                    JSONObject object = jsonArray.getJSONObject(j);

                    resultString += "● " + object.getString("name") + " ("
                            + object.getString("year") + "; "
                            + object.getString("rating") + "; "
                            + object.getString("id") + ")\n";
                }
            }
        }
        return resultString;
    }

    public Integer getChatFilmsSize(String chatId) {
        Integer index = JsonUtilities.getJSONObjectIndex(chatsArray, chatId);

        if (!index.equals(Integer.MIN_VALUE)) {
            return chatsArray.getJSONObject(index).getJSONArray("films").length();
        }
        return 0;
    }

    public boolean isFilmExist(String chatId, String film) {
        Integer index = JsonUtilities.getJSONObjectIndex(chatsArray, chatId);

        if (!index.equals(Integer.MIN_VALUE)) {
            JSONArray jsonArray = chatsArray.getJSONObject(index).getJSONArray("films");

            if (!jsonArray.isEmpty()) {

                for (int j = 0; j < jsonArray.length(); j++) {
                    JSONObject object = jsonArray.getJSONObject(j);

                    if (object.getString("id").equals(film)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
