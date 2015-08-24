package com.joseluishdz.eartquake.app.views;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.*;
import com.google.gson.Gson;
import com.joseluishdz.eartquake.app.R;
import com.joseluishdz.eartquake.app.adapters.EarthquakeAdapter;
import com.joseluishdz.eartquake.app.database.EqDAO;
import com.joseluishdz.eartquake.app.models.FeatureCollectionModel;
import com.joseluishdz.eartquake.app.models.FeatureModel;
import com.squareup.okhttp.*;

import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();
    private ListView listEarthquakes;
    private EarthquakeAdapter ea;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private GoogleMap map;
    FeatureCollectionModel fcm;
    private MapFragment mapFragment;
    private int type;
    private Menu menu;
    private BitmapDescriptor bm;
    private EqDAO eqDAO;
    private View coordinatorLayoutView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        coordinatorLayoutView = findViewById(R.id.snackbarPosition);
        listEarthquakes = (ListView) findViewById(R.id.list_earthquakes);
        ea = new EarthquakeAdapter(this);
        eqDAO = new EqDAO(this);

        mapFragment = ((MapFragment) getFragmentManager().findFragmentById(
                R.id.map));

        map = mapFragment.getMap();

        mapFragment.getView().setVisibility(View.GONE);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.blue, R.color.yellow, R.color.red);
        mSwipeRefreshLayout.setDistanceToTriggerSync(200);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });

        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });
        getData();
    }

    private void getData() {
        Request request = new Request.Builder()
                .url("http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson")
                .build();


        Call call = client.newCall(request);

        call.enqueue(new Callback() {

            @Override
            public void onFailure(Request request, IOException e) {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Snackbar
                                .make(coordinatorLayoutView, R.string.network_error, Snackbar.LENGTH_INDEFINITE)
                                .setAction(R.string.snackbar_try_again, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mSwipeRefreshLayout.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                mSwipeRefreshLayout.setRefreshing(true);
                                            }
                                        });
                                        getData();
                                    }
                                })
                                .show();

                        ea.clear();
                        fcm = new FeatureCollectionModel();
                        fcm.setFeatures(eqDAO.getAllEq());

                        if (fcm != null && fcm.getFeatures().size() > 0) {
                            ea.addAll(fcm.getFeatures());
                            listEarthquakes.setAdapter(ea);
                            fillMap();
                        }


                        mSwipeRefreshLayout.post(new Runnable() {
                            @Override
                            public void run() {
                                mSwipeRefreshLayout.setRefreshing(false);
                            }
                        });
                    }
                });
                e.printStackTrace();
            }

            @Override public void onResponse(Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                Headers responseHeaders = response.headers();
                for (int i = 0; i < responseHeaders.size(); i++) {
                    System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }

                fcm = gson.fromJson(response.body().charStream(), FeatureCollectionModel.class);

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        ea.clear();

                        if(fcm != null && !fcm.getFeatures().isEmpty()) {
                            eqDAO.deleteAllEq();
                            eqDAO.insertAllEq(fcm.getFeatures());
                        }

                        ea.addAll(fcm.getFeatures());
                        listEarthquakes.setAdapter(ea);

                        getSupportActionBar().setTitle(fcm.getMetadata().getTitle());
                        fillMap();

                        mSwipeRefreshLayout.post(new Runnable() {
                            @Override
                            public void run() {
                                mSwipeRefreshLayout.setRefreshing(false);
                            }
                        });
                    }
                });

            }
        });

    }

    public void fillMap() {
        if(fcm != null && fcm.getFeatures() != null & fcm.getFeatures().size() > 0) {
            LatLng firstLocation = new LatLng(fcm.getFeatures().get(0).getGeometry().getCoordinates()[1], fcm.getFeatures().get(0).getGeometry().getCoordinates()[0]);
            for (FeatureModel fm : fcm.getFeatures()) {
                LatLng location = new LatLng(fm.getGeometry().getCoordinates()[1],
                        fm.getGeometry().getCoordinates()[0]);

                int mag = (int) Math.floor(fm.getProperties().getMagnitude());
                switch (mag) {
                    case 0:
                        bm = BitmapDescriptorFactory
                                .fromResource(R.drawable.pin_map_1);
                        break;
                    case 1:
                        bm = BitmapDescriptorFactory
                                .fromResource(R.drawable.pin_map_2);
                        break;
                    case 2:
                        bm = BitmapDescriptorFactory
                                .fromResource(R.drawable.pin_map_3);
                        break;
                    case 3:
                        bm = BitmapDescriptorFactory
                                .fromResource(R.drawable.pin_map_4);
                        break;
                    case 4:
                        bm = BitmapDescriptorFactory
                                .fromResource(R.drawable.pin_map_5);
                        break;
                    case 5:
                        bm = BitmapDescriptorFactory
                                .fromResource(R.drawable.pin_map_6);
                        break;
                    case 6:
                        bm = BitmapDescriptorFactory
                                .fromResource(R.drawable.pin_map_7);
                        break;
                    case 7:
                        bm = BitmapDescriptorFactory
                                .fromResource(R.drawable.pin_map_8);
                        break;
                    case 8:
                        bm = BitmapDescriptorFactory
                                .fromResource(R.drawable.pin_map_9);
                        break;
                    case 9:
                        bm = BitmapDescriptorFactory
                                .fromResource(R.drawable.pin_map_10);
                        break;

                }
                map.addMarker(new MarkerOptions()
                        .position(location)
                        .title(String.valueOf(fm.getProperties().getMagnitude()))
                        .icon(bm));
            }
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    firstLocation, 1));
        }
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_map) {
            if(type == 0) {
                type=1; //switch to map mode
                mapFragment.getView().setVisibility(View.VISIBLE);
                listEarthquakes.setVisibility(View.GONE);
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.list));
            } else {
                type=0; //switch to list mode
                mapFragment.getView().setVisibility(View.GONE);
                listEarthquakes.setVisibility(View.VISIBLE);
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.map));
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
