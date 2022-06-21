package agenor.houessou.projetcovoiture_houessou_monvoisin;

import static java.lang.Integer.parseInt;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import agenor.houessou.projetcovoiture_houessou_monvoisin.liste.trajets.AdapteurTrajet;
import agenor.houessou.projetcovoiture_houessou_monvoisin.liste.trajets.ListeDesTrajets;
import agenor.houessou.projetcovoiture_houessou_monvoisin.objets.metier.AdapteurVille;
import agenor.houessou.projetcovoiture_houessou_monvoisin.objets.metier.Trajet;
import agenor.houessou.projetcovoiture_houessou_monvoisin.objets.metier.Ville;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RechercheTrajet#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RechercheTrajet extends Fragment implements AdapterView.OnItemClickListener {
    private Context context;
    private View view;
    private ArrayList<Trajet> listeTrajet;
    private ArrayList<Ville> listeVilles = new ArrayList<Ville>();

    public static RechercheTrajet newInstance() {
        return (new RechercheTrajet());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        listeTrajet = new ArrayList<Trajet>();
        View view = getView();
        getListeTrajet(view);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recherche_trajet, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        this.view = view;
        Button quitBtn = view.findViewById(R.id.rechercher);
        quitBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                searchListeTrajet(RechercheTrajet.this.view);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // TODO : setup l'adapteur
        //https://developer.android.com/guide/topics/ui/controls/spinner
        getVilles(view, getContext());
    }

    public void getVilles(View view, Context actualContext){
        Log.d("ronan","getVilles");
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url = "https://dev.lamy.bzh/listeVille";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject item = response.getJSONObject(i);

                                Iterator<String> keys = item.keys();

                                while( keys.hasNext() ) {
                                    String key = (String) keys.next();
                                    try {
                                        listeVilles.add(new Ville(parseInt(key),(String)item.get(key)));
                                    } catch (JSONException e) {
                                        Log.e("ronan",e.toString());
                                        e.printStackTrace();
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Spinner spinner = (Spinner) view.findViewById(R.id.ville_spinner);
                        // Create an ArrayAdapter using the string array and a default spinner layout
                        AdapteurVille adapter = new AdapteurVille(actualContext, listeVilles, getActivity());
                        // Specify the layout to use when the list of choices appears
                        adapter.setDropDownViewResource(R.layout.list_item);
                        // Apply the adapter to the spinner
                        spinner.setAdapter(adapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ronan",error.toString());
                Toast.makeText(context, "Fail to get data..", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders(){
                Map<String, String> params = new HashMap<String, String>();
                SharedPreferences token = context.getSharedPreferences("Login", Context.MODE_PRIVATE);
                //Log.d("ronan","setHeader:"+token.getString("token","vide"));
                params.put("x-auth-token", token.getString("token","vide"));
                return params;
            }
        };
        requestQueue.add(jsonArrayRequest);
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
                        ListView viewListeTrajet = (ListView)getView().findViewById(R.id.listeTrajetRecherche);
                        AdapteurTrajet adapteurTrajet = new AdapteurTrajet(context, R.layout.trajet_in_list_layout, listeTrajet, getActivity());

                        viewListeTrajet.setOnItemClickListener(RechercheTrajet.this);
                        viewListeTrajet.setAdapter(adapteurTrajet);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Fail to get data..", Toast.LENGTH_SHORT).show();
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

    public void searchListeTrajet(View view) {
        Log.d("ronan","searchListeTrajet"+view.toString());

        EditText depart = view.findViewById(R.id.search_depart);
        EditText arrivee = view.findViewById(R.id.search_arrivee);
        EditText date = view.findViewById(R.id.search_date);
        EditText heure = view.findViewById(R.id.search_heure);

        // Instantiate the RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url = "https://dev.lamy.bzh/rechercheTrajet/"
                +depart.getText().toString()
                +"/"
                +arrivee.getText().toString()
                +"/"
                +date.getText().toString()
                +" "
                +heure.getText().toString();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

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
                        ListView viewListeTrajet = (ListView)getView().findViewById(R.id.listeTrajetRecherche);
                        AdapteurTrajet adapteurTrajet = new AdapteurTrajet(context, R.layout.trajet_in_list_layout, listeTrajet, getActivity());

                        viewListeTrajet.setOnItemClickListener(RechercheTrajet.this);
                        viewListeTrajet.setAdapter(adapteurTrajet);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Fail to get data..", Toast.LENGTH_SHORT).show();
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

        Bundle bundle = new Bundle();
        bundle.putInt("listeId", R.id.group2);
        bundle.putInt("soloId", R.id.trajetSoloSearch);
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
                .replace(R.id.trajetSoloSearch, trajetSolo)
                .commit();

        Log.d("ronan", "hello "+position+" "+ id);
    }

}