package mutant.services;

import mutant.domain.Sequence;
import mutant.dto.Statistic;
import mutant.repository.SequenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private SequenceRepository sequenceRepository;

    @Async
    @Override
    public CompletableFuture<Statistic> getStatistics() {
        List<Sequence> mutantTotal = sequenceRepository.findByMutantTrue();
        Long totalSeq = sequenceRepository.count();
        long ratio = 0;
        if(!CollectionUtils.isEmpty(mutantTotal)) {
            ratio = mutantTotal.size() / totalSeq;
        }
        DecimalFormat df = new DecimalFormat("#.#");
        return CompletableFuture.completedFuture(new Statistic(BigDecimal.valueOf(mutantTotal.size()), BigDecimal.valueOf(totalSeq), Double.valueOf(df.format(ratio))));
    }
}
