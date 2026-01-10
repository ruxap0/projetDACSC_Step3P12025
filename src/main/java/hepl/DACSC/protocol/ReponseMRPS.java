package hepl.DACSC.protocol;

import java.io.*;

public class ReponseMRPS implements Serializable {
    private boolean success;
    private String message;
    private byte[] data; // données cryptées symétriquement (pour LIST_REPORTS)
    private byte[] hmac; // HMAC de la réponse (pour LIST_REPORTS)

    public ReponseMRPS(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ReponseMRPS(boolean success, String message, byte[] data, byte[] hmac) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.hmac = hmac;
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public byte[] getData() { return data; }
    public byte[] getHmac() { return hmac; }
}