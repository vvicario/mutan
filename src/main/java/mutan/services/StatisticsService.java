package mutan.services;

import mutan.dto.Statistic;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public interface StatisticsService {

    CompletableFuture<Statistic> getStatistics();

}
