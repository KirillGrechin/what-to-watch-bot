package CommandsParsers;

import JsonParsers.FilmsJsonParser;
import JsonParsers.JsonUtilities;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FilmsCommandsParser {

    public static Map<String, String> getCommandsMap() {
        return commandsMap;
    }

    public final static Map<String, String> commandsMap = new HashMap() {{
        put("Комедии", "/comedies");
        put("Драмы", "/dramas");
        put("Боевики", "/thrillers");
        put("Ужасы", "/horrors");
        put("Детективы", "/detectives");
    }};

    public static String getGenres() {
        String resultString = "";

        for (Map.Entry<String, String> command : commandsMap.entrySet()) {
            resultString += "● " + command.getKey() + " (" + command.getValue() + ")\n";
        }
        return resultString;
    }

    public static boolean containsGenre(String command) {
        return commandsMap.containsValue(command);
    }

    public static boolean containsFilm(String filmId) throws IOException {
        for (Map.Entry<String, String> command : commandsMap.entrySet()) {
            if (!JsonUtilities.getJSONObjectIndex(new FilmsJsonParser(command.getValue())
                    .getFilmsArray(), filmId).equals(Integer.MIN_VALUE)) {
                return true;
            }
        }
        return false;
    }

    public static String getFilmGenre(String filmId) throws IOException {
        for (Map.Entry<String, String> command : commandsMap.entrySet()) {
            if (!JsonUtilities.getJSONObjectIndex(new FilmsJsonParser(command.getValue())
                    .getFilmsArray(), filmId).equals(Integer.MIN_VALUE)) {
                return command.getValue();
            }
        }
        return null;
    }
}
