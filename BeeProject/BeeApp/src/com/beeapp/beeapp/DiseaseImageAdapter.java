package com.beeapp.beeapp;

import java.util.List;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DiseaseImageAdapter extends ArrayAdapter<JSONObject> {
	
	Context context;
	List<JSONObject> list;

	public DiseaseImageAdapter(Context context, int resource, List<JSONObject> list) {
		super(context, resource, list);
		this.context = context;
		this.list = list;
	}

	@Override
	  public View getView(int position, View convertView, ViewGroup parent) {
	    LayoutInflater inflater = (LayoutInflater) context
	        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View rowView = inflater.inflate(R.layout.disease_list_layout, parent, false);
	    TextView diseaseName = (TextView) rowView.findViewById(R.id.diseaseName);
	    TextView diseaseBlurb = (TextView) rowView.findViewById(R.id.diseaseBlurb);
	    ImageView imageView = (ImageView) rowView.findViewById(R.id.diseaseImage);
	    int i = (position % 8) + 1;
	    int res = R.drawable.d1;
	    switch (i) {
		case 2:
			res = R.drawable.d2;
			break;
		case 3:
			res = R.drawable.d3;
			break;
		case 4:
			res = R.drawable.d4;
			break;
		case 5:
			res = R.drawable.d5;
			break;
		case 6:
			res = R.drawable.d6;
			break;
		case 7:
			res = R.drawable.d7;
			break;
		case 8:
			res = R.drawable.d8;
			break;
		case 9:
			res = R.drawable.d9;
			break;
		default:
			break;
		}
	    imageView.setImageResource(res);
	    try {
			diseaseName.setText(list.get(position).getString("name"));
			// TODO: get blurb somehow, or leave it
			diseaseBlurb.setText("Varroa mites are generally identified by their tiny red appea...");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	    return rowView;
	  }

}
