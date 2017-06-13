package com.chris.tatusafety;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    ListView list;
    ArrayList <Report> data;
    CustomListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        list = (ListView) findViewById(R.id.historyList);
        Database db = new Database(this);
        data = db.getAllRecords();
        adapter = new CustomListAdapter(this,data);
        list.setAdapter(adapter);
        registerForContextMenu(list);
    }
    //Context menu.

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add("Share");
        menu.add("Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;
        Report x = data.get(position);

        if (item.getTitle().equals("Share"))
        {
            String details = "Road is "+ x.getRoad() +"\n and Sacco is "+ x.getSacco() +"\n on date "+ x.getDate();
            Intent share = new Intent(Intent.ACTION_SEND);
            share.putExtra(Intent.EXTRA_TEXT,details);
            share.setType("text/plain");
            startActivity(share);
        }
        else if (item.getTitle().equals("Delete"))
        {
            Database db = new Database(this);
            db.deleteRecord(x.getId());
            data.remove(position);
            adapter.notifyDataSetChanged();
        }

        return super.onContextItemSelected(item);
    }
}