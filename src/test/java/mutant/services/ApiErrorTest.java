package mutant.services;

import mutant.dto.ApiError;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;

/**
 * @author vvicario
 */
public class ApiErrorTest {

    private ApiError apiError;
    private static final String MESSAGE = "test";

    @Test
    public void testCreateApiWithConstructorError() {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, MESSAGE);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, apiError.getStatus());
        Assert.assertEquals(MESSAGE, apiError.getMessage());
    }

    @Test
    public void testCreateApiError() {
        ApiError apiError = new ApiError();
        apiError.setMessage(MESSAGE);
        apiError.setStatus(HttpStatus.BAD_REQUEST);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, apiError.getStatus());
        Assert.assertEquals(MESSAGE, apiError.getMessage());
    }
}
