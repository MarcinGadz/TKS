package com.edu.tks.record;

import com.edu.tks.exception.InputException;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;

public class Record {
    private UUID recordID;
    private boolean isRented;

    private String title;
    private String artist;
    private LocalDate releaseDate;

    public Record(String title, String artist, String releaseDate) {
        this(UUID.randomUUID(), title, artist, releaseDate, false);
    }

    public Record(UUID recordID, String title, String artist, String releaseDate, boolean isRented) {
        this.isRented = isRented;
        this.recordID = recordID;
        this.title = title;
        this.artist = artist;
        this.releaseDate = LocalDate.parse(releaseDate);
    }

    public Record() {
    }

    public boolean isRented() {
        return isRented;
    }

    public UUID getRecordID() {
        return recordID;
    }

    public void setRecordID(String id) {
        this.recordID = UUID.fromString(id);
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void rent() throws InputException {
        if (this.isRented) {
            throw new InputException("shop.Record already rented");
        }
        this.isRented = true;
    }

    public void release() throws InputException {
        if (!this.isRented) {
            throw new InputException("This record is not rented");
        }
        this.isRented = false;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setReleaseDate(String releaseDate) throws ParseException {
        this.releaseDate = LocalDate.parse(releaseDate);
    }

    @Override
    public String toString() {
        return "Record{" +
                "recordID=" + recordID +
                ", isRented=" + isRented +
                ", title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", releaseDate=" + releaseDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Record record = (Record) o;

        return new EqualsBuilder()
                .append(recordID, record.recordID)
                .append(title, record.title)
                .append(artist, record.artist)
                .append(releaseDate, record.releaseDate)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(recordID)
                .toHashCode();
    }
}
