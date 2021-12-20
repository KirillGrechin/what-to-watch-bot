package Bot;

public class Constants {

    public final static String USERNAME = "what_to_watch_kpo_bot";
    public final static String TOKEN ="2130952343:AAGx0jVy2DBJv2L24ZFFaWkLO8elywKi3bg";

    public final static String START_EMOJI = "\uD83D\uDE42";
    public final static String WRONG_EMOJI = "\uD83E\uDD7A";
    public final static String JOURNAL_EMOJI = "\uD83D\uDCD5";

    public final static String START_MESSAGE = "Привет, это бот подбора фильмов! " +
            "Для выбора фильма, нажимай на жанры " + START_EMOJI;
    public final static String WRONG_MESSAGE = "Я не понял " + WRONG_EMOJI;

    public final static String START_COMMAND = "/start";
    public final static String GENRES_COMMAND = "Жанры";
    public final static String JOURNAL_COMMAND = "Мой " + JOURNAL_EMOJI;

    public final static String ADD_FILM = "Фильм добавлен в " + JOURNAL_EMOJI;
    public final static String REMOVE_FILM = "Фильм удален из " + JOURNAL_EMOJI;
    public final static String EMPTY_JOURNAL = "Вы ничего не добавляли " + WRONG_EMOJI;
}
