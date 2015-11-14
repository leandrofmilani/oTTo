package br.com.megaroot.milani.otto.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import br.com.megaroot.milani.otto.Fragments.ShowsInternationalsFragment;
import br.com.megaroot.milani.otto.Fragments.ShowsNationalsFragment;

/**
 * Created by milani on 05/11/15.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment myFragment = null;
        if(position == 0){
            myFragment = ShowsNationalsFragment.newInstance(position);
        }
        if (position == 1){
            myFragment = ShowsInternationalsFragment.newInstance(position);
        }

        return myFragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String titulo = "";
        switch (position){
            case 0:
                titulo = "Nacionais";
                break;
            case 1:
                titulo = "Internacionais";
        }
        return titulo;
    }
}