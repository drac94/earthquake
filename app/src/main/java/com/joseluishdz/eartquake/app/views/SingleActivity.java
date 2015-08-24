package com.joseluishdz.eartquake.app.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.joseluishdz.eartquake.app.R;
import com.joseluishdz.eartquake.app.database.EqDAO;
import com.joseluishdz.eartquake.app.models.FeatureModel;
import com.joseluishdz.eartquake.app.utils.ConversionUtils;


public class SingleActivity extends AppCompatActivity {

    public static final String TAG_FEATURE = "tag_feature" ;
    private Toolbar toolbar;
    private GoogleMap map;
    private MapFragment mapFragment;
    private BitmapDescriptor bm;
    private FeatureModel fm;
    private TextView place, magnitude, date, depth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        place = (TextView) findViewById(R.id.tw_place);
        magnitude = (TextView) findViewById(R.id.tw_mag);
        date = (TextView) findViewById(R.id.tw_date);
        depth = (TextView) findViewById(R.id.tw_depth);

        EqDAO eqDAO = new EqDAO(this);

        String id = getIntent().getStringExtra(TAG_FEATURE);
        fm = eqDAO.getEq(id);

        getSupportActionBar().setTitle(fm.getProperties().getTitle());

        place.setText(fm.getProperties().getPlace());
        magnitude.setText(String.valueOf(fm.getProperties().getMagnitude()));
        date.setText(ConversionUtils.getCompleteDateFromTimestamp(fm.getProperties().getTime()));
        depth.setText(String.format(getString(R.string.label_depth), fm.getGeometry().getCoordinates()[2]));

        mapFragment = ((MapFragment) getFragmentManager().findFragmentById(
                R.id.map));

        map = mapFragment.getMap();

        fillMap();
    }

    public void fillMap() {
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
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                location, 5));
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
