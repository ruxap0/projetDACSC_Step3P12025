package hepl.DACSC.protocol.Requetes;

public class RequeteLoginFirst implements IRequete {
    private String login;

    public RequeteLoginFirst(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }
}
