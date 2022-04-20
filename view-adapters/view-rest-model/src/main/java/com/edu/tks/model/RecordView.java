package com.edu.tks.model;

import com.edu.tks.exception.InputExceptionView;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.UUID;

public class RecordView {
    private UUID recordID;
    private boolean isRented;

    private String title;
    private String artist;
    private LocalDate releaseDate;

    public RecordView(String title, String artist, String releaseDate) {
        this(UUID.randomUUID(), title, artist, releaseDate, false);
    }

    public RecordView(UUID recordID, String title, String artist, String releaseDate, boolean isRented) {
        this.isRented = isRented;
        this.recordID = recordID;
        this.title = title;
        this.artist = artist;
        this.releaseDate = LocalDate.parse(releaseDate);
    }

    public RecordView() {
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

    public void rent() throws InputExceptionView {
        if (this.isRented) {
            throw new InputExceptionView("shop.Record already rented");
        }
        this.isRented = true;
    }

    public void release() throws InputExceptionView {
        if (!this.isRented) {
            throw new InputExceptionView("This record is not rented");
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

        RecordView record = (RecordView) o;

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
