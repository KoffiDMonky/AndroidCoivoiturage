package agenor.houessou.projetcovoiture_houessou_monvoisin.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import agenor.houessou.projetcovoiture_houessou_monvoisin.ListeDesTrajets;
import agenor.houessou.projetcovoiture_houessou_monvoisin.MonCompte;
import agenor.houessou.projetcovoiture_houessou_monvoisin.PublierTrajet;
import agenor.houessou.projetcovoiture_houessou_monvoisin.R;
import agenor.houessou.projetcovoiture_houessou_monvoisin.RechercheTrajet;
import agenor.houessou.projetcovoiture_houessou_monvoisin.VosTrajets;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3, R.string.tab_text_4, R.string.tab_text_5};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: //Page number 1
                return ListeDesTrajets.newInstance();
            case 1: //Page number 2
                return RechercheTrajet.newInstance();
            case 2: //Page number 3
                return VosTrajets.newInstance();
            case 3: //Page number 4
                return PublierTrajet.newInstance();
            case 4: //Page number 5
                return MonCompte.newInstance();
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: //Page number 1
                return "Liste des trajets";
            case 1: //Page number 2
                return "Rechercher un trajet";
            case 2: //Page number 3
                return "Vos trajets";
            case 3: //Page number 3
                return "Publier un trajet";
            case 4: //Page number 3
                return "Mon compte";
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        // Show 5 total pages.
        return 5;
    }
}