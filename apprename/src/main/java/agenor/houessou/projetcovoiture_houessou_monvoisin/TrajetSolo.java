package agenor.houessou.projetcovoiture_houessou_monvoisin;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import agenor.houessou.projetcovoiture_houessou_monvoisin.ui.main.PlaceholderFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrajetSolo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrajetSolo extends Fragment {
    private int layout = R.layout.fragment_trajet_solo;
    private int soloId = R.id.trajetSoloListe;
    private int listeId = R.id.listeTrajet;

    public TrajetSolo(){
        super(R.layout.fragment_trajet_solo);
    }

    public static TrajetSolo newInstance() {
        TrajetSolo fragment = new TrajetSolo();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        // Put info into text fields
        Bundle bundle = this.getArguments();
        if(bundle != null) {
            Log.d("ronan", "onViewCreated solo :"+ bundle.toString());
            listeId = bundle.getInt("listeId",R.id.listeTrajet);
            soloId = bundle.getInt("soloId",R.id.trajetSoloListe);
        }
        populateView(view);

        // Config button to go back on list view
        Button quitBtn = view.findViewById(R.id.quit);
        quitBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                View rootView = view.getRootView();
                rootView.findViewById(listeId).setVisibility(View.VISIBLE);
                rootView.findViewById(soloId).setVisibility(View.INVISIBLE);
            }
        });
    }

    public void onStart() {
        super.onStart();
    }

    private void populateView(View view){
        Bundle bundle = this.getArguments();
        if(bundle != null){
            Log.d("ronan", "Trajet solo :"+ bundle.toString());

            // On recupère les textview des champs
            TextView depart = view.findViewById(R.id.solo_depart);
            TextView arrive = view.findViewById(R.id.solo_arrive);
            TextView nbKms = view.findViewById(R.id.solo_kms);
            TextView dateTrajet = view.findViewById(R.id.solo_date);

            // On insère les valeurs dans les textview
            depart.setText(bundle.getString("ville_dep"));
            arrive.setText(bundle.getString("ville_arr"));
            nbKms.setText(bundle.getInt("nbKms")+"");
            dateTrajet.setText(bundle.getString("dateTrajet"));

            Log.d("ronan","soloId:"+soloId);
            Log.d("ronan","listeId:"+listeId);
            // On affiche la vue solo et cache la liste
            View rootView = view.getRootView();
            rootView.findViewById(soloId).setVisibility(View.VISIBLE);
            rootView.findViewById(listeId).setVisibility(View.INVISIBLE);
        }
    }

}