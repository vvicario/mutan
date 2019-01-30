package mutan.services;

import mutan.domain.Sequence;
import mutan.dto.StatsDTO;
import mutan.repository.SequenceRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    private SequenceRepository sequenceRepository;

    @Async
    @Override
    public CompletableFuture<Sequence> getStatistics() {
        List<Sequence> mutantTotal = sequenceRepository.findByMutantTrue();
        Long totalSeq = sequenceRepository.count();
        long ratio = 0;
        if(!CollectionUtils.isEmpty(mutantTotal)) {
            ratio = mutantTotal.size() / totalSeq;
        }
        DecimalFormat df = new DecimalFormat("#.#");
        new StatsDTO(BigDecimal.valueOf(mutantTotal.size()), BigDecimal.valueOf(totalSeq), Double.valueOf(df.format(ratio)));
        return null;
    }
}
