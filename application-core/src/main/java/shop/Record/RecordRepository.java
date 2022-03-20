package shop.Record;

import shop.Exceptions.NotFoundException;
import shop.Exceptions.RentalException;
import shop.User.User;
import shop.User.UserType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecordRepository {
    private List<Record> records;

    public RecordRepository() {
        Record[] arr = {
          new Record("Moral Power", "Nothing but Lorde", "2020-03-04"),
          new Record("Solar Panic", "Thieves", "2021-10-13")
        };
        this.records  =  new ArrayList<>(Arrays.asList(arr));
    }

    public List<Record> getAllRecords() {
        return records;
    }

    public Record getRecordByID(String recordID) throws NotFoundException {
        Record record = records.stream()
                .filter( r -> recordID.equals(r.getRecordID().toString()))
                .findFirst()
                .orElse(null);
        if (record == null) {
            throw new NotFoundException("Record not found");
        }
        return record;
    }


    public void appendRecord(Record record) {
        records.add(record);
    }

    public void removeRecord(String recordID) throws RentalException, NotFoundException {
        Record record = this.getRecordByID(recordID);

        if (record.isRented()) {
            throw new RentalException("Can't remove - this record is rented.");
        }

        records.remove(record);
    }
}
