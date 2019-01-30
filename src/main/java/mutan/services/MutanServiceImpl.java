package mutan.services;

import javassist.NotFoundException;
import mutan.domain.Sequence;
import mutan.repository.SequenceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

/**
 * @author vvicario
 */
@Service
public class MutanServiceImpl implements MutantService {

    @Autowired
    private SequenceRepository sequenceRepository;

    private final String ADN_BASE_A = "AAAA";
    private final String ADN_BASE_T = "TTTT";
    private final String ADN_BASE_G = "GGGG";

    private final Integer MIN_TOTAL_SEQUENCE = 2;
    private final Integer TOTAL_SIZE_SEQUENCE = 4;

    private static final Logger log = LoggerFactory.getLogger(MutanServiceImpl.class);


    @Transactional
    @Override
    public Sequence update(Sequence sequence) throws NotFoundException {
        return sequenceRepository.save(sequence);
    }

    @Transactional
    @Override
    public Sequence save(@Valid Sequence sequence) {
        Sequence existentSequence = sequenceRepository.findAllByDna(sequence.getDna());
        if (existentSequence == null) {
            sequence = sequenceRepository.save(sequence);
        }
        return existentSequence != null ? existentSequence : sequence;
    }

    @Async
    @Override
    public CompletableFuture<Boolean> isMutant(Sequence sequence) {
        char[][] dna = createArray(sequence.getDna());
        boolean isMutant = false;
        int total = 0;
        for (int i = 0; i < dna.length; i++) {
            if (total < MIN_TOTAL_SEQUENCE) {
                // validate columns if string is greater than 4
                if (i <= dna.length - TOTAL_SIZE_SEQUENCE) {
                    String row = String.valueOf(dna[i]);
                    // validate horizontal
                    if (isValidSequence(row)) {
                        total++;
                    }
                }
                if (total < MIN_TOTAL_SEQUENCE) {
                    // validate vertical
                    StringBuilder column = new StringBuilder();
                    for (int j = 0; j < dna.length; j++) {
                        column.append(dna[j][i]);
                    }
                    if (isValidSequence(column.toString())) {
                        total++;
                    }
                }
                // validate diagonal under x = y
                StringBuilder diagonal = new StringBuilder();
                for (int j = 0; j < dna.length; j++) {
                    if (i + j < dna.length) {
                        diagonal.append(dna[j][i + j]);
                    } else {
                        break;
                    }
                }
                if (isValidSequence(diagonal.toString())) {
                    total++;
                }

            } else {
                isMutant = true;
                break;
            }
        }
        // validate diagonal above x, y
        validateDiagonal(dna, total);
        return CompletableFuture.completedFuture(isMutant);
    }

    private boolean validateDiagonal(final char[][] dna, final int total) {
        int partialTotal = total;
        boolean isMutant = false;
        for (int i = dna.length; i >= 0; i--) {
            StringBuilder diagonal = new StringBuilder();
            for (int j = 0; j < dna.length - i; j++) {
                diagonal.append(dna[i + j][j]);
            }
            if (isValidSequence(diagonal.toString())) {
                partialTotal = total + 1;
            }
            if (partialTotal >= MIN_TOTAL_SEQUENCE) {
                isMutant = true;
                break;
            }
        }
        return isMutant;
    }

    private char[][] createArray(String[] dnaList) {
        char[][] mutant;
        int sizePerColumn = dnaList[0].length();
        int sizePerRows = dnaList.length;
        mutant = new char[sizePerRows][sizePerColumn];
        for (int i = 0; i < sizePerRows; i++) {
            if (sizePerColumn != dnaList[i].length()) {
                throwIllegalArgumentException("Each element in the array must have the same size.");
            }
            char[] row = dnaList[i].toCharArray();
            for (int j = 0; j < row.length; j++) {
                if (row[j] == 'A' || row[j] == 'T' || row[j] == 'C' || row[j] == 'G') {
                    mutant[i][j] = row[j];
                } else {
                    throwIllegalArgumentException("Invalid argument. It must contains A, T, C or G");
                }
            }
        }
        return mutant;
    }

    private void throwIllegalArgumentException(String message) {
        throw new IllegalArgumentException(message);
    }

    private boolean isValidSequence(String sequence) {
        boolean isValidSequence = false;
        if (sequence.contains(ADN_BASE_T) || sequence.contains(ADN_BASE_G) || sequence.contains(ADN_BASE_A)) {
            isValidSequence = true;
        }
        return isValidSequence;
    }
}
