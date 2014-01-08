package com.beeapp.beeapp;

import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import android.widget.ArrayAdapter;

public class DiseaseDetailAdapter extends ArrayAdapter<JSONObject> {

	public DiseaseDetailAdapter(Context context, int resource,
			List<JSONObject> objects) {
		super(context, resource, objects);
		
	}

}
