package agenor.houessou.projetcovoiture_houessou_monvoisin;

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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PublierTrajet#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PublierTrajet extends Fragment {

    private Context context;

    public static PublierTrajet newInstance() {
        return (new PublierTrajet());
    }

    String[] lieux = {"Vannes", "Rennes", "Pontivy"};
    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterLieux;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();

        //inputListData(getView());

        //val items = listOf("Option 1", "Option 2", "Option 3", "Option 4")
        //val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        //(textField.editText as? AutoCompleteTextView)?.setAdapter(adapter)

       // String[] lieux = getResources().getStringArray(R.array.lieux);
       // String[] adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, lieux);
        View v = inflater.inflate(R.layout.fragment_publier_trajet, container, false);
        autoCompleteTxt = v.findViewById(R.id.autoCompleteTextView);

        adapterLieux = new ArrayAdapter<String>(context, R.layout.dropdown_item,lieux);
        autoCompleteTxt.setAdapter(adapterLieux);

        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                String lieu = parent.getItemAtPosition(i).toString();
                Toast.makeText(context, "lieu: "+lieu, Toast.LENGTH_SHORT).show();
            }
        });


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
}