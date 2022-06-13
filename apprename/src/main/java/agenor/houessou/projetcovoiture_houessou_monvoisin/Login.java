package agenor.houessou.projetcovoiture_houessou_monvoisin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import agenor.houessou.projetcovoiture_houessou_monvoisin.databinding.ActivityMainBinding;
import agenor.houessou.projetcovoiture_houessou_monvoisin.ui.main.SectionsPagerAdapter;

public class Login extends AppCompatActivity {
  private Context rContext;
  private ActivityMainBinding binding;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Context rContext = this;
    setContentView(R.layout.activity_login);
  }


  public void skipLogin(View view){
    setContentView(R.layout.activity_main);

    binding = ActivityMainBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
    ViewPager viewPager = binding.viewPager;
    viewPager.setAdapter(sectionsPagerAdapter);
    TabLayout tabs = binding.tabs;
    tabs.setupWithViewPager(viewPager);
  }

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
      String url = "https://dev.lamy.bzh/login/" + username.getText().toString() + "/" + password.getText().toString();
      StringRequest stringRequest = new StringRequest(
              Request.Method.GET,
              url,
              new Response.Listener() {
                @Override
                public void onResponse(Object response) {
                  Toast.makeText(rContext,
                          response.toString(),
                          Toast.LENGTH_SHORT).show();
                }
              },
              new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                  Toast.makeText(rContext,
                          error.toString(),
                          Toast.LENGTH_SHORT).show();
                }
              });
      requestQueue.add(stringRequest);
    }
  }
}