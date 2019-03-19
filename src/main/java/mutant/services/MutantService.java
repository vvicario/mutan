package mutant.services;

import mutant.domain.Sequence;
import org.springframework.stereotype.Component;
import java.util.concurrent.CompletableFuture;

/**
 * @author vvicario
 */
@Component
public interface MutantService {

    /**
     * Check if the specified sequence is mutant
     * @param sequence array of sequence values
     *
     */
    CompletableFuture<Boolean> isMutant(Sequence sequence) throws InterruptedException;

    /**
     * Save the specified sequence
     */
    Sequence save(Sequence sequence);

    /**
     * Find existing sequence that match with the specified dna
     * @param sequence with dna to be matched
     * @return existent sequence
     */
    Sequence findByDna(Sequence sequence);
}
