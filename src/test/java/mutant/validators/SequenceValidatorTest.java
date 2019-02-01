package mutant.validators;

import mutant.domain.Sequence;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author vvicario
 */
public class SequenceValidatorTest {

    private SequenceValidator validator;

    @Before
    public void setUp() {
        validator = new SequenceValidator();
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void testValidateNullSequence() {
        Sequence sequence = new Sequence();
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(SequenceValidator.ERROR_MESSAGE);
        validator.validate(sequence);
    }

    @Test
    public void testValidateInvalidSequence() {
        Sequence sequence = new Sequence();
        String dna[] = {"ATGCGA","CAGTGC"};
        sequence.setDna(dna);
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(SequenceValidator.ERROR_MESSAGE);
        validator.validate(sequence);
    }

    @Test
    public void testValidateSuccessSequence() {
        Sequence sequence = new Sequence();
        String[] dna = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
        sequence.setDna(dna);
    }
}
