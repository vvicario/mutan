package mutant.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.stereotype.Component;

/**
 * @author vvicario
 */
@Component
@JsonIgnoreProperties
public class Sequence {

    private Long id;

    private String[] dna;

    private Boolean mutant;

    public Sequence() {
    }

    public Sequence(Long id, Boolean mutant) {
        this.id = id;
        this.mutant = mutant;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String[] getDna() {
        return dna;
    }

    public void setDna(String[] dna) {
        this.dna = dna;
    }

    public Boolean getMutant() {
        return mutant;
    }

    public void setMutant(Boolean mutant) {
        this.mutant = mutant;
    }
}
