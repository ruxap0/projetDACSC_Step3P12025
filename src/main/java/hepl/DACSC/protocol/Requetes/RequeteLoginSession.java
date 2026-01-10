package hepl.DACSC.protocol.Requetes;

public class RequeteLoginSession implements IRequete {
    private byte[] _cryptedSessionKey;

    public RequeteLoginSession(byte[] cryptedSessionKey) {
        this._cryptedSessionKey = cryptedSessionKey;
    }

    public byte[] getCryptedSessionKey() {
        return this._cryptedSessionKey;
    }
}
