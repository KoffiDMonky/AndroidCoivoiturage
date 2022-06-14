package agenor.houessou.projetcovoiture_houessou_monvoisin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import agenor.houessou.projetcovoiture_houessou_monvoisin.databinding.ActivityMainBinding;
import agenor.houessou.projetcovoiture_houessou_monvoisin.ui.main.SectionsPagerAdapter;

public class Login extends AppCompatActivity {
  private Context rContext;
  private ActivityMainBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
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
              Log.d("ronan", token + " "+ userId);
              // On créer l'objet d'édition des préférences
              SharedPreferences prefs = Login.this.getPreferences(Context.MODE_PRIVATE);
              SharedPreferences.Editor editor = prefs.edit();
              // On ajoute les valeurs
              editor.putString("token",token);
              editor.putInt("userId",userId);
              // On enregistre les valeurs
              editor.apply();
              logedIn(view);
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


  public void logedIn(View view) {
    setContentView(R.layout.activity_main);

    binding = ActivityMainBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
    ViewPager viewPager = binding.viewPager;
    viewPager.setAdapter(sectionsPagerAdapter);
    TabLayout tabs = binding.tabs;
    tabs.setupWithViewPager(viewPager);
  }

  public void test(View view){
    SharedPreferences prefs = Login.this.getPreferences(Context.MODE_PRIVATE);
    Toast.makeText(Login.this, prefs.getString("token","vide") + " "+ prefs.getInt("userId",0), Toast.LENGTH_SHORT).show();
  }
}