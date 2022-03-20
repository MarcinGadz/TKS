package shop.Record;

import shop.Exceptions.NotFoundException;
import shop.Exceptions.RentalException;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;


@Service
public class RecordManager {
    RecordRepository repository = new RecordRepository();

    public Record getRecordByID(String recordid) throws NotFoundException {
        return repository.getRecordByID(recordid);
    }

    public List<Record> getAllRecords() {
        return repository.getAllRecords();
    }

    public synchronized void appendRecord(Record record){
        repository.appendRecord(record);
    }

    public synchronized void removeRecord(String recordid) throws RentalException, NotFoundException {
        repository.removeRecord(recordid);
    }

    public synchronized void modifyRecord(Record record, String title, String artist, String releaseDate) throws ParseException {
        if (title.length() > 0) {
            record.setTitle(title);
        }

        if (artist.length() > 0) {
            record.setArtist(artist);
        }

        if (releaseDate.length() > 0) {
            record.setReleaseDate(releaseDate);
        }
    }

}
