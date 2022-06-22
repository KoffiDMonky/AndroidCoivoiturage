package agenor.houessou.projetcovoiture_houessou_monvoisin.liste.trajets;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.Inflater;

import agenor.houessou.projetcovoiture_houessou_monvoisin.Login;
import agenor.houessou.projetcovoiture_houessou_monvoisin.R;
import agenor.houessou.projetcovoiture_houessou_monvoisin.TrajetSolo;
import agenor.houessou.projetcovoiture_houessou_monvoisin.objets.metier.Trajet;
import agenor.houessou.projetcovoiture_houessou_monvoisin.ui.main.SectionsPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListeDesTrajets#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListeDesTrajets extends Fragment implements AdapterView.OnItemClickListener {
  private Context context;
  private ViewGroup container;
  private ArrayList<Trajet> listeTrajet;
  private Inflater inflater;

  public static ListeDesTrajets newInstance() {
    return (new ListeDesTrajets());
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    context = getActivity();
    listeTrajet = new ArrayList<Trajet>();
    getListeTrajet(getView());
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_liste_des_trajets, container, false);
  }

  public void getListeTrajet(View view) {

    Log.d("ronan","getListeTrajet");
    // Instantiate the RequestQueue.
    RequestQueue requestQueue = Volley.newRequestQueue(context);
    String url = "https://dev.lamy.bzh/listeTrajet";
    JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
      url,
      new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {
          Log.d("ronan",response.toString());
          for (int i = 0; i < response.length(); i++) {
            try {
              JSONArray array = response.getJSONArray(i);
              JSONObject data = array.getJSONObject(0);
              Trajet trajet = new Trajet(
                      data.getInt("id"),
                      data.getString("ville_dep"),
                      data.getString("ville_arr"),
                      data.getInt("nbKms"),
                      data.getString("DateTrajet"),
                      data.getInt("id_pers")
              );
              listeTrajet.add(trajet);
            } catch (JSONException e) {
              e.printStackTrace();
            }
          }

          // Create front list
          Log.d("ronan","start Adapteur");
          ListView viewListeTrajet = (ListView)getView().findViewById(R.id.listeTrajet);
          AdapteurTrajet adapteurTrajet = new AdapteurTrajet(context, R.layout.trajet_in_list_layout, listeTrajet, getActivity());

          viewListeTrajet.setOnItemClickListener(ListeDesTrajets.this);
          viewListeTrajet.setAdapter(adapteurTrajet);
        }
      }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
          Log.d("ronan","error getting list");
          Toast.makeText(context, "Fail to get data..", Toast.LENGTH_SHORT).show();

          // On créer l'objet d'édition des préférences
          SharedPreferences prefs = getContext().getSharedPreferences("Login",Context.MODE_PRIVATE);
          SharedPreferences.Editor editor = prefs.edit();

          // On ajoute les valeurs
          editor.putString("token", "vide");
          editor.putInt("userId", 0);

          // On enregistre les valeurs
          editor.apply();
        }
    }) {
      @Override
      public Map<String, String> getHeaders(){
        Map<String, String> params = new HashMap<String, String>();
        SharedPreferences token = context.getSharedPreferences("Login", Context.MODE_PRIVATE);
        Log.d("ronan","setHeader:"+token.getString("token","vide"));
        params.put("x-auth-token", token.getString("token","vide"));
        return params;
      }
    };
    requestQueue.add(jsonArrayRequest);
  }

  public void onItemClick(AdapterView parent, View v, int position, long id) {
    Trajet clickedTrajet = listeTrajet.get(position);
    Log.d("ronan","trajet"+clickedTrajet);

    Bundle bundle = new Bundle();
    bundle.putInt("listeId", R.id.listeTrajet);
    bundle.putInt("soloId", R.id.trajetSoloListe);
    bundle.putLong("id", id);
    bundle.putString("ville_dep", clickedTrajet.getVille_dep());
    bundle.putString("ville_arr", clickedTrajet.getVille_arr());
    bundle.putInt("nbKms", clickedTrajet.getNbKms());
    bundle.putString("dateTrajet", clickedTrajet.getDateTrajetString(context));
    bundle.putInt("id_pers", clickedTrajet.getId_pers());

    TrajetSolo trajetSolo = new TrajetSolo();
    trajetSolo.setArguments(bundle);

    FragmentTransaction ft = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
    ft
      .replace(R.id.trajetSoloListe, trajetSolo)
      .commit();

    Log.d("ronan", "hello "+position+" "+ id);
  }
}
