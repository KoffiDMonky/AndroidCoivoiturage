package agenor.houessou.projetcovoiture_houessou_monvoisin;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        Log.d("ronan","setIdUser:"+idUser.getInt("userId", 1));

        String url = "https://dev.lamy.bzh/selectPersonne/" + idUser.getInt("userId", 1) ;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            // On récupère les valeurs depuis l'objet JSON

                            String nom = response.getString("nom");
                            String prenom = response.getString("prenom");
                            String tel = response.getString("tel");
                            String email = response.getString("email");
                            String ville = response.getString("ville");
                            String nbPlaces = response.getString("nbPlaces");
                            String marque = response.getString("marque");
                            String modele = response.getString("modele");

                            Log.d("agénor", response.toString());

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