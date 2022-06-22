package agenor.houessou.projetcovoiture_houessou_monvoisin;

import static java.lang.Integer.parseInt;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
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

import agenor.houessou.projetcovoiture_houessou_monvoisin.objets.metier.AdapteurVille;
import agenor.houessou.projetcovoiture_houessou_monvoisin.objets.metier.Trajet;
import agenor.houessou.projetcovoiture_houessou_monvoisin.objets.metier.Ville;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PublierTrajet#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PublierTrajet extends Fragment {

    private Context context;
    private ArrayList<Trajet> listeTrajet;
    private ArrayList<Ville> listeVilles = new ArrayList<Ville>();

    public static PublierTrajet newInstance() {
        return (new PublierTrajet());
    }

    //String[] items = {"Vannes", "Rennes", "Pontivy"};
    int items = R.array.listeVille;





    AutoCompleteTextView autoCompleteTxt1;
    ArrayAdapter<String> adapterItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d("agénor Items","items");
        context = container.getContext();
        View v = inflater.inflate(R.layout.fragment_publier_trajet, container, false);

        /*autoCompleteTxt1 = v.findViewById(R.id.auto_complete_txt1);
        adapterItems = new ArrayAdapter<String>(context, R.layout.list_item, items);
        autoCompleteTxt1.setAdapter(adapterItems);

        autoCompleteTxt1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(context, "Item:"+item, Toast.LENGTH_SHORT).show();
            }
        });*/


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_publier_trajet, container, false);
    }

    public void inputListData(View view){

        //val items = listOf("Option 1", "Option 2", "Option 3", "Option 4")
        //val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        //(textField.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        Log.d("agénor","inputListData");
        // Instantiate the RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        String url = "https://dev.lamy.bzh/listeVille";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        //try {

                            // On récupère les valeurs depuis l'objet JSON

                            //String nom = response.getString("nom");
                            //EditText editTextNom = (EditText)getView().findViewById(R.id.nom);
                            //editTextNom.setText(nom, TextView.BufferType.EDITABLE);


                        //} catch (JSONException e) {
                        //    e.printStackTrace();
                        //}



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
                params.put("x-auth-token", token.getString("token","vide"));
                return params;
            }
        };
        requestQueue.add(jsonArrayRequest);

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
                        //adapter.setDropDownViewResource(R.layout.list_item);
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

}