package com.example.rahul.asg4_rxs161630;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lvContact;
    Context context = MainActivity.this;
    ArrayList myList = new ArrayList();
    public final static String EXTRA_CONTACT_NAME= "com.example.rahul.asg4_rx2161630.NAME";
    public final static String EXTRA_CONTACT_PHONE= "com.example.rahul.asg4_rx2161630.PHONE";
    public final static String EXTRA_CONTACT_EMAIL= "com.example.rahul.asg4_rx2161630.EMAIL";
    String[] title = new String[]{
            "Title 1", "Title 2", "Title 3", "Title 4",
            "Title 5", "Title 6", "Title 7", "Title 8"
    };
    String[] desc = new String[]{
            "Desc 1", "Desc 2", "Desc 3", "Desc 4",
            "Desc 5", "Desc 6", "Desc 7", "Desc 8"
    };
    int[] img = new int[]{
            android.support.v7.appcompat.R.drawable.abc_ic_star_black_16dp, android.support.v7.appcompat.R.drawable.abc_ic_star_black_16dp,
            android.support.v7.appcompat.R.drawable.abc_ic_star_black_16dp, android.support.v7.appcompat.R.drawable.abc_ic_star_black_16dp,
            android.support.v7.appcompat.R.drawable.abc_ic_star_black_16dp, android.support.v7.appcompat.R.drawable.abc_ic_star_black_16dp,
            android.support.v7.appcompat.R.drawable.abc_ic_star_black_16dp, android.support.v7.appcompat.R.drawable.abc_ic_star_black_16dp
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lvContact = (ListView) findViewById(R.id.lv_contact);
        getData();
        lvContact.setAdapter(new ListViewAdapter(context,myList));
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ListData data = (ListData) lvContact.getItemAtPosition(position);
                Intent intent = new Intent(context,EditContact.class);
                Bundle bundle = new Bundle();
                bundle.putString(EXTRA_CONTACT_NAME,data.getName());
                bundle.putString(EXTRA_CONTACT_EMAIL,data.getEmail());
                bundle.putString(EXTRA_CONTACT_PHONE,data.getPhone());
                intent.putExtras(bundle);
                startActivity(intent);
                Toast.makeText(context, "Opening contact information", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getData()
    {
        for (int i = 0; i < title.length; i++) {
        // Create a new object for each list item
        ListData ld = new ListData();
        ld.setName(title[i]);
        ld.setEmail(title[i]);
        ld.setPhone(desc[i]);
        ld.setImgResId(img[i]);
        myList.add(ld);
    }

    }
}
