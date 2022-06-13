package agenor.houessou.projetcovoiture_houessou_monvoisin;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Trajet implements Serializable {
  private int id;
  private String ville_dep;
  private String ville_arr;
  private int nbKms;
  private Date dateTrajet;
  private int id_pers;

  public Trajet(int id, String ville_dep, String ville_arr, int nbKms, Date dateTrajet, int id_pers) {
    setId(id);
    setVille_dep(ville_dep);
    setVille_arr(ville_arr);
    setNbKms(nbKms);
    setDateTrajet(dateTrajet);
    setId_pers(id_pers);
  }

  public Trajet(int id, String ville_dep, String ville_arr, int nbKms, String dateTrajetString, int id_pers) {
    setId(id);
    setVille_dep(ville_dep);
    setVille_arr(ville_arr);
    setNbKms(nbKms);
    setDateTrajetFromString(dateTrajetString);
    setId_pers(id_pers);
  }
  public Trajet(int id, String ville_dep, String ville_arr, int nbKms, int id_pers) {
    setId(id);
    setVille_dep(ville_dep);
    setVille_arr(ville_arr);
    setNbKms(nbKms);
    setDateTrajetFromString("16-05-2022 04:16");
    setId_pers(id_pers);
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getVille_dep() {
    return ville_dep;
  }

  public void setVille_dep(String ville_dep) {
    this.ville_dep = ville_dep;
  }

  public String getVille_arr() {
    return ville_arr;
  }

  public void setVille_arr(String ville_arr) {
    this.ville_arr = ville_arr;
  }

  public int getNbKms() {
    return nbKms;
  }

  public void setNbKms(int nbKms) {
    this.nbKms = nbKms;
  }

  public Date getDateTrajet() {
    return dateTrajet;
  }

  public void setDateTrajet(Date dateTrajet) {
    this.dateTrajet = dateTrajet;
  }

  public void setDateTrajetFromString(String dateTrajetString) {
    SimpleDateFormat simpledateformat = new SimpleDateFormat("dd-MM-yy kk:mm");
    try {
      this.dateTrajet = simpledateformat.parse(dateTrajetString);
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  public int getId_pers() {
    return id_pers;
  }

  public void setId_pers(int id_pers) {
    this.id_pers = id_pers;
  }
}
