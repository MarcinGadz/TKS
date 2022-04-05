package com.edu.tks;

import com.edu.tks.record.Record;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;


import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RecordWebserviceTest {


    private final Record[] INIT_DATA = {
            new Record(UUID.fromString("02cf35bf-d025-440b-a6ec-17cc6c77b021"), "Moral Power", "Nothing but Lorde", "2020-03-04", false),
            new Record(UUID.fromString("3e3719e5-8689-4e65-883f-4cd06cae7195"), "Solar Panic", "Thieves", "2021-10-13", false)
    };

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate rest;

    private String BASE_PATH;

    @PostConstruct
    private void setBasePath() {
        BASE_PATH = "http://localhost:" + port + "/record-shop";
    }

    @Test
    public void getRecordsTest() {
        ParameterizedTypeReference<List<Record>> typeRef = new ParameterizedTypeReference<>() {
        };
        ResponseEntity<List<Record>> response = rest.exchange(BASE_PATH + "/records", HttpMethod.GET, null, typeRef);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(2, response.getBody().size());
        assertEquals(INIT_DATA[0], response.getBody().get(0));
        assertEquals(INIT_DATA[1], response.getBody().get(1));
    }

    @Test
    void getRecordByID() {
        Record response = rest.getForObject(BASE_PATH + "/records/" + INIT_DATA[0].getRecordID().toString(), Record.class);
        assertEquals(INIT_DATA[0], response);
    }

    @Test
    void addAndRemoveRecord() {
        String title = "title";
        String artist = "artist";
        String releaseDate = "2020-01-01";
        Record newRecord = new Record(title, artist, releaseDate);
        ResponseEntity<Record> response = rest.postForEntity(BASE_PATH + "/records", newRecord, Record.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(title, response.getBody().getTitle());
        assertEquals(artist, response.getBody().getArtist());
        assertEquals(LocalDate.parse(releaseDate), response.getBody().getReleaseDate());
        assertNotNull(response.getBody().getRecordID());
        response = rest.exchange(BASE_PATH + "/records/" + response.getBody().getRecordID().toString(), HttpMethod.DELETE, null, Record.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());

        ParameterizedTypeReference<List<Record>> typeRef = new ParameterizedTypeReference<>() {
        };
        ResponseEntity<List<Record>> all = rest.exchange(BASE_PATH + "/records", HttpMethod.GET, null, typeRef);
        assertTrue(all.getStatusCode().is2xxSuccessful());
        assertEquals(2, all.getBody().size());
    }

    @Test
    void modifyRecord() {
        String newTitle = "newTitle";
        Record r = new Record(newTitle, INIT_DATA[0].getArtist(), INIT_DATA[0].getReleaseDate().toString());
        HttpEntity<Record> ent = new HttpEntity<>(r);
        ResponseEntity<Record> response = rest.exchange(BASE_PATH + "/records/" + INIT_DATA[0].getRecordID().toString(), HttpMethod.PUT, ent, Record.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        Record responseRecord = rest.getForObject(BASE_PATH + "/records/" + INIT_DATA[0].getRecordID().toString(), Record.class);
        assertEquals(INIT_DATA[0], responseRecord);
    }
}