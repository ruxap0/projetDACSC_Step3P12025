package hepl.DACSC.protocol.Requetes;

import hepl.DACSC.protocol.RequeteMRPS;

public class RequeteLoginAuth implements IRequete {
    private byte[] digest;
    private byte[] signature;

    public RequeteLoginAuth(byte[] digest, byte[] signature) {
        this.digest = digest;
        this.signature = signature;
    }

    public byte[] getDigest() {return digest;}
    public byte[] getSignature() {return signature;}
}