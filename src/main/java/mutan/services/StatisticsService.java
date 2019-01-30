package mutan.services;

import mutan.domain.Sequence;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public interface StatisticsService {

    CompletableFuture<Sequence> getStatistics();

}
