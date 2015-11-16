package br.com.megaroot.milani.otto.Schedule;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.megaroot.milani.otto.R;
import br.com.megaroot.milani.otto.Classes.Show;

import static br.com.megaroot.milani.otto.Classes.URL.url;

//CHAMA a classe que contem a url

public class ScheduleActivity extends Activity {
    // Log tag
    private static final String TAG = ScheduleActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private List<Show> showList = new ArrayList<Show>();
    private ListView listView;
    private CustomListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduler);

        listView = (ListView) findViewById(R.id.list);
        adapter = new CustomListAdapter(this, showList);
        listView.setAdapter(adapter);

        pDialog = new ProgressDialog(this);
        // Mostra o carregando antes de fazer a conexao http
        pDialog.setMessage("Carregando...");
        pDialog.show();

        /*// muda a cor do background
        getActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#1b1b1b")));*/

        // Criando o objeto volley de requisicao
        JsonArrayRequest showReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        hidePDialog();

                        // Setando o que veio do json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                Show show = new Show();
                                show.setBand(obj.getString("band"));
                                show.setThumbnailUrl(obj.getString("image"));
                                show.setCity(obj.getString("city"));
                                show.setDate(obj.getString("date"));
                                show.setPlace(obj.getString("place"));

                                // adiciona o show para o array de shows
                                showList.add(show);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        //avisa o list adpter sobre os dados que recebeu e atualiza
                        // a list view com o dados
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();
                Toast.makeText(getApplicationContext(), error.getMessage(),Toast.LENGTH_LONG).show();
                finish();

            }
        });

        //Adiciona o onitemclick para a lista como uma action
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Depois sera usado para passar o objeto  para a activity dos eventos
                Toast.makeText(getApplicationContext(), showList.get(position).getBand(),
                        Toast.LENGTH_LONG).show();
                //Intent intent = new Intent(SchedulerActivity.this, MainActivity.class);
                //startActivity(intent);
            }
        });

        // Adiciona a requisicao na fila
        AppController.getInstance().addToRequestQueue(showReq);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}
