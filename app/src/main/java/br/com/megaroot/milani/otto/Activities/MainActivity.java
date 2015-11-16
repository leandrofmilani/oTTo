package br.com.megaroot.milani.otto.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.megaroot.milani.otto.Adapters.PagerAdapter;
import br.com.megaroot.milani.otto.Classes.Show;
import br.com.megaroot.milani.otto.Database.BD;
import br.com.megaroot.milani.otto.Preferences.PreferencesActivity;
import br.com.megaroot.milani.otto.R;
import br.com.megaroot.milani.otto.Schedule.AppController;
import br.com.megaroot.milani.otto.Schedule.ScheduleActivity;

import static br.com.megaroot.milani.otto.Classes.URL.url;

//CHAMA a classe que contem a url

public class MainActivity extends AppCompatActivity {

    // Need this to link with the Snackbar
    private CoordinatorLayout mCoordinator;
    //Need this to set the title of the app bar
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private FloatingActionButton mFab;
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ViewPager mPager;
    private PagerAdapter mAdapter;
    private TabLayout mTabLayout;
    private SliderLayout sliderShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCoordinator = (CoordinatorLayout) findViewById(R.id.root_coordinator);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mAdapter = new PagerAdapter(getSupportFragmentManager());
        mPager = (ViewPager) findViewById(R.id.view_pager);
        mPager.setAdapter(mAdapter);
        //Notice how the Tab Layout links with the Pager Adapter
        mTabLayout.setTabsFromPagerAdapter(mAdapter);

        //Notice how The Tab Layout adn View Pager object are linked
        mTabLayout.setupWithViewPager(mPager);
        mPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));


        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Notice how the Coordinator Layout object is used here
                //Snackbar.make(mCoordinator, "FAB Clicked", Snackbar.LENGTH_SHORT).setAction("DISMISS", null).show();
                startActivity(new Intent(MainActivity.this, ScheduleActivity.class));
            }
        });

        //Notice how the title is set on the Collapsing Toolbar Layout instead of the Toolbar
        //mCollapsingToolbarLayout.setTitle(getResources().getString(R.string.title_activity_fourth));
        mCollapsingToolbarLayout.setTitleEnabled(false);

        //testa se esta online
        if (isOnline()){
            JsonToDB();
        }else {
            alert("Verifique sua conexão com a Internet!");
        }

        //Add slider for homescreen
        SliderHome();

        //Load json to database

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
                //Toast.makeText(getApplicationContext(),error.getMessage(), Toast.LENGTH_SHORT).show();
                alert(error.getMessage());
                // hide the progress dialog
                // hidepDialog();
            }
        });
        AppController.getInstance().addToRequestQueue(showReq);
    }

    public boolean isOnline() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return manager.getActiveNetworkInfo() != null &&
                manager.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    private void alert(String erro){
        //Exibe msg de alerta
        AlertDialog.Builder ADBuilder = new AlertDialog.Builder(MainActivity.this);
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

    private void SliderHome(){
        sliderShow = (SliderLayout) findViewById(R.id.slider);
        BD bd = new BD(getApplicationContext());
        List<Show> showTodos = bd.buscarTodosShows();

        for (int i = 0; i < showTodos.size(); i++) {
            TextSliderView textSliderView = new TextSliderView(getApplicationContext());
            textSliderView
                    .description(showTodos.get(i).getBand())
                    .image(showTodos.get(i).getThumbnailUrl())
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView slider) {
                            Toast.makeText(getApplicationContext(), slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
                        }
                    });

            //add your extra information
            //textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", showTodos.get(i).getBand());

            sliderShow.addSlider(textSliderView);

        }
    }

    @Override
    protected void onStop() {
        sliderShow.stopAutoCycle();
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this, PreferencesActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
