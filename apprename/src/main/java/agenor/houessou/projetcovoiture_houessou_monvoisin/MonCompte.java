package agenor.houessou.projetcovoiture_houessou_monvoisin;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
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

        View v = inflater.inflate(R.layout.fragment_mon_compte, container, false);
        Button buttonUpdate = v.findViewById(R.id.modifier);
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            //APPEL de la méthode de l'interface pour envoyer message F2
            //à l'activity quand on clique sur le bouton
            public void onClick(View v) {

                updateDataUser(v);
            }
        });

        // Inflate the layout for this fragment
        return v;
    }


    public void getDataUser(View view){

        Log.d("agénor","getDataUser");
        // Instantiate the RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        SharedPreferences idUser = context.getSharedPreferences("Login", Context.MODE_PRIVATE);

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


                            String ville = response.getString("ville");
                            EditText editTextVille = (EditText)getView().findViewById(R.id.ville);
                            editTextVille.setText(ville, TextView.BufferType.EDITABLE);

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

    // Lancé à l'appuie du bouton "Modifier"
    public void updateDataUser(View view){

        Log.d("agénor","upDateDataUser");

        EditText nom = getView().findViewById(R.id.nom);
        EditText prenom = getView().findViewById(R.id.prenom);
        EditText tel = getView().findViewById(R.id.telephone);
        EditText email = getView().findViewById(R.id.email);
        EditText ville = getView().findViewById(R.id.ville);
        EditText nbPlaces = getView().findViewById(R.id.places);
        EditText marque = getView().findViewById(R.id.marque);
        EditText modele = getView().findViewById(R.id.modele);


        if (nom.getText().toString().equals("")) {
            Toast.makeText(context, "Vous n'avez pas entré de nom.", Toast.LENGTH_SHORT).show();
        } else if (prenom.getText().toString().equals("")) {
            Toast.makeText(context, "Vous n'avez pas entré de prenom.", Toast.LENGTH_SHORT).show();
        } else if (tel.getText().toString().equals("")) {
            Toast.makeText(context, "Vous n'avez pas entré de numéro de téléphone.", Toast.LENGTH_SHORT).show();
        } else if (email.getText().toString().equals("")) {
            Toast.makeText(context, "Vous n'avez pas entré d'email.", Toast.LENGTH_SHORT).show();
        } else if (nbPlaces.getText().toString().equals("")) {
            Toast.makeText(context, "Vous n'avez pas entré le nombre de place disponible.", Toast.LENGTH_SHORT).show();
        } else if (marque.getText().toString().equals("")) {
            Toast.makeText(context, "Vous n'avez pas entré la marque de votre véhicule.", Toast.LENGTH_SHORT).show();
        } else if (modele.getText().toString().equals("")) {
            Toast.makeText(context, "Vous n'avez pas entré la modèle de votre véhicule.", Toast.LENGTH_SHORT).show();
        } else {


            // Instantiate the RequestQueue.
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            SharedPreferences idUser = context.getSharedPreferences("Login", Context.MODE_PRIVATE);
            Log.d("agénor", "setIdUser:" + idUser.getInt("userId", 1));

            String url = "https://dev.lamy.bzh/updatePersonne/"
                    + prenom.getText().toString()
                    + "/" + nom.getText().toString()
                    + "/" + tel.getText().toString()
                    + "/" + email.getText().toString()
                    + "/" + marque.getText().toString()
                    + "/" + modele.getText().toString()
                    + "/" + nbPlaces.getText().toString()
                    + "/" + idUser.getInt("userId", 1);

            Log.d("agénor", "url:" + url);
            //https://dev.lamy.bzh/updatePersonne/ronan/monvoisin/0123456789/test@test.fr/7/8/5/22

// Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Log.d("agénor", "reponse" + response);

                            if(response.equals("[\"ok\"]")){
                                Toast.makeText(context, "Vous avez bien modifié votre compte.", Toast.LENGTH_SHORT).show();
                                getDataUser(getView());
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("agénor", "error" + error);
                }
            }){
                @Override
                public Map<String, String> getHeaders(){
                    Map<String, String> params = new HashMap<String, String>();
                    SharedPreferences token = context.getSharedPreferences("Login", Context.MODE_PRIVATE);
                    Log.d("agénor","setHeader:"+token.getString("token","vide"));
                    params.put("x-auth-token", token.getString("token","vide"));
                    return params;
                }
            };

// Add the request to the RequestQueue.
            requestQueue.add(stringRequest);


        }
    }

}