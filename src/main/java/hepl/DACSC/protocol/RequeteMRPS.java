package hepl.DACSC.protocol;

import java.io.*;
import java.security.*;

public abstract class RequeteMRPS implements Serializable {
    private String commande;
    private byte[] signature;

    public RequeteMRPS(String commande) {
        this.commande = commande;
    }

    public String getCommande() {
        return commande;
    }

    protected abstract byte[] getDataToSign() throws IOException;

    public void signRequest(PrivateKey clePriveeClient) throws NoSuchAlgorithmException,
            NoSuchProviderException, InvalidKeyException, SignatureException, IOException {
        Signature s = Signature.getInstance("SHA256withRSA", "BC");
        s.initSign(clePriveeClient);
        s.update(getDataToSign());
        signature = s.sign();
    }

    public boolean verifySignature(PublicKey clePubliqueClient) throws NoSuchAlgorithmException,
            NoSuchProviderException, InvalidKeyException, SignatureException, IOException {
        if (signature == null) return false;

        Signature s = Signature.getInstance("SHA256withRSA", "BC");
        s.initVerify(clePubliqueClient);
        s.update(getDataToSign());
        return s.verify(signature);
    }
}