package com.joseluishdz.eartquake.app.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.joseluishdz.eartquake.app.R;
import com.joseluishdz.eartquake.app.models.FeatureModel;
import com.joseluishdz.eartquake.app.utils.EnumUtils;
import com.joseluishdz.eartquake.app.views.SingleActivity;
import com.joseluishdz.eartquake.app.wrappers.EarthquakeWrapper;

/**
 * Created by drac94 on 8/23/15.
 */
public class EarthquakeAdapter extends ArrayAdapter<FeatureModel> {

    private Activity activity = null;

    public EarthquakeAdapter(Activity activity) {
            super(activity, 0);
            this.activity = activity;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final FeatureModel eq = getItem(position);
            EarthquakeWrapper wrapper;

            if (convertView==null){
                LayoutInflater mInflater = activity.getLayoutInflater();
                convertView = mInflater.inflate(R.layout.item_list_earthquake,parent, false);
                wrapper = new EarthquakeWrapper(convertView);
                convertView.setTag(wrapper);
            }else{
                wrapper = (EarthquakeWrapper)convertView.getTag();
            }

            int mag = (int) Math.floor(eq.getProperties().getMagnitude());

            wrapper.getViewColor().setBackgroundColor(activity.getResources().getColor(EnumUtils.colors.get(mag)));

            wrapper.getTwPlace().setText(eq.getProperties().getPlace());
            wrapper.getTwMagnitude().setText(String.valueOf(eq.getProperties().getMagnitude()));

            convertView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    Intent i = new Intent(activity, SingleActivity.class);
                    i.putExtra(SingleActivity.TAG_FEATURE, eq.getId());
                    activity.startActivity(i);
                }
            });

            return convertView;
        }
}

