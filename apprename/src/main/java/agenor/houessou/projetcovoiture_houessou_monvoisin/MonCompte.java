package agenor.houessou.projetcovoiture_houessou_monvoisin;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MonCompte#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MonCompte extends Fragment {

    private Context context;

    public static MonCompte newInstance() {
        return (new MonCompte());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        getDataUser(getView());
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mon_compte, container, false);
    }


    public void getDataUser(View view){


        Log.d("agénor","getDataUser");
        // Instantiate the RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        SharedPreferences idUser = context.getSharedPreferences("Login", Context.MODE_PRIVATE);
        Log.d("agénor","setIdUser:"+idUser.getInt("userId", 1));

        String url = "https://dev.lamy.bzh/selectPersonne/" + idUser.getInt("userId", 1) ;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("agénor",response.toString());

                        try {

                            // On récupère les valeurs depuis l'objet JSON

                            String nom = response.getString("nom");
                            EditText editTextNom = (EditText)getView().findViewById(R.id.nom);
                            editTextNom.setText(nom, TextView.BufferType.EDITABLE);

                            String prenom = response.getString("prenom");
                            EditText editTextPrenom = (EditText)getView().findViewById(R.id.prenom);
                            editTextPrenom.setText(prenom, TextView.BufferType.EDITABLE);

                            String tel = response.getString("tel");
                            EditText editTextTel = (EditText)getView().findViewById(R.id.telephone);
                            editTextTel.setText(tel, TextView.BufferType.EDITABLE);

                            String email = response.getString("email");
                            EditText editTextMail = (EditText)getView().findViewById(R.id.email);
                            editTextMail.setText(email, TextView.BufferType.EDITABLE);


                            //String ville = response.getString("ville");
                            //EditText editTextVille = (EditText)getView().findViewById(R.id.adresse);
                            //editTextVille.setText(ville, TextView.BufferType.EDITABLE);

                            String nbPlaces = response.getString("nb_places");
                            EditText editTextPlace = (EditText)getView().findViewById(R.id.places);
                            editTextPlace.setText(nbPlaces, TextView.BufferType.EDITABLE);

                            String marque = response.getString("marque");
                            EditText editTextMarque = (EditText)getView().findViewById(R.id.marque);
                            editTextMarque.setText(marque, TextView.BufferType.EDITABLE);

                            String modele = response.getString("modele");
                            EditText editTextModele = (EditText)getView().findViewById(R.id.modele);
                            editTextModele.setText(modele, TextView.BufferType.EDITABLE);





                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



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
        requestQueue.add(jsonObjectRequest);

    }
}