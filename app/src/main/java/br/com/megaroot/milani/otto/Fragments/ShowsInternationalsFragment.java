package br.com.megaroot.milani.otto.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.megaroot.milani.otto.Adapters.ShowsInternationalsRecyclerAdapter;

/**
 * Created by milani on 05/11/15.
 */
public class ShowsInternationalsFragment extends Fragment {
    public static final String ARG_PAGE = "arg_page";

    public ShowsInternationalsFragment() {

    }

    public static ShowsInternationalsFragment newInstance(int pageNumber) {
        ShowsInternationalsFragment showsInternationalsFragment = new ShowsInternationalsFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARG_PAGE, pageNumber + 1);
        showsInternationalsFragment.setArguments(arguments);
        return showsInternationalsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        int pageNumber = arguments.getInt(ARG_PAGE);
        RecyclerView recyclerView = new RecyclerView(getActivity());
        recyclerView.setAdapter(new ShowsInternationalsRecyclerAdapter(getActivity()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }
}