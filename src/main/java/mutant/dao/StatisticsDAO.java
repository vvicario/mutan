package mutant.dao;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.IncompleteKey;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.Transaction;
import org.springframework.stereotype.Component;

/**
 * @author vvicario
 * Interact with Cloud Datastore database to handle statistics data
 */
@Component
public class StatisticsDAO {

    private Datastore datastore;
    private KeyFactory keyFactory;
    public static final String TOTAL = "total";
    public static final String CANT_MUTANTS = "cantMutants";
    private static final String STATISTICS = "Statistics";

    public StatisticsDAO() {
        this.datastore = DatastoreOptions.getDefaultInstance().getService();
        this.keyFactory = datastore.newKeyFactory().setKind(STATISTICS);
    }

    /**
     * Save statistics, only one row with all the information
     * total records
     * total mutant records
     *
     * @param isMutant is a boolean value to include in statistics
     */
    public void save(Boolean isMutant) {
        Transaction txn = datastore.newTransaction();
        try {
            Entity statistics = findStatistics(txn);
            if (statistics == null) {
                saveFirstRecord(isMutant, txn);
            } else {
                update(isMutant, txn, statistics);
            }
        } finally {
            if (txn.isActive()) {
                txn.rollback();
            }
        }
    }

    /**
     * Update existent statistics record
     *
     * @param isMutant   is a boolean value to include in statistics
     * @param txn        current transaction
     * @param statistics existent information
     */
    private void update(Boolean isMutant, Transaction txn, Entity statistics) {
        Key key = keyFactory.newKey(statistics.getKey().getId());
        Entity entity = Entity.newBuilder(key)
                .set(TOTAL, statistics.getLong(TOTAL) + 1)
                .set(CANT_MUTANTS, isMutant ? statistics.getLong(CANT_MUTANTS) + 1 : statistics.getLong(CANT_MUTANTS))
                .build();
        txn.update(entity);
        txn.commit();
    }

    /**
     * Save first statistics record
     *
     * @param isMutant boolean value that represents if the sample is mutant
     * @param txn      current transaction
     */
    private void saveFirstRecord(Boolean isMutant, Transaction txn) {
        IncompleteKey key = keyFactory.newKey();
        FullEntity<IncompleteKey> entity = Entity.newBuilder(key)
                .set(TOTAL, 1)
                .set(CANT_MUTANTS, isMutant ? 1 : 0)
                .build();
        txn.add(entity);
        txn.commit();
    }

    /**
     * Find existent statistics information
     *
     * @param txn current transaction
     */
    public Entity findStatistics(Transaction txn) {
        txn = txn == null ? datastore.newTransaction() : txn;
        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind(STATISTICS)
                .build();
        QueryResults<Entity> results = txn.run(query);
        return results != null && results.hasNext() ? results.next() : null;
    }

    public void setDatastore(Datastore datastore) {
        this.datastore = datastore;
    }
}
