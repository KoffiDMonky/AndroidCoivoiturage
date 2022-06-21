package agenor.houessou.projetcovoiture_houessou_monvoisin;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
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
import java.util.Map;

import agenor.houessou.projetcovoiture_houessou_monvoisin.liste.trajets.AdapteurTrajet;
import agenor.houessou.projetcovoiture_houessou_monvoisin.objets.metier.Trajet;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VosTrajets#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VosTrajets extends Fragment {

    private Context context;

    public static VosTrajets newInstance() {
        return (new VosTrajets());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = getActivity();
        getMesTrajet(getView());
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vos_trajets, container, false);
    }

    public void getMesTrajet(View view) {

        Log.d("agénor","getMestrajet");

        // Instantiate the RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        SharedPreferences idUser = context.getSharedPreferences("Login", Context.MODE_PRIVATE);



        String url = "https://dev.lamy.bzh/listeInscriptionUser/" + idUser.getInt("userId", 1) ;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Log.d("response agénor",response.toString());

                        ArrayList<Trajet> listeTrajet = new ArrayList<Trajet>();
                        for (int i = 0; i < response.length(); i++) {

                            try {
                                JSONArray array = response.getJSONArray(i);
                                JSONObject data = array.getJSONObject(0);

                                Log.d("agénor data :",  data.toString());
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
                        Log.d("ronan","start Adapteur");
                        ListView viewListeTrajet = (ListView)getView().findViewById(R.id.listeTrajet);
                        AdapteurTrajet adapteurTrajet = new AdapteurTrajet(context, R.layout.trajet_in_list_layout, listeTrajet, getActivity());

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
                Log.d("agénor","setHeader:"+token.getString("token","vide"));
                params.put("x-auth-token", token.getString("token","vide"));
                return params;
            }
        };
        requestQueue.add(jsonArrayRequest);


    }
}