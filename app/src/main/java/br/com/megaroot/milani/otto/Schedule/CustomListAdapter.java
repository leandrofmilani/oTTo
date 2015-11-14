package br.com.megaroot.milani.otto.Schedule;

/**
 * Created by milani on 22/10/15.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import br.com.megaroot.milani.otto.R;
import br.com.megaroot.milani.otto.Classes.Show;

public class CustomListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Show> showItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CustomListAdapter(Activity activity, List<Show> showItems) {
        this.activity = activity;
        this.showItems = showItems;
    }

    @Override
    public int getCount() {
        return showItems.size();
    }

    @Override
    public Object getItem(int location) {
        return showItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.thumbnail);
        TextView band = (TextView) convertView.findViewById(R.id.title);
        TextView city = (TextView) convertView.findViewById(R.id.city);
        TextView place = (TextView) convertView.findViewById(R.id.place);
        TextView date = (TextView) convertView.findViewById(R.id.date);

        // pega os dados do show da linha atual
        Show m = showItems.get(position);

        // imagem
        thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);

        // banda
        band.setText(m.getBand());

        // cidade
        city.setText(String.valueOf(m.getCity()));

        // lugar
        place.setText(String.valueOf(m.getPlace()));

        // data
        date.setText(String.valueOf(m.getDate()));

        return convertView;
    }

}