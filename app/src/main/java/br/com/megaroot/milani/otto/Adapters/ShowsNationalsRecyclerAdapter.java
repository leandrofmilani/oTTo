package br.com.megaroot.milani.otto.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.List;

import br.com.megaroot.milani.otto.Database.BD;
import br.com.megaroot.milani.otto.R;
import br.com.megaroot.milani.otto.Schedule.AppController;
import br.com.megaroot.milani.otto.Classes.Show;

/**
 * Created by milani on 05/11/15.
 */
public class ShowsNationalsRecyclerAdapter extends RecyclerView.Adapter<ShowsNationalsRecyclerAdapter.ShowsNationalsRecyclerViewHolder> {
    private LayoutInflater inflater;
    List<Show> showList = new ArrayList<Show>();

    public ShowsNationalsRecyclerAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        BD bd = new BD(context);
        List<Show> showN = bd.buscarShowsNacionais();

        for (int i = 0; i < showN.size(); i++) {
            Show show = new Show();
            show.setBand(showN.get(i).getBand());
            show.setDescription("Em " + showN.get(i).getCity() + ", " + showN.get(i).getPlace() + ". Dia " + showN.get(i).getDate());
            show.setThumbnailUrl(showN.get(i).getThumbnailUrl());
            showList.add(show);
        }

    }

    @Override
    public ShowsNationalsRecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_view_card_item, viewGroup, false);
        ShowsNationalsRecyclerViewHolder viewHolder = new ShowsNationalsRecyclerViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ShowsNationalsRecyclerViewHolder viewHolder, int position) {
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        final Show show = showList.get(position);
        viewHolder.tvBand.setText(show.getBand());
        viewHolder.tvDescription.setText(show.getDescription());
        viewHolder.imgThumbnail.setImageUrl(show.getThumbnailUrl(), imageLoader);
        viewHolder.imgThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), show.getBand(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return showList.size();
    }

    static class ShowsNationalsRecyclerViewHolder extends RecyclerView.ViewHolder {

        public TextView tvBand;
        public TextView tvDescription;
        public NetworkImageView imgThumbnail;

        public ShowsNationalsRecyclerViewHolder(View itemView) {
            super(itemView);
            imgThumbnail = (NetworkImageView)itemView.findViewById(R.id.img_thumbnail);
            tvBand = (TextView)itemView.findViewById(R.id.tv_band);
            tvDescription = (TextView)itemView.findViewById(R.id.tv_description);
        }
    }
}
