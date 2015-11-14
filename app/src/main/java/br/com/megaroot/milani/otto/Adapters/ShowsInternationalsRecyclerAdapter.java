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
public class ShowsInternationalsRecyclerAdapter extends RecyclerView.Adapter<ShowsInternationalsRecyclerAdapter.ShowsInternationalsRecyclerViewHolder> {
    private LayoutInflater inflater;
    List<Show> showList = new ArrayList<Show>();

    public ShowsInternationalsRecyclerAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        BD bd = new BD(context);
        List<Show> showI = bd.buscarShowsInternacionais();

        for (int i = 0; i < showI.size(); i++) {
            Show show = new Show();
            show.setBand(showI.get(i).getBand());
            show.setDescription("Em " + showI.get(i).getCity() + ", " + showI.get(i).getPlace() + ". Dia " + showI.get(i).getDate());
            show.setThumbnailUrl(showI.get(i).getThumbnailUrl());
            showList.add(show);
        }

    }

    @Override
    public ShowsInternationalsRecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_view_card_item, viewGroup, false);
        ShowsInternationalsRecyclerViewHolder viewHolder = new ShowsInternationalsRecyclerViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ShowsInternationalsRecyclerViewHolder viewHolder, int position) {
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

    static class ShowsInternationalsRecyclerViewHolder extends RecyclerView.ViewHolder {

        public TextView tvBand;
        public TextView tvDescription;
        public NetworkImageView imgThumbnail;

        public ShowsInternationalsRecyclerViewHolder(View itemView) {
            super(itemView);
            imgThumbnail = (NetworkImageView)itemView.findViewById(R.id.img_thumbnail);
            tvBand = (TextView)itemView.findViewById(R.id.tv_band);
            tvDescription = (TextView)itemView.findViewById(R.id.tv_description);
        }
    }
}
