package mutant.dao;


import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.IncompleteKey;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.StructuredQuery;
import com.google.cloud.datastore.Transaction;
import mutant.domain.Sequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author vvicario
 * Interact with Cloud Datastore database to handle sequence data
 */
@Component
public class SequenceDAO {

    @Autowired
    private StatisticsDAO statisticDAO;
    private Datastore datastore;
    private KeyFactory sequenceKeyFactory;
    private static final String DNA = "dna";
    private static final String MUTANT = "mutant";
    private static final String SEQUENCE = "Sequence";

    public SequenceDAO() {
        datastore = DatastoreOptions.getDefaultInstance().getService();
        sequenceKeyFactory = datastore.newKeyFactory().setKind(SEQUENCE);
    }

    /**
     * Save a new sequence
     * @param sequence information to be saved with dna sequence and a boolean value that represents if it's mutant
     * @return the new id
     */
    public Long save(Sequence sequence) {
        Transaction txn = datastore.newTransaction();
        try {
            IncompleteKey key = sequenceKeyFactory.newKey();
            FullEntity<IncompleteKey> entity = Entity.newBuilder(key)
                    .set(DNA, String.join(",", sequence.getDna()))
                    .set(MUTANT, sequence.getMutant())
                    .build();
            Entity sequenceEntity = txn.add(entity);
            statisticDAO.save(sequence.getMutant());
            txn.commit();
            return sequenceEntity.getKey().getId();
        } finally {
            if (txn.isActive()) {
                txn.rollback();
            }
        }
    }

    /**
     * Find sequence by DNA
     *
     * @param dna sequence of values
     * @return the existent sequence that match with the specified dna
     */
    public Sequence findByDna(String[] dna) {
        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind(SEQUENCE)
                .setFilter(StructuredQuery.PropertyFilter.eq(DNA, String.join(",", dna)))
                .build();

        QueryResults<Entity> results = datastore.run(query);
        return results.hasNext() ? getSequence(results.next()) : null;
    }

    private Sequence getSequence(Entity sequenceEntity) {
        return new Sequence(sequenceEntity.getKey().getId(), sequenceEntity.getBoolean(MUTANT));
    }
}
