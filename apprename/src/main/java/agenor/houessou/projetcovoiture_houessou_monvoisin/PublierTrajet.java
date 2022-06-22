package agenor.houessou.projetcovoiture_houessou_monvoisin;

import static java.lang.Integer.parseInt;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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
import java.util.Calendar;
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
    EditText etDate;
    DatePickerDialog.OnDateSetListener setListener;

    public static PublierTrajet newInstance() {
        return (new PublierTrajet());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d("agénor Items","items");
        context = container.getContext();
        View v = inflater.inflate(R.layout.fragment_publier_trajet, container, false);

        etDate = v.findViewById(R.id.date);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);


        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("agénor", "click");
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month+1;
                        String date = day+"/"+month+"/"+year;
                        etDate.setText(date);
                    }
                }, year,month,day);
                datePickerDialog.show();
            }
        });


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_publier_trajet, container, false);
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