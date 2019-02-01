package mutant.services;

import mutant.MutantApplication;
import mutant.dto.Statistic;
import mutant.repository.SequenceRepository;
import mutant.resources.MutantResource;
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
    private SequenceRepository sequenceRepository;

    private StatisticsServiceImpl service;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        service = new StatisticsServiceImpl(sequenceRepository);
    }

    @Test
    public void getEmptyStatistics() throws Exception {
        Mockito.when(sequenceRepository.findByMutantTrue()).thenReturn(null);
        Mockito.when(sequenceRepository.count()).thenReturn(0l);
        CompletableFuture<Statistic> statisticCompletableFuture = service.getStatistics();
        Statistic statistic = statisticCompletableFuture.get();
        Assert.assertEquals(statistic.getCountHumanDNA(), BigDecimal.valueOf(0));
        Assert.assertEquals(statistic.getCountMutantDNA(), BigDecimal.valueOf(0));
    }

}
