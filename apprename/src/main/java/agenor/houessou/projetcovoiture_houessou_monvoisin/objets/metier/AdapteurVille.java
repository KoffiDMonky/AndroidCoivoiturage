package agenor.houessou.projetcovoiture_houessou_monvoisin.objets.metier;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import agenor.houessou.projetcovoiture_houessou_monvoisin.R;

public class AdapteurVille extends ArrayAdapter<Ville> {
  public AdapteurVille(Context context, ArrayList<Ville> villes) {
    super(context, 0, villes);
  }
}