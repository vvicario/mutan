package mutan.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class Statistic {

    @JsonProperty("count_mutant_dna")
    BigDecimal countMutantDNA;

    @JsonProperty("count_human_dna")
    BigDecimal countHumanDNA;

    Double ration;

    public Statistic(BigDecimal countMutantDNA, BigDecimal countHumanDNA, Double ration) {
        this.countMutantDNA = countMutantDNA;
        this.countHumanDNA = countHumanDNA;
        this.ration = ration;
    }

    public BigDecimal getCountMutantDNA() {
        return countMutantDNA;
    }

    public void setCountMutantDNA(BigDecimal countMutantDNA) {
        this.countMutantDNA = countMutantDNA;
    }

    public BigDecimal getCountHumanDNA() {
        return countHumanDNA;
    }

    public void setCountHumanDNA(BigDecimal countHumanDNA) {
        this.countHumanDNA = countHumanDNA;
    }

    public Double getRation() {
        return ration;
    }

    public void setRation(Double ration) {
        this.ration = ration;
    }
}
