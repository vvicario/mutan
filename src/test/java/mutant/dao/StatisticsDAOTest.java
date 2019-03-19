package mutant.dao;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.FullEntity;
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
public class StatisticsDAOTest {


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
        statisticsDAO = new StatisticsDAO();
        statisticsDAO.setDatastore(datastore);
    }

    @Test
    public void testSave() {
        Sequence sequence = new Sequence();
        sequence.setDna(DNA);
        sequence.setMutant(true);
        Mockito.when(datastore.newTransaction()).thenReturn(transaction);
        statisticsDAO.save(true);
        Mockito.verify(transaction, Mockito.times(1)).run(Mockito.any());
        Mockito.verify(transaction, Mockito.times(0)).update(Mockito.any());
    }

    @Test
    public void testUpdate() {
        statisticsDAO.update(true, transaction, 15l, 2l, 2l);
        Mockito.verify(transaction, Mockito.times(1)).update(Mockito.any());
        Mockito.verify(transaction, Mockito.times(1)).commit();
    }

}
