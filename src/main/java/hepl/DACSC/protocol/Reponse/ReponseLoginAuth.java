package hepl.DACSC.protocol.Reponse;

public class ReponseLoginAuth implements IReponse {
    private boolean succes;
    private String message;

    public ReponseLoginAuth(boolean succes, String message) {
        this.succes = succes;
        this.message = message;
    }

    public boolean isSucces() {return succes;}
    public String getMessage() {return message;}
}
