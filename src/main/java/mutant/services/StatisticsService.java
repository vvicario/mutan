package mutant.services;

import mutant.dto.Statistic;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * @author vvicario
 */
@Component
public interface StatisticsService {

    CompletableFuture<Statistic> getStatistics();

}
