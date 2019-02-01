package mutant.services;

import mutant.domain.Sequence;
import javassist.NotFoundException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author vvicario
 */
@Component
public interface MutantService {

    CompletableFuture<Boolean> isMutant(Sequence reservation) throws InterruptedException;

    Sequence save(Sequence sequence);

    Sequence update(Sequence sequence) throws NotFoundException;

}
