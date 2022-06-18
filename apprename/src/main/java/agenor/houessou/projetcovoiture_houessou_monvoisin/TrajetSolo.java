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
        Log.d("ronan","container: "+container.toString());

        return inflater.inflate(layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        populateView(view);
        Button quitBtn = view.findViewById(R.id.quit);
        quitBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                View rootView = view.getRootView();
                rootView.findViewById(R.id.listeTrajet).setVisibility(View.VISIBLE);
                rootView.findViewById(R.id.trajetSoloListe).setVisibility(View.INVISIBLE);
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

            TextView depart = view.findViewById(R.id.solo_depart);
            TextView arrive = view.findViewById(R.id.solo_arrive);
            TextView nbKms = view.findViewById(R.id.solo_kms);
            TextView dateTrajet = view.findViewById(R.id.solo_date);

            // TODO : comprendre pourquoi ça ne s'affiche pas

            depart.setText(bundle.getString("ville_dep"));
            arrive.setText(bundle.getString("ville_arr"));
            nbKms.setText(bundle.getInt("nbKms")+"");
            dateTrajet.setText(bundle.getString("dateTrajet"));

            View rootView = view.getRootView();
            rootView.findViewById(R.id.trajetSoloListe).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.listeTrajet).setVisibility(View.INVISIBLE);
        }
    }

}