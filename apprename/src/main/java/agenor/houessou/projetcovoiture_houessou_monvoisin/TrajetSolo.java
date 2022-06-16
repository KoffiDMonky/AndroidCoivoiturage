package agenor.houessou.projetcovoiture_houessou_monvoisin;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrajetSolo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrajetSolo extends Fragment {

    public static TrajetSolo newInstance() {
        return (new TrajetSolo());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trajet_solo, container, false);
        Log.d("ronan", "View "+ view.toString());

        Bundle bundle = this.getArguments();

        TextView depart = view.findViewById(R.id.solo_depart);
        TextView arrive = view.findViewById(R.id.solo_arrive);
        TextView nbKms = view.findViewById(R.id.solo_kms);
        TextView dateTrajet = view.findViewById(R.id.solo_date);
        //Log.d("ronan",depart.getFontFeatureSettings());

        /*depart.setText(bundle.getString("ville_dep"));
        arrive.setText(bundle.getString("ville_arr"));
        nbKms.setText(bundle.getInt("nbKms")+"");
        dateTrajet.setText(bundle.getString("dateTrajet"));*/

        return view;
    }

}