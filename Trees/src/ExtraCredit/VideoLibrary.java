package ExtraCredit;

import java.util.*;

public class VideoLibrary {

    private List<String> alias;
    private Map<String, List<Movie>> directors;
    private Map<String, Integer> timeUsed;
    private String mostUsed;

    public VideoLibrary() {
        this.alias = new ArrayList<>();
        this.directors = new HashMap<>();
        this.timeUsed = new HashMap<>();
        this.mostUsed = null;
    }

    public void add(String alias, String title, int year, List<String> directors, List<String> actors) {
        if (this.alias.contains(alias)) {
            throw new IllegalArgumentException("Alias already existed");
        }
        Movie movie = new Movie(title, year, directors, actors);
        this.alias.add(alias);
        for (String director : directors) {
            if (this.directors.containsKey(director)) {
                List<Movie> movies = this.directors.get(director);
                movies.add(movie);
                this.directors.put(director, movies);
            } else {
                List<Movie> movies = new ArrayList<>();
                movies.add(movie);
                this.directors.put(director, movies);
            }
        }
    }

    public void add(String alias, String title, int year, String director, List<String> actors) {
        List<String> directors = new ArrayList<>();
        directors.add(director);
        add(alias, title, year, directors, actors);
    }

    public void add(String alias, String title, int year, String director, String actor) {
        List<String> directors = new ArrayList<>();
        directors.add(director);
        List<String> actors = new ArrayList<>();
        actors.add(actor);
        add(alias, title, year, directors, actors);
    }

    public void add(String alias, String title, int year, List<String> directors, String actor) {
        List<String> actors = new ArrayList<>();
        actors.add(actor);
        add(alias, title, year, directors, actors);
    }

    public void usingMovie(String alias) {
        if (!this.alias.contains(alias))
            throw new IllegalArgumentException("Alias does not exist in this library");
        if (timeUsed.containsKey(alias)) {
            timeUsed.put(alias, timeUsed.get(alias) + 1);
        } else {
            timeUsed.put(alias, 1);
        }
    }

    private void updateMostUsed() {
        Set<String> keys = this.timeUsed.keySet();
        int max = 0;
        for (String key : keys) {
            if (this.timeUsed.get(key) > max) {
                max = this.timeUsed.get(key);
                this.mostUsed = key;
            }
        }
    }

    public String getMostUsedMovie() {
        if (this.timeUsed.size() == 0) {
            return null;
        }
        updateMostUsed();
        return this.mostUsed;
    }

    public List<String> getDirectorsMovie(String director) {
        List<Movie> movies = this.directors.get(director);
        Collections.sort(movies);
        List<String> movieName = new ArrayList<>();
        for (Movie movie : movies) {
            movieName.add(movie.title);
        }
        return movieName;
    }

    class Movie implements Comparable{
        String title;
        int year;
        List<String> directors;
        List<String> actors;

        public Movie(String title, int year, List<String> directors, List<String> actors) {
            this.title = title;
            this.year = year;
            this.actors = actors;
            this.directors = directors;
        }

        @Override
        public int compareTo(Object o) {
            Movie other = (Movie)o;
            if (this.title.equals(other.title) && this.year == other.year) {
                return 0;
            } else if (this.year > other.year)
                return -1;
            return 1;
        }
    }


}
