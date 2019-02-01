package mutant.validators;

import mutant.domain.Sequence;
import org.springframework.stereotype.Component;


/**
 * @author vvicario
 */
@Component
public class SequenceValidator {

    static final String ERROR_MESSAGE = "Invalid size. Min zise: 4";

    public void validate(Object obj) {
        Sequence sample = (Sequence) obj;
        if(sample.getDna() == null || sample.getDna().length < 4) {
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }
    }
}
