package mutant.dao;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.Transaction;
import mutant.domain.Sequence;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SequenceDAOTest {


    private SequenceDAO sequenceDAO;
    @Mock
    private StatisticsDAO statisticsDAO;
    @Mock
    private Datastore datastore;
    @Mock
    private Transaction transaction;

    private KeyFactory sequenceKeyFactory;

    private Key key;

    private static final String[] DNA = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        sequenceKeyFactory = new KeyFactory("test-01");
        sequenceDAO = new SequenceDAO();
        sequenceDAO.setDatastore(datastore);
        sequenceDAO.setStatisticDAO(statisticsDAO);
    }

    @Test
    public void testSaveSequence() {
        Sequence sequence = new Sequence();
        sequence.setDna(DNA);
        sequence.setMutant(true);
        Mockito.when(datastore.newTransaction()).thenReturn(transaction);
        sequenceDAO.save(sequence);
        Mockito.verify(statisticsDAO, Mockito.times(1)).save(true);
    }

    @Test
    public void testFindByDNA() {
        Sequence sequence = sequenceDAO.findByDna(DNA);
        Mockito.verify(datastore, Mockito.times(1)).run(Mockito.any());
    }
}
