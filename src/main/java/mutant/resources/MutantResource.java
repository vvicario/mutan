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

    @PostMapping(path = "/mutant")
    public ResponseEntity create(@RequestBody @Valid Sequence sequence) throws Exception {
        validator.validate(sequence);
        sequence = mutantService.save(sequence);
        if(sequence.getMutant() == null) {
            CompletableFuture<Boolean> isMutantFuture = mutantService.isMutant(sequence);
            Boolean isMutant = isMutantFuture.get();
            sequence.setMutant(isMutant);
            mutantService.update(sequence);
        }
        if (sequence.getMutant()) {
           return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(Response.SC_FORBIDDEN).build();
        }
    }

    @GetMapping(path = "/stats")
    public ResponseEntity<Statistic> findStatistics() throws ExecutionException, InterruptedException {
       return ResponseEntity.ok().body(statisticsService.getStatistics().get());
    }

}
