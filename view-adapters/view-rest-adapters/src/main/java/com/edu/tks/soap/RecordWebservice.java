package com.edu.tks.soap;

import com.edu.tks.exception.SOAPInputException;
import com.edu.tks.exception.SOAPNotFoundException;
import com.edu.tks.exception.SOAPRentalException;
import com.edu.tks.model.RecordView;
import com.edu.tks.ports.view.service.record.AddRecordUseCase;
import com.edu.tks.ports.view.service.record.GetRecordsUseCase;
import com.edu.tks.ports.view.service.record.RemoveRecordUseCase;
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
    public List<RecordView> getRecords() {
        return getRecordsUseCase.getAllRecords();
    }

    @GetMapping("/{recordID}")
    public RecordView getRecordByID(@PathVariable(required = false) String recordID) throws SOAPNotFoundException {
        return getRecordsUseCase.getRecordByID(recordID);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public RecordView addRecord(@RequestBody RecordView body) throws SOAPInputException {

        String title = body.getTitle();
        if (!title.matches("^[a-zA-Z0-9_ -]{3,50}$")) {
            throw new SOAPInputException("Title must be between 3 and 50 characters");
        }

        String artist = body.getArtist();
        if (!artist.matches("^[a-zA-Z0-9_ -]{3,50}$")) {
            throw new SOAPInputException("Artist name must be between 3 and 50 characters");
        }

        String releaseDate = body.getReleaseDate().toString();

        RecordView record = new RecordView(title, artist, releaseDate);

        addRecordUseCase.appendRecord(record);
        return record;
    }

    @DeleteMapping("/{recordID}")
    public RecordView removeRecord(@PathVariable(required = true) String recordID) throws SOAPNotFoundException, SOAPRentalException {
        RecordView record = getRecordsUseCase.getRecordByID(recordID);
        removeRecordUseCase.removeRecord(recordID);
        return record;
    }

    @PutMapping("/{recordID}")
    public RecordView modifyRecord(@PathVariable(required = true) String recordID, @RequestBody RecordView body)
            throws SOAPInputException, SOAPNotFoundException {

        try {
            if (!recordID.matches("\\b[0-9a-f]{8}\\b-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-\\b[0-9a-f]{12}\\b")) {
                throw new SOAPInputException("Wrong uuid format");
            }

            String title = body.getTitle();
            if (title.length() != 0 && !title.matches("^[a-zA-Z0-9_ -]{3,50}$")) {
                throw new SOAPInputException("Title must be between 3 and 50 characters");
            }


            String artist = body.getArtist();
            if (artist.length() != 0 && !artist.matches("^[a-zA-Z0-9_ -]{3,50}$")) {
                throw new SOAPInputException("Artist name must be between 3 and 50 characters");
            }
            return addRecordUseCase.updateRecord(recordID, body);

        } catch (ParseException e) {
            throw new SOAPInputException("Wrong date format");
        } catch (NullPointerException e) {
            throw new SOAPInputException("Not every field was provided");
        }

    }
}
