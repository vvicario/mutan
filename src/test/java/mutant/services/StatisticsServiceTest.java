package mutant.services;

import mutant.MutantApplication;
import mutant.dao.StatisticsDAO;
import mutant.dto.Statistic;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MutantApplication.class}, loader = AnnotationConfigContextLoader.class)
public class StatisticsServiceTest {

    @Mock
    private StatisticsDAO statisticsDAO;

    private StatisticsServiceImpl service;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        service = new StatisticsServiceImpl(statisticsDAO);
    }

    @Test
    public void getEmptyStatistics() throws Exception {
        Mockito.when(statisticsDAO.findStatistics(null)).thenReturn(null);
        CompletableFuture<Statistic> statisticCompletableFuture = service.getStatistics();
        Statistic statistic = statisticCompletableFuture.get();
        Assert.assertEquals(statistic.getCountHumanDNA(), BigDecimal.valueOf(0));
        Assert.assertEquals(statistic.getCountMutantDNA(), BigDecimal.valueOf(0));
    }
}
