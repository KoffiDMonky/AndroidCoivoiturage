package agenor.houessou.projetcovoiture_houessou_monvoisin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RechercheTrajet#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RechercheTrajet extends Fragment {

    public static RechercheTrajet newInstance() {
        return (new RechercheTrajet());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recherche_trajet, container, false);
    }
}