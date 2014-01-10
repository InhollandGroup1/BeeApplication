package com.beeapp.beeapp;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class DiseaseDetailAdapter extends ArrayAdapter<JSONObject> {

	Context context;
	List<JSONObject> list;

	public DiseaseDetailAdapter(Context context, int resource,
			List<JSONObject> list) {
		super(context, resource, list);
		this.context = context;
		this.list = list;
	}
	
	@Override
	  public View getView(int position, View convertView, ViewGroup parent) {
	    LayoutInflater inflater = (LayoutInflater) context
	        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View rowView = inflater.inflate(R.layout.detail_list_layout, parent, false);
	    
	    TextView diseaseName = (TextView) rowView.findViewById(R.id.textView1);
	    TextView diseaseBlurb = (TextView) rowView.findViewById(R.id.textView2);
	    try {
			diseaseName.setText("Treatment #" + position);
			diseaseBlurb.setText(list.get(position).getString("description"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	    return rowView;
	  }

}