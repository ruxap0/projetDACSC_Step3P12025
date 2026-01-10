package hepl.DACSC.protocol.Reponse;

public class ReponseLoginFirst implements IReponse {
    private boolean succes;
    private byte[] salt;
    private String message;

    public ReponseLoginFirst(boolean succes, byte[] salt, String message) {
        this.succes = succes;
        this.salt = salt;
        this.message = message;
    }

    public boolean isSucces() {return succes;}
    public byte[] getSalt() {return salt;}
    public String getMessage() {return message;}
}
