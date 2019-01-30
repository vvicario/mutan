package mutan.resources;

import mutan.domain.Sequence;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import mutan.MutanApplication;

import java.time.LocalDate;

/**
 * @author vvicario
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MutanApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MutantResourceIT {


    @LocalServerPort
    private int port;

    private TestRestTemplate restTemplate = new TestRestTemplate();

    private HttpHeaders headers = new HttpHeaders();

    @Test
    public void testIsMutantWithNullDNAParameters() {
        Sequence sequence = new Sequence();
        HttpEntity<Sequence> entity = new HttpEntity<>(sequence, headers);
        ResponseEntity<String> badResponse = restTemplate.exchange(
                createURLWithPort("/mutant"),
                HttpMethod.POST, entity, String.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, badResponse.getStatusCode());
    }

    @Test
    public void testIsMutantWithInvalidSizeParameters() {
        String[] dnaList = {"ATGCGA", "CAGTGC", "TTATGT"};
        Sequence sequence = new Sequence();
        HttpEntity<Sequence> entity = new HttpEntity<>(sequence, headers);
        ResponseEntity<String> badResponse = restTemplate.exchange(
                createURLWithPort("/mutant"),
                HttpMethod.POST, entity, String.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, badResponse.getStatusCode());
    }

    @Test
    public void testIsMutantTrue() {
        String[] dnaList = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
        Sequence sequence = new Sequence();
        sequence.setDna(dnaList);
        HttpEntity<Sequence> entity = new HttpEntity<>(sequence, headers);
        ResponseEntity<String> badResponse = restTemplate.exchange(
                createURLWithPort("/mutant"),
                HttpMethod.POST, entity, String.class);
        Assert.assertEquals(HttpStatus.OK, badResponse.getStatusCode());
    }

    @Test
    public void testIsMutantFalse() {
        String[] dnaList = {"TTGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
        Sequence sequence = new Sequence();
        sequence.setDna(dnaList);
        HttpEntity<Sequence> entity = new HttpEntity<>(sequence, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/mutant"),
                HttpMethod.POST, entity, String.class);
        Assert.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}
