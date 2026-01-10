package hepl.DACSC.protocol.Reponse;

public class ReponseErreur implements IReponse{
    public static String Erreur = "Requete non reconnue par ce protocole";

    public ReponseErreur(){}

    public static String getErreur() {
        return Erreur;
    }
}
