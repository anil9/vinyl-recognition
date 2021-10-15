package com.nilsson.vinylrecordsales.domain;

import java.time.Year;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class RecordInformation {
    private final String title;
    private final List<String> genre;
    private final List<String> style;
    private final Year year;
    private final Map<String, String> tracklist;

    private RecordInformation(String title, List<String> genre, List<String> style, Year year, Map<String, String> tracklist) {
        this.title = Objects.requireNonNull(title, "title");
        this.genre = Objects.requireNonNull(genre, "genre");
        this.style = Objects.requireNonNull(style, "style");
        this.tracklist = Objects.requireNonNull(tracklist, "tracklist");
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getGenre() {
        return genre;
    }

    public List<String> getStyle() {
        return style;
    }

    public Optional<Year> getYear() {
        return Optional.ofNullable(year);
    }

    public Map<String, String> getTracklist() {
        return tracklist;
    }

    public static RecordInformationBuilder builder() {
        return new RecordInformationBuilder();
    }

    public static final class RecordInformationBuilder {
        private String title;
        private List<String> genre;
        private List<String> style;
        private Year year;
        private Map<String, String> tracklist;

        private RecordInformationBuilder() {
        }



        public RecordInformationBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        public RecordInformationBuilder withGenre(List<String> genre) {
            this.genre = genre;
            return this;
        }

        public RecordInformationBuilder withStyle(List<String> style) {
            this.style = style;
            return this;
        }

        public RecordInformationBuilder withYear(Year year) {
            this.year = year;
            return this;
        }

        public RecordInformationBuilder withTracklist(Map<String, String> tracklist) {
            this.tracklist = tracklist;
            return this;
        }

        public RecordInformation build() {
            return new RecordInformation(title, genre, style, year, tracklist);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecordInformation that = (RecordInformation) o;
        return Objects.equals(title, that.title) && Objects.equals(genre, that.genre) && Objects.equals(style, that.style) && Objects.equals(year, that.year) && Objects.equals(tracklist, that.tracklist);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, genre, style, year, tracklist);
    }

    @Override
    public String toString() {
        return "RecordInformation{" +
                "title='" + title + '\'' +
                ", genre=" + genre +
                ", style=" + style +
                ", year=" + year +
                ", tracklist=" + tracklist +
                '}';
    }
}
