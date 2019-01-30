package mutan.repository;

import mutan.domain.Sequence;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author vvicario
 */
@Repository
public interface SequenceRepository extends CrudRepository<Sequence, Long> {

    Sequence findAllByDna(String[] dna);

    List<Sequence> findByMutantTrue();

    List<Sequence> findByMutantFalse();

}
