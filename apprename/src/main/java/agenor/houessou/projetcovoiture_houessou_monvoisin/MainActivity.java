package agenor.houessou.projetcovoiture_houessou_monvoisin;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import agenor.houessou.projetcovoiture_houessou_monvoisin.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Button button = (Button) findViewById(R.id.logout);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("ronan","test");
                // On créer l'objet d'édition des préférences
                SharedPreferences prefs = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();

                // On enlève les valeurs
                editor.remove("token");
                editor.remove("userId");

                // On enregistre les valeurs
                editor.apply();
                setContentView(R.layout.activity_login);
            }
        });
    }
}