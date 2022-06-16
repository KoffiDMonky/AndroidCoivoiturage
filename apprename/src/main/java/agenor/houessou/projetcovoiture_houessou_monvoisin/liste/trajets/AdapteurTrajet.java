package agenor.houessou.projetcovoiture_houessou_monvoisin.liste.trajets;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import agenor.houessou.projetcovoiture_houessou_monvoisin.R;
import agenor.houessou.projetcovoiture_houessou_monvoisin.objets.metier.Trajet;

public class AdapteurTrajet extends ArrayAdapter<Trajet> {
  private Activity activity;
  private ArrayList<Trajet> lTrajet;
  private static LayoutInflater inflater = null;

  public AdapteurTrajet(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<Trajet> objects) {
    super(context, resource, textViewResourceId, objects);
    try {
      this.activity = activity;
      this.lTrajet = (ArrayList<Trajet>) objects;

      inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    } catch (Exception e) {

    }

  }

  public AdapteurTrajet(@NonNull Context context, int textViewResourceId, @NonNull List<Trajet> objects, Activity activity) {
    super(context, textViewResourceId, objects);
    Log.d("ronan","start AdapteurTrajet");
    try {
      this.activity = activity;
      this.lTrajet = (ArrayList<Trajet>) objects;

      inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    } catch (Exception e) {
      Log.e("ronan",e.toString());
    }
  }

  public int getCount(){
    return lTrajet.size();
  }

  public Trajet getItem(int position){
    return lTrajet.get(position);
  }

  public long getItemId(int position){
    return lTrajet.get(position).getId();
  }

  public int getPosition(Trajet trajet){
    return lTrajet.indexOf(trajet);
  }

  public static class ViewHolder {
    public TextView depart;
    public TextView arrive;
    public TextView nbKms;
    public TextView dateTrajet;
  }

  public View getView(int position, View convertView, ViewGroup  parent){
    View vi = convertView;
    final ViewHolder holder;

    try {
      if (convertView == null) {
        vi = inflater.inflate(R.layout.trajet_in_list_layout, null);
        holder = new ViewHolder();

        holder.depart = (TextView) vi.findViewById(R.id.depart);
        holder.arrive = (TextView) vi.findViewById(R.id.arrive);
        holder.nbKms = (TextView) vi.findViewById(R.id.nbKms);
        holder.dateTrajet = (TextView) vi.findViewById(R.id.date_trajet);

        vi.setTag(holder);
      } else {
        holder = (ViewHolder) vi.getTag();
      }
      holder.depart.setText(lTrajet.get(position).getVille_dep());
      holder.arrive.setText(lTrajet.get(position).getVille_arr());
      holder.nbKms.setText(lTrajet.get(position).getNbKms()+" kms");
      holder.dateTrajet.setText(lTrajet.get(position).getDateTrajetString(getContext()));
      //android.text.format.DateFormat.
    } catch (Exception e) {


    }
    return vi;

  }

}
