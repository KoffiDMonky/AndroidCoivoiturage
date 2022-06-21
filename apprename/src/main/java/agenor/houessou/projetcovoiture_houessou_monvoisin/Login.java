package agenor.houessou.projetcovoiture_houessou_monvoisin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import agenor.houessou.projetcovoiture_houessou_monvoisin.databinding.ActivityMainBinding;
import agenor.houessou.projetcovoiture_houessou_monvoisin.liste.trajets.AdapteurTrajet;
import agenor.houessou.projetcovoiture_houessou_monvoisin.liste.trajets.ListeDesTrajets;
import agenor.houessou.projetcovoiture_houessou_monvoisin.objets.metier.Trajet;
import agenor.houessou.projetcovoiture_houessou_monvoisin.ui.main.SectionsPagerAdapter;

public class Login extends AppCompatActivity {
  private Context rContext;
  private ActivityMainBinding binding;
  private int ok = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
  }

  protected void onStart() {
    super.onStart();

    SharedPreferences token = this.getSharedPreferences("Login", Context.MODE_PRIVATE);
    if(token.getString("token","vide") != "vide")
      logedIn();
  }

  // Lancé à l'appuie du bouton "Connexion" de l'activity login
  public void login(View view) {
    EditText username = findViewById(R.id.loginEmail);
    EditText password = findViewById(R.id.loginPass);
    rContext = this;

      if (username.getText().toString().equals("")) {
        Toast.makeText(this, "Vous n'avez pas entré d'identifiant.", Toast.LENGTH_SHORT).show();
      } else if (password.getText().toString().length() < 8) {
        Toast.makeText(this, "Le mot de passe doit contenir 8 caractères minimum.", Toast.LENGTH_SHORT).show();
      } else {
        // Instantiate the RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        // TODO : escape URL
        String url = "https://dev.lamy.bzh/login/" + username.getText().toString() + "/" + password.getText().toString();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                  @Override
                  public void onResponse(JSONObject response) {
                    try {
                      // On récupère les valeurs depuis l'objet JSON
                      String token = response.getString("token");
                      int userId = response.getInt("id_user");
                      Log.d("ronan", token + " " + userId);

                      // On créer l'objet d'édition des préférences
                      SharedPreferences prefs = Login.this.getPreferences(Context.MODE_PRIVATE);
                      SharedPreferences.Editor editor = prefs.edit();

                      // On ajoute les valeurs
                      editor.putString("token", token);
                      editor.putInt("userId", userId);

                      // On enregistre les valeurs
                      editor.apply();
                      logedIn();
                    } catch (JSONException e) {
                      e.printStackTrace();
                    }
                  }
                }, new Response.ErrorListener() {

          @Override
          public void onErrorResponse(VolleyError error) {
            Toast.makeText(Login.this, "Fail to get data..", Toast.LENGTH_SHORT).show();
          }
        });
        requestQueue.add(jsonObjectRequest);
      }
  }

  public void logedIn() {
    setContentView(R.layout.activity_main);

    binding = ActivityMainBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
    ViewPager viewPager = binding.viewPager;
    viewPager.setAdapter(sectionsPagerAdapter);
    TabLayout tabs = binding.tabs;
    tabs.setupWithViewPager(viewPager);
  }

  public int textToken(){
    SharedPreferences token = getApplicationContext().getSharedPreferences("Login", Context.MODE_PRIVATE);

    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
    String url = "https://dev.lamy.bzh/selectPersonne/"+token.getString("userId","0");
    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
            url,
            new Response.Listener<JSONObject>() {
              @Override
              public void onResponse(JSONObject response) {
                ok = 1;
              }
            }, new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), "Fail to get data..", Toast.LENGTH_SHORT).show();
        ok = 0;
      }
    }) {
      @Override
      public Map<String, String> getHeaders(){
        Map<String, String> params = new HashMap<String, String>();
        Log.d("ronan","setHeader:"+token.getString("token","vide"));
        params.put("x-auth-token", token.getString("token","vide"));
        return params;
      }
    };
    requestQueue.add(jsonObjectRequest);
    return ok;
  }

  public void test(View view){
    SharedPreferences prefs = Login.this.getPreferences(Context.MODE_PRIVATE);
    Toast.makeText(Login.this, prefs.getString("token","vide") + " "+ prefs.getInt("userId",0), Toast.LENGTH_SHORT).show();
  }
}