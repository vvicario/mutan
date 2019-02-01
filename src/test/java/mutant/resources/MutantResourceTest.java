package mutant.resources;

import mutant.domain.Sequence;
import mutant.dto.Statistic;
import mutant.services.MutantService;
import mutant.services.StatisticsService;
import mutant.validators.SequenceValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import mutant.MutantApplication;

import javax.xml.ws.Response;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;

/**
 * @author vvicario
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MutantApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MutantResourceTest {

    @Mock
    private MutantService mutantService;

    @Mock
    private SequenceValidator validator;

    @Mock
    private StatisticsService statisticsService;

    private MutantResource resource;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        resource = new MutantResource(validator, statisticsService, mutantService);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsMutantWithNullDNAParameters() throws Exception {
        Sequence sequence = new Sequence();
        Mockito.doThrow(new IllegalArgumentException()).when(validator).validate(sequence);
        resource.create(sequence);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsMutantWithInvalidSizeParameters() throws Exception {
        String[] dnaList = {"ATGCGA", "CAGTGC", "TTATGT"};
        Sequence sequence = new Sequence();
        sequence.setDna(dnaList);
        Mockito.doThrow(new IllegalArgumentException()).when(validator).validate(sequence);
        resource.create(sequence);
    }

    @Test
    public void testIsMutantTrue() throws Exception {
        String[] dnaList = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
        Sequence sequence = new Sequence();
        sequence.setDna(dnaList);
        Mockito.when(mutantService.save(sequence)).thenReturn(sequence);
        Mockito.when(mutantService.isMutant(sequence)).thenReturn(CompletableFuture.completedFuture(true));
        ResponseEntity responseEntity = resource.create(sequence);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testIsMutantFalse() throws Exception {
        String[] dnaList = {"TTGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
        Sequence sequence = new Sequence();
        sequence.setDna(dnaList);
        Mockito.when(mutantService.save(sequence)).thenReturn(sequence);
        Mockito.when(mutantService.isMutant(sequence)).thenReturn(CompletableFuture.completedFuture(false));
        ResponseEntity responseEntity = resource.create(sequence);
        Assert.assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }

    @Test
    public void testStatistics() throws Exception {
        Statistic statistic = new Statistic(BigDecimal.valueOf(40), BigDecimal.valueOf(100), 0.4);
        Mockito.when(statisticsService.getStatistics()).thenReturn(CompletableFuture.completedFuture(statistic));
        ResponseEntity responseEntity = resource.findStatistics();
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Statistic result = (Statistic) responseEntity.getBody();
        Assert.assertEquals(40, result.getCountMutantDNA().intValue());
        Assert.assertEquals(100, result.getCountHumanDNA().intValue());
    }


}
