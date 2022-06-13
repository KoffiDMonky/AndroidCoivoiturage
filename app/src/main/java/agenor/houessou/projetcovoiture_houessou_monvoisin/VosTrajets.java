package agenor.houessou.projetcovoiture_houessou_monvoisin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VosTrajets#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VosTrajets extends Fragment {

    public static VosTrajets newInstance() {
        return (new VosTrajets());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vos_trajets, container, false);
    }
}