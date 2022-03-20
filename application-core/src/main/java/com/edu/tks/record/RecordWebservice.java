package com.edu.tks.record;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.edu.tks.exception.InputException;
import com.edu.tks.exception.NotFoundException;
import com.edu.tks.exception.RentalException;
import com.edu.tks.rental.Rental;

import java.text.ParseException;
import java.util.List;


@RestController
@RequestMapping("records")
public class RecordWebservice {

    private final RecordService recordManager;

    @Autowired
    public RecordWebservice(RecordService recordManager) {
        this.recordManager = recordManager;
    }

    @GetMapping
    public List<Record> getRecords() {
        return recordManager.getAllRecords();
    }

    @GetMapping("/{recordID}")
    public Record getRecordByID(@PathVariable(required = false) String recordID) throws NotFoundException {
        return recordManager.getRecordByID(recordID);
    }

    @GetMapping("/{recordID}/currentRent")
    public Rental getCurrentRent(@PathVariable(required = true) String recordID) throws NotFoundException {
        Record record = recordManager.getRecordByID(recordID);
        return record.getCurrentRent();
    }

    @GetMapping("/{recordID}/archiveRents")
    public List<Rental> getArchiveRents(@PathVariable(required = true) String recordID) throws NotFoundException {
        Record record = recordManager.getRecordByID(recordID);
        return record.getArchiveRents();
    }

    @PostMapping
    @ResponseStatus(value= HttpStatus.CREATED)
    public Record addRecord(@RequestBody String body) throws InputException {
        JsonObject jsonBody = JsonParser.parseString(body).getAsJsonObject();

        String title = jsonBody.get("title").getAsString();
        if (!title.matches("^[a-zA-Z0-9_ -]{3,50}$")) {
            throw new InputException("Title must be between 3 and 50 characters");
        }

        String artist = jsonBody.get("artist").getAsString();
        if (!artist.matches("^[a-zA-Z0-9_ -]{3,50}$")) {
            throw new InputException("Artist name must be between 3 and 50 characters");
        }

        String releaseDate = jsonBody.get("releaseDate").getAsString();

        Record record = new Record(title, artist, releaseDate);

        recordManager.appendRecord(record);
        return record;
    }

    @DeleteMapping("/{recordID}")
    public Record removeRecord(@PathVariable(required = true) String recordID) throws NotFoundException, RentalException {
        Record record = recordManager.getRecordByID(recordID);
        recordManager.removeRecord(recordID);
        return record;
    }

    @PostMapping("/{recordID}/edit")
    public Record modifyRecord(@PathVariable(required = true) String recordID, @RequestBody String body)
            throws InputException, NotFoundException {

        try {
            JsonObject jsonBody = JsonParser.parseString(body).getAsJsonObject();
            if (!recordID.matches("\\b[0-9a-f]{8}\\b-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-\\b[0-9a-f]{12}\\b")) {
                throw new InputException("Wrong uuid format");
            }

            Record record = recordManager.getRecordByID(recordID);

            String title = jsonBody.get("title").getAsString();
            if (title.length() != 0 && !title.matches("^[a-zA-Z0-9_ -]{3,50}$")) {
                throw new InputException("Title must be between 3 and 50 characters");
            }


            String artist = jsonBody.get("artist").getAsString();
            if (artist.length() != 0 && !artist.matches("^[a-zA-Z0-9_ -]{3,50}$")) {
                throw new InputException("Artist name must be between 3 and 50 characters");
            }

            String releaseDate = jsonBody.get("releaseDate").getAsString();

            recordManager.modifyRecord(record, title, artist, releaseDate);
            return record;
        } catch (ParseException e) {
            throw new InputException("Wrong date format");
        } catch (NullPointerException e) {
            throw new InputException("Not every field was provided");
        }

    }
}
