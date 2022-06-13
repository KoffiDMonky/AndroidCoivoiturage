package agenor.houessou.projetcovoiture_houessou_monvoisin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MonCompte#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MonCompte extends Fragment {

    public static MonCompte newInstance() {
        return (new MonCompte());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mon_compte, container, false);
    }
}