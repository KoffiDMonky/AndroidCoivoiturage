package agenor.houessou.projetcovoiture_houessou_monvoisin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;

import agenor.houessou.projetcovoiture_houessou_monvoisin.databinding.ActivityMainBinding;
import agenor.houessou.projetcovoiture_houessou_monvoisin.ui.main.SectionsPagerAdapter;

public class Login extends AppCompatActivity {

  private ActivityMainBinding binding;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
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
/*
  TextInputEditText username = (TextInputEditText)findViewById(R.id.TextInputEditText);
  TextInputEditText password = (TextInputEditText)findViewById(R.id.TextInputEditText);

  public void login(View view){
    if(username.getText().toString().equals("admin") && password.getText().toString().equals("admin")){

      //correct password
    }else{
      //wrong password
    }*/
}