package com.edu.tks;

import com.edu.tks.rest.model.RecordView;
import com.edu.tks.rest.RestServiceApplication;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,classes = RestServiceApplication.class)
class RecordWebserviceTest {


    private final RecordView[] INIT_DATA = {
            new RecordView(UUID.fromString("02cf35bf-d025-440b-a6ec-17cc6c77b021"), "Moral Power", "Nothing but Lorde", "2020-03-04", false),
            new RecordView(UUID.fromString("3e3719e5-8689-4e65-883f-4cd06cae7195"), "Solar Panic", "Thieves", "2021-10-13", false)
    };

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate rest;

    private String BASE_PATH;

    @PostConstruct
    private void setBasePath() {
        BASE_PATH = "http://localhost:" + port + "/";
    }

    @Test
    public void getRecordsTest() {
        ParameterizedTypeReference<List<RecordView>> typeRef = new ParameterizedTypeReference<>() {
        };
        ResponseEntity<List<RecordView>> response = rest.exchange(BASE_PATH + "/records", HttpMethod.GET, null, typeRef);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(INIT_DATA[0], response.getBody().get(0));
        assertEquals(INIT_DATA[1], response.getBody().get(1));
    }

    @Test
    void getRecordByID() {
        RecordView response = rest.getForObject(BASE_PATH + "/records/" + INIT_DATA[0].getRecordID().toString(), RecordView.class);
        assertEquals(INIT_DATA[0], response);
    }

    @Test
    void getNonExistingRecordByID() {
        String nonExistingUUID = "37575e25-2929-415d-a7d9-254e96250970";
        ResponseEntity<RecordView> response = rest.getForEntity(BASE_PATH + "/records/" + nonExistingUUID, RecordView.class);
        assertEquals(404, response.getStatusCode().value());
        assertNull(response.getBody().getRecordID());
    }

    @Test
    void addAndRemoveRecord() {
        String title = "title";
        String artist = "artist";
        String releaseDate = "2020-01-01";
        RecordView newRecord = new RecordView(title, artist, releaseDate);

        ResponseEntity<RecordView> response = rest.postForEntity(BASE_PATH + "/records", newRecord, RecordView.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(title, response.getBody().getTitle());
        assertEquals(artist, response.getBody().getArtist());
        assertEquals(LocalDate.parse(releaseDate), response.getBody().getReleaseDate());
        assertNotNull(response.getBody().getRecordID());

        ParameterizedTypeReference<List<RecordView>> typeRef = new ParameterizedTypeReference<>() {
        };
        ResponseEntity<List<RecordView>> withAdded = rest.exchange(BASE_PATH + "/records", HttpMethod.GET, null, typeRef);
        assertTrue(withAdded.getStatusCode().is2xxSuccessful());
        assertEquals(3, withAdded.getBody().size());

        RecordView rec = withAdded.getBody().stream().filter(r -> r.getTitle().equals(title)).findFirst().orElseThrow();
        response = rest.exchange(BASE_PATH + "/records/" + rec.getRecordID().toString(), HttpMethod.DELETE, null, RecordView.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());

        ResponseEntity<List<RecordView>> all = rest.exchange(BASE_PATH + "/records", HttpMethod.GET, null, typeRef);
        assertTrue(all.getStatusCode().is2xxSuccessful());
        assertEquals(2, all.getBody().size());
    }

    @Test
    void modifyRecord() {
        String newTitle = "newTitle";
        RecordView r = new RecordView(INIT_DATA[0].getRecordID(), newTitle, INIT_DATA[0].getArtist(), INIT_DATA[0].getReleaseDate().toString(), INIT_DATA[0].isRented());
        HttpEntity<RecordView> ent = new HttpEntity<>(r);
        ResponseEntity<RecordView> response = rest.exchange(BASE_PATH + "/records/" + INIT_DATA[0].getRecordID().toString(), HttpMethod.PUT, ent, RecordView.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        RecordView responseRecord = rest.getForObject(BASE_PATH + "/records/" + INIT_DATA[0].getRecordID().toString(), RecordView.class);
        assertEquals(r, responseRecord);
        r.setTitle(INIT_DATA[0].getTitle());
        response = rest.exchange(BASE_PATH + "/records/" + INIT_DATA[0].getRecordID().toString(), HttpMethod.PUT, ent, RecordView.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }
}