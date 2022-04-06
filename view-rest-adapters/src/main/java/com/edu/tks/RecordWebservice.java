package com.edu.tks;

import com.edu.tks.exception.InputException;
import com.edu.tks.exception.NotFoundException;
import com.edu.tks.exception.RentalException;
import com.edu.tks.record.AddRecordUseCase;
import com.edu.tks.record.GetRecordsUseCase;
import com.edu.tks.record.Record;
import com.edu.tks.record.RemoveRecordUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("records")
public class RecordWebservice {

    private final AddRecordUseCase addRecordUseCase;
    private final GetRecordsUseCase getRecordsUseCase;
    private final RemoveRecordUseCase removeRecordUseCase;

    @Autowired
    public RecordWebservice(AddRecordUseCase addRecordUseCase, GetRecordsUseCase getRecordsUseCase, RemoveRecordUseCase removeRecordUseCase) {
        this.addRecordUseCase = addRecordUseCase;
        this.getRecordsUseCase = getRecordsUseCase;
        this.removeRecordUseCase = removeRecordUseCase;
    }

    @GetMapping
    public List<Record> getRecords() {
        return getRecordsUseCase.getAllRecords();
    }

    @GetMapping("/{recordID}")
    public Record getRecordByID(@PathVariable(required = false) String recordID) throws NotFoundException {
        return getRecordsUseCase.getRecordByID(recordID);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Record addRecord(@RequestBody Record body) throws InputException {

        String title = body.getTitle();
        if (!title.matches("^[a-zA-Z0-9_ -]{3,50}$")) {
            throw new InputException("Title must be between 3 and 50 characters");
        }

        String artist = body.getArtist();
        if (!artist.matches("^[a-zA-Z0-9_ -]{3,50}$")) {
            throw new InputException("Artist name must be between 3 and 50 characters");
        }

        String releaseDate = body.getReleaseDate().toString();

        Record record = new Record(title, artist, releaseDate);

        addRecordUseCase.appendRecord(record);
        return record;
    }

    @DeleteMapping("/{recordID}")
    public Record removeRecord(@PathVariable(required = true) String recordID) throws NotFoundException, RentalException {
        Record record = getRecordsUseCase.getRecordByID(recordID);
        removeRecordUseCase.removeRecord(recordID);
        return record;
    }

    @PutMapping("/{recordID}")
    public Record modifyRecord(@PathVariable(required = true) String recordID, @RequestBody Record body)
            throws InputException, NotFoundException {

        try {
            if (!recordID.matches("\\b[0-9a-f]{8}\\b-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-\\b[0-9a-f]{12}\\b")) {
                throw new InputException("Wrong uuid format");
            }

            String title = body.getTitle();
            if (title.length() != 0 && !title.matches("^[a-zA-Z0-9_ -]{3,50}$")) {
                throw new InputException("Title must be between 3 and 50 characters");
            }


            String artist = body.getArtist();
            if (artist.length() != 0 && !artist.matches("^[a-zA-Z0-9_ -]{3,50}$")) {
                throw new InputException("Artist name must be between 3 and 50 characters");
            }

            String releaseDate = body.getReleaseDate().toString();
            return addRecordUseCase.updateRecord(recordID, body);

        } catch (ParseException e) {
            throw new InputException("Wrong date format");
        } catch (NullPointerException e) {
            throw new InputException("Not every field was provided");
        }

    }
}
