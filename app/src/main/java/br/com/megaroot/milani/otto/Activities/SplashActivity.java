package br.com.megaroot.milani.otto.Activities;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import br.com.megaroot.milani.otto.Classes.Show;
import br.com.megaroot.milani.otto.Database.BD;
import br.com.megaroot.milani.otto.R;
import br.com.megaroot.milani.otto.Schedule.AppController;
import br.com.megaroot.milani.otto.Schedule.ScheduleActivity;

import static br.com.megaroot.milani.otto.Classes.URL.url;

public class SplashActivity extends AppCompatActivity {

        private Handler splashHandler = new Handler();
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Runnable r = new Runnable(){
                public void run(){
                    Intent main = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(main);
                    finish();
                }
            };
            setContentView(R.layout.activity_splash);
            if(isNetworkAvailable()) {
                //If network is ok than execute JsonToDB
                JsonToDB();
                //After it do the delay
                splashHandler.postDelayed(r, 3000);
            }else {
                alert("Verifique sua conexão com a Internet!");
            }
        }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    private void JsonToDB() {
        final List<Show> showList = new ArrayList<Show>();
        JsonArrayRequest showReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        BD bd = new BD(getApplicationContext());

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject j_object = response.getJSONObject(i);
                                Show show = new Show();
                                show.setBand(j_object.getString("band"));
                                show.setThumbnailUrl(j_object.getString("image"));
                                show.setDate(j_object.getString("date"));
                                show.setCity(j_object.getString("city"));
                                show.setPlace(j_object.getString("place"));
                                show.setCountry(j_object.getInt("country"));
                                show.setCode(j_object.getString("code"));

                                showList.add(show);

                                //Verifica se ja tem o cod no banco caso nao tenha adiciona o objeto
                                Boolean verifGravado = bd.verificaShowGravado(j_object.getString("code"));
                                if (!verifGravado){
                                    bd.inserirShow(show);
                                    Notification("Novo evento adicionado",j_object.getString("band"));
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            //Toast.makeText(getApplicationContext(),"Error: " + e.getMessage(),
                            //Toast.LENGTH_LONG).show();
                            Log.e("LOG", e.getMessage());
                        }
                        //hidepDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TAG", "Error: " + error.getMessage());
                Log.e("LOG", error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(),Toast.LENGTH_LONG).show();
                //alert(error.getMessage());
                // hide the progress dialog
                // hidepDialog();
            }
        });
        AppController.getInstance().addToRequestQueue(showReq);
    }

    private void alert(String erro){
        //Exibe msg de alerta
        AlertDialog.Builder ADBuilder = new AlertDialog.Builder(SplashActivity.this);
        //ADBuilder.setIcon(R.drawable.ic_dialog_info);
        ADBuilder.setTitle("Infelizmente ocorreu um erro!");
        ADBuilder.setMessage(erro);
        ADBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        ADBuilder.show();
    }

    private void Notification(String title, String text){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.calendar)
                        .setContentTitle(title)
                        .setContentText(text);
        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, ScheduleActivity.class);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(ScheduleActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(1, mBuilder.build());
    }

}
