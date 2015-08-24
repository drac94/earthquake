package com.joseluishdz.eartquake.app.wrappers;

import android.view.View;
import android.widget.TextView;
import com.joseluishdz.eartquake.app.R;

/**
 * Created by drac94 on 8/23/15.
 */
public class EarthquakeWrapper {

    private View base = null;
    private View color;
    private TextView place;
    private TextView magnitude;

    public EarthquakeWrapper(View base) {
        this.base = base;
    }

    public View getViewColor() {
        if (color==null)
            color=(View)base.findViewById(R.id.view_color);
        return color;
    }

    public TextView getTwPlace() {
        if (place==null)
            place=(TextView)base.findViewById(R.id.tw_place);
        return place;
    }

    public TextView getTwMagnitude() {
        if (magnitude==null)
            magnitude=(TextView)base.findViewById(R.id.tw_magnitude);
        return magnitude;
    }

}
