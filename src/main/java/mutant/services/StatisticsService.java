package mutant.services;

import mutant.dto.Statistic;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public interface StatisticsService {

    CompletableFuture<Statistic> getStatistics();

}
