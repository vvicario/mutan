package mutant.services;

import com.google.cloud.datastore.Entity;
import mutant.dao.StatisticsDAO;
import mutant.dto.Statistic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.concurrent.CompletableFuture;

/**
 * @author vvicario
 */
@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private StatisticsDAO statisticsDAO;

    public StatisticsServiceImpl(StatisticsDAO statisticsDAO) {
        this.statisticsDAO = statisticsDAO;
    }

    @Async
    @Override
    public CompletableFuture<Statistic> getStatistics() {
        Entity statistics = statisticsDAO.findStatistics(null);
        Long totalMutants = statistics != null ? statistics.getLong(StatisticsDAO.CANT_MUTANTS) : 0;
        Long totalSeq = statistics != null ? statistics.getLong(StatisticsDAO.TOTAL) : 0;
        long ratio = 0;
        if(totalMutants > 1 && totalSeq > 0) {
            ratio = totalMutants / totalSeq;
        }
        DecimalFormat df = new DecimalFormat("#.#");
        return CompletableFuture.completedFuture(new Statistic(BigDecimal.valueOf(totalMutants), BigDecimal.valueOf(totalSeq), Double.valueOf(df.format(ratio))));
    }
}
