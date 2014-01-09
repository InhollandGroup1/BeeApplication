package com.beeapp.beeapp;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AndroidListActivity extends ListActivity {

 @Override
 protected void onCreate(Bundle savedInstanceState) {
  // TODO Auto-generated method stub
  super.onCreate(savedInstanceState);
  
  setListAdapter(new ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_1, HEALTHCOORD));
  getListView().setTextFilterEnabled(true);
 }

 static final String[] HEALTHCOORD = new String[] {
 "health@coordinator.com"
 };

 @Override
 protected void onListItemClick(ListView l, View v, int position, long id) {
  // TODO Auto-generated method stub
  super.onListItemClick(l, v, position, id);
  
    Intent intent = new Intent();
    Bundle bundle = new Bundle();
  
    bundle.putString("healthcoordinator", l.getItemAtPosition(position).toString());
    intent.putExtras(bundle);
    setResult(RESULT_OK, intent);
    finish();
 }

}