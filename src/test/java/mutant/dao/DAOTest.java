package mutant.dao;

import com.google.cloud.datastore.Entity;
import mutant.MutantApplication;
import mutant.domain.Sequence;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MutantApplication.class}, loader = AnnotationConfigContextLoader.class)
public class DAOTest {

    @Autowired
    private SequenceDAO sequenceDAO;

    @Autowired
    private StatisticsDAO statisticsDAO;

    private static final String[] DNA = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};

    @Test
    public void testSaveSequence() {
        Sequence sequence = new Sequence();
        sequence.setDna(DNA);
        sequence.setMutant(true);
        Long id = sequenceDAO.save(sequence);
        Assert.assertTrue(id != null);
        testFindByDNA();
        testFindStatistics();
    }

    private void testFindStatistics() {
        Entity entity = statisticsDAO.findStatistics(null);
        Assert.assertTrue(entity.getLong(StatisticsDAO.TOTAL) > 0);
    }

    private void testFindByDNA() {
        Sequence sequence = sequenceDAO.findByDna(DNA);
        Assert.assertTrue(sequence != null);
        Assert.assertTrue(sequence.getId() != null);
    }

    @Test
    public void testFindByNotExistingDNA() {
        String [] dna = {"ATGCGA","CAGTGV","TTATGC"};
        Sequence sequence = sequenceDAO.findByDna(dna);
        Assert.assertTrue(sequence == null);
    }
}
