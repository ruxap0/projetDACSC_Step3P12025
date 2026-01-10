package hepl.DACSC.protocol.Reponse;

public class ReponseLoginSession implements IReponse{
    private boolean succes;
    private String message;

    public ReponseLoginSession(boolean succes, String message) {
        this.succes = succes;
        this.message = message;
    }

    public boolean isSucces() {return succes;}
    public String getMessage() {return message;}

}
