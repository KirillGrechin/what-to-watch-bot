package JsonParsers;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FilmsJsonParser {

    public static final String FILMS_PATH = "Films.json";

    private final String genre;
    private final JSONArray filmsArray;

    public FilmsJsonParser(String genre) throws IOException {
        this.genre = genre;
        this.filmsArray = getJSONArrayFilms();
    }

    private JSONArray getJSONArrayFilms() throws IOException {
        return new JSONObject(Files.readString(Path.of(FILMS_PATH))).getJSONArray(genre);
    }

    public JSONArray getFilmsArray() {
        return filmsArray;
    }

    public JSONObject getJSONObjectFilm(String filmId) {
        return filmsArray.getJSONObject(JsonUtilities.getJSONObjectIndex(filmsArray, filmId));
    }

    public String getFilms() {
        String resultString = "";

        for (int i = 0; i < filmsArray.length(); i++) {
            JSONObject jsonObject = filmsArray.getJSONObject(i);

            resultString += "● "+ jsonObject.getString("name") + " ("
                    + jsonObject.getString("year") + "; "
                    + jsonObject.getString("rating") + "; "
                    + jsonObject.getString("id") + ")\n";
        }
        return resultString;
    }

    public String getFilmInfo(String filmId) {
        JSONObject jsonObject = getJSONObjectFilm(filmId);

        return "● Название: " + jsonObject.getString("name") + "\n"
                + "● Год: " + jsonObject.getString("year") + "\n"
                + "● Рейтинг: " + jsonObject.getString("rating") + "\n"
                + "● Ограничение: " + jsonObject.getString("limit") + "\n"
                + "● Трейлер: " + jsonObject.getString("trailer") + "\n";
    }

    public String[] getFilmsIds() {
        String[] filmsIds = new String[filmsArray.length()];

        for (int i = 0; i < filmsArray.length(); i++) {
            JSONObject jsonObject = filmsArray.getJSONObject(i);
            filmsIds[i] = jsonObject.getString("id");
        }
        return filmsIds;
    }
}
