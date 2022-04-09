package model.record;

import model.exceptions.SOAPInputException;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.LocalDate;
import java.util.UUID;

public class RecordSOAPEntity {
    private UUID recordID;
    private boolean isRented;
    private String title;
    private String artist;
    private LocalDate releaseDate;

    public RecordSOAPEntity(UUID recordID, String title, String artist, String releaseDate, boolean isRented) {
        this.isRented = isRented;
        this.recordID = recordID;
        this.title = title;
        this.artist = artist;
        this.releaseDate = LocalDate.parse(releaseDate);
    }

    public UUID getRecordID() {
        return recordID;
    }

    public boolean isRented() {
        return isRented;
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

    public void setRented(boolean rented) throws SOAPInputException {
        if (this.isRented && rented) {
            throw new SOAPInputException("This record is already rented");
        }
        if (!this.isRented && !rented) {
            throw new SOAPInputException("This record is not rented");
        }
        isRented = rented;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        RecordSOAPEntity recordEntity = (RecordSOAPEntity) o;

        return new EqualsBuilder()
                .append(recordID, recordEntity.recordID)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(recordID)
                .toHashCode();
    }
}
