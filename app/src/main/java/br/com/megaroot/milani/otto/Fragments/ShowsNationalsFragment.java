package br.com.megaroot.milani.otto.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.megaroot.milani.otto.Adapters.ShowsNationalsRecyclerAdapter;

/**
 * Created by milani on 05/11/15.
 */
public class ShowsNationalsFragment extends Fragment {
    public static final String ARG_PAGE = "arg_page";

    public ShowsNationalsFragment() {

    }

    public static ShowsNationalsFragment newInstance(int pageNumber) {
        ShowsNationalsFragment myFragment = new ShowsNationalsFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARG_PAGE, pageNumber + 1);
        myFragment.setArguments(arguments);
        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        int pageNumber = arguments.getInt(ARG_PAGE);
        RecyclerView recyclerView = new RecyclerView(getActivity());
        recyclerView.setAdapter(new ShowsNationalsRecyclerAdapter(getActivity()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }
}