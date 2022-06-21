package agenor.houessou.projetcovoiture_houessou_monvoisin.objets.metier;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import agenor.houessou.projetcovoiture_houessou_monvoisin.R;
import agenor.houessou.projetcovoiture_houessou_monvoisin.liste.trajets.AdapteurTrajet;

public class AdapteurVille extends ArrayAdapter<Ville> {
  private ArrayList<Ville> lVilles;
  private static LayoutInflater inflater = null;


  public AdapteurVille(Context context, ArrayList<Ville> villes, Activity activity) {
    super(context, R.layout.list_item, villes);
    lVilles = villes;
    inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }
  public int getCount(){
    return lVilles.size();
  }
  public int size(){
    return lVilles.size();
  }

  public Ville getItem(int position){
    return lVilles.get(position);
  }

  public long getItemId(int position){
    return lVilles.get(position).getId();
  }

  public int getPosition(Ville ville){
    return lVilles.indexOf(ville);
  }

  public static class ViewHolder {
    public TextView text;
  }

  public View getView(int position, View convertView, ViewGroup  parent){
    View vi = convertView;
    final ViewHolder holder;

    try {
      if (convertView == null) {
        vi = inflater.inflate(R.layout.list_item, null);
        holder = new ViewHolder();

        holder.text = (TextView) vi.findViewById(R.id.text);

        vi.setTag(holder);
      } else {
        holder = (ViewHolder) vi.getTag();
      }
      holder.text.setText(lVilles.get(position).getNom());
      //android.text.format.DateFormat.
    } catch (Exception e) {


    }
    return vi;
  }
}