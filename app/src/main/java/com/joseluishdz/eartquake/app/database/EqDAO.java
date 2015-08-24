/**
 * 
 */
package com.joseluishdz.eartquake.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.joseluishdz.eartquake.app.models.FeatureModel;
import com.joseluishdz.eartquake.app.models.GeometryModel;
import com.joseluishdz.eartquake.app.models.PropertiesModel;

import java.util.ArrayList;

public class EqDAO {

	private DBAdapter dbAdapter;

	public static final String TABLE_NAME = "earthquake";
	public static final String ID_EQ = "eq_id";
	public static final String PLACE_EQ = "eq_place";
	public static final String DATE_EQ = "eq_date";
	public static final String TITLE_EQ = "eq_title";
	public static final String MAG_EQ = "eq_mag";
	public static final String LONG_EQ = "long_mag";
	public static final String LAT_EQ = "lat_mag";
	public static final String DEPTH_EQ = "depth_mag";

	public EqDAO(Context ctx) {
		dbAdapter = new DBAdapter(ctx);
	}
	
	public boolean insertEq(FeatureModel eq){
		if (!alreadyInBD(eq)){
			dbAdapter.beginTransaction();
			ContentValues cValues = new ContentValues();
			cValues.put(ID_EQ, eq.getId());
			cValues.put(PLACE_EQ, eq.getProperties().getPlace());
			cValues.put(DATE_EQ, eq.getProperties().getTime());
			cValues.put(TITLE_EQ, eq.getProperties().getTitle());
			cValues.put(MAG_EQ, eq.getProperties().getMagnitude());
			cValues.put(LONG_EQ, eq.getGeometry().getCoordinates()[1]);
			cValues.put(LAT_EQ, eq.getGeometry().getCoordinates()[0]);
			cValues.put(DEPTH_EQ, eq.getGeometry().getCoordinates()[2]);
			long id = dbAdapter.insert(TABLE_NAME, cValues);
			dbAdapter.setTransactionSuccessful();
            return id != -1;
		}
		return false;
	}

	public void insertAllEq(ArrayList<FeatureModel> allEq) {
		dbAdapter.beginTransaction();
		for(FeatureModel eq : allEq) {
			ContentValues cValues = new ContentValues();
			cValues.put(ID_EQ, eq.getId());
			cValues.put(PLACE_EQ, eq.getProperties().getPlace());
			cValues.put(DATE_EQ, eq.getProperties().getTime());
			cValues.put(TITLE_EQ, eq.getProperties().getTitle());
			cValues.put(MAG_EQ, eq.getProperties().getMagnitude());
			cValues.put(LONG_EQ, eq.getGeometry().getCoordinates()[1]);
			cValues.put(LAT_EQ, eq.getGeometry().getCoordinates()[0]);
			cValues.put(DEPTH_EQ, eq.getGeometry().getCoordinates()[2]);
			dbAdapter.insert(TABLE_NAME, cValues);
		}
		dbAdapter.setTransactionSuccessful();
	}
	
	public void deleteAllEq(){
		dbAdapter.deleteAll(TABLE_NAME);
	}

	public FeatureModel getEq(String id){
		FeatureModel eq = new FeatureModel();
		String[] fields = {ID_EQ, PLACE_EQ, DATE_EQ, TITLE_EQ, MAG_EQ, LONG_EQ, LAT_EQ, DEPTH_EQ};
		String conditional = ID_EQ + " = '" + id + "'";

		dbAdapter.open();
		Cursor cursor = dbAdapter.getData(TABLE_NAME, fields, conditional, null);

		int _id_eq = cursor.getColumnIndex(ID_EQ);
		int _place_eq = cursor.getColumnIndex(PLACE_EQ);
		int _date_eq = cursor.getColumnIndex(DATE_EQ);
		int _title_eq = cursor.getColumnIndex(TITLE_EQ);
		int _mag_eq = cursor.getColumnIndex(MAG_EQ);
		int _long_eq = cursor.getColumnIndex(LONG_EQ);
		int _lat_eq = cursor.getColumnIndex(LAT_EQ);
		int _depth_eq = cursor.getColumnIndex(DEPTH_EQ);
		cursor.moveToFirst();

		for (int i=0;i<cursor.getCount(); i++){
			eq.setId(cursor.getString(_id_eq));
			PropertiesModel pm = new PropertiesModel();
			GeometryModel gm = new GeometryModel();
			pm.setPlace(cursor.getString(_place_eq));
			pm.setTime(cursor.getLong(_date_eq));
			pm.setTitle(cursor.getString(_title_eq));
			pm.setMagnitude(cursor.getDouble(_mag_eq));
			gm.setCoordinates(new double[]{cursor.getDouble(_lat_eq), cursor.getDouble(_long_eq), cursor.getDouble(_depth_eq)});
			eq.setProperties(pm);
			eq.setGeometry(gm);
			cursor.moveToNext();
		}
		cursor.close();
		dbAdapter.close();
		return eq;
	}

	public ArrayList<FeatureModel> getAllEq() {
		ArrayList<FeatureModel> sections = new ArrayList<FeatureModel>();
		String[] fields = {ID_EQ, PLACE_EQ, DATE_EQ, TITLE_EQ, MAG_EQ, LONG_EQ, LAT_EQ, DEPTH_EQ};

		dbAdapter.open();
		Cursor cursor = dbAdapter.getData(TABLE_NAME, fields, null);

		int _id_eq = cursor.getColumnIndex(ID_EQ);
		int _place_eq = cursor.getColumnIndex(PLACE_EQ);
		int _date_eq = cursor.getColumnIndex(DATE_EQ);
		int _title_eq = cursor.getColumnIndex(TITLE_EQ);
		int _mag_eq = cursor.getColumnIndex(MAG_EQ);
		int _long_eq = cursor.getColumnIndex(LONG_EQ);
		int _lat_eq = cursor.getColumnIndex(LAT_EQ);
		int _depth_eq = cursor.getColumnIndex(DEPTH_EQ);
		cursor.moveToFirst();

		for (int i=0;i<cursor.getCount(); i++){
			FeatureModel a = new FeatureModel();
			a.setId(cursor.getString(_id_eq));
			PropertiesModel pm = new PropertiesModel();
			GeometryModel gm = new GeometryModel();
			pm.setPlace(cursor.getString(_place_eq));
			pm.setTime(cursor.getLong(_date_eq));
			pm.setTitle(cursor.getString(_title_eq));
			pm.setMagnitude(cursor.getDouble(_mag_eq));
			gm.setCoordinates(new double[]{cursor.getDouble(_lat_eq), cursor.getDouble(_long_eq), cursor.getDouble(_depth_eq)});
			a.setProperties(pm);
			a.setGeometry(gm);
			sections.add(a);
			cursor.moveToNext();
		}
		cursor.close();
		dbAdapter.close();
		return sections;
	}
	
	private boolean alreadyInBD(FeatureModel eq){
		boolean exists = false;
		String[] fields = {ID_EQ};
		String conditional = ID_EQ + " = '" + eq.getId() + "'";
		dbAdapter.open();
		Cursor cursor = dbAdapter.getData(TABLE_NAME, fields, conditional);
		if (cursor.getCount()>0)
			exists = true;
		cursor.close();
		dbAdapter.close();
		return exists;
	}


}
