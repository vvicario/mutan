package mutant.resources;

import mutant.domain.Sequence;
import mutant.dto.Statistic;
import mutant.services.StatisticsService;
import org.apache.catalina.connector.Response;
import mutant.services.MutantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import mutant.validators.SequenceValidator;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author vvicario
 */
@RestController
@RequestMapping
public class MutantResource {

    @Autowired
    private MutantService mutantService;

    @Autowired
    private StatisticsService statisticsService;

    @Autowired
    private SequenceValidator validator;

    public MutantResource(SequenceValidator validator, StatisticsService statisticsService, MutantService mutantService) {
      this.validator = validator;
      this.statisticsService = statisticsService;
      this.mutantService = mutantService;
    }

    /**
     * Return 200 OK if the specified sequence is mutant otherwise 403
     * @param sequence array of sequence
     * @return Return 200 OK if the specified sequence is mutant, 400 BAD REQUEST if
     * sequence is invalid or 403 FORBIDDEN if it is not mutant
     * @throws Exception
     */
    @PostMapping(path = "/mutant")
    public ResponseEntity create(@RequestBody @Valid Sequence sequence) throws Exception {
        validator.validate(sequence);
        Sequence existentSequence = mutantService.findByDna(sequence);
        if(existentSequence == null) {
            CompletableFuture<Boolean> isMutantFuture = mutantService.isMutant(sequence);
            Boolean isMutant = isMutantFuture.get();
            sequence.setMutant(isMutant);
            mutantService.save(sequence);
        }
        if (existentSequence != null && existentSequence.getMutant() || sequence.getMutant()) {
           return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(Response.SC_FORBIDDEN).build();
        }
    }

    /**
     *
     * @return Statistics information with among of mutants and total count human dna
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @GetMapping(path = "/stats")
    public ResponseEntity<Statistic> findStatistics() throws ExecutionException, InterruptedException {
       return ResponseEntity.ok().body(statisticsService.getStatistics().get());
    }

}
