package communication;

import java.io.Serializable;

public class ResponseImpl implements Response, Serializable {
    private String message;
    private boolean successful;

    public ResponseImpl(String message, boolean successful) {
        this.message = message;
        this.successful = successful;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccessful() {
        return successful;
    }
}
