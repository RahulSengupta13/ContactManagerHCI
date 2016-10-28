package com.example.rahul.asg4_rxs161630;

import android.app.Activity;
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
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

//Main Activity
public class MainActivity extends AppCompatActivity {

    ListView lvContact;
    Context context = MainActivity.this;
    ArrayList<ListData> myList;
    ListViewAdapter contactAdapter;
    FileManager lff = new FileManager(context);
    public final static String EXTRA_CONTACT_NAME = "com.example.rahul.asg4_rx2161630.NAME";
    public final static String EXTRA_CONTACT_PHONE = "com.example.rahul.asg4_rx2161630.PHONE";
    public final static String EXTRA_CONTACT_EMAIL = "com.example.rahul.asg4_rx2161630.EMAIL";

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(context,"Activity Restarted", Toast.LENGTH_SHORT).show();
        contactAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(context,"Application shutting down", Toast.LENGTH_SHORT).show();
        lff.WriteIntoFile(myList);
    }

    @Override
    protected void onPause() {
        super.onPause();
        lff.WriteIntoFile(myList);
    }
    //changes
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lvContact = (ListView) findViewById(R.id.lv_contact);
        //getData();=
        myList = lff.ReadFromFile();
        contactAdapter = new ListViewAdapter(context,myList);
        lvContact.setAdapter(contactAdapter);
        if(myList.size() == 0)
            Toast.makeText(context,"No contacts found", Toast.LENGTH_SHORT).show();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        /*lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ListData data = (ListData) lvContact.getItemAtPosition(position);
                Intent intent = new Intent(context,AddContact.class);
                Bundle bundle = new Bundle();
                bundle.putString(EXTRA_CONTACT_NAME,data.getName());
                bundle.putString(EXTRA_CONTACT_EMAIL,data.getEmail());
                bundle.putString(EXTRA_CONTACT_PHONE,data.getPhone());
                bundle.putString("EXTRA_TYPE","EDIT");
                intent.putExtras(bundle);
                startActivity(intent);
                Toast.makeText(context, "Opening contact information", Toast.LENGTH_SHORT).show();
            }
        });*/
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
        else if(id == R.id.action_add_contact)
        {
            Intent intent = new Intent(context,AddContact.class);
            intent.putExtra("EXTRA_TYPE","ADD");
            startActivityForResult(intent,0);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                ListData listData = new ListData();
                listData.setName(data.getStringExtra("Name"));
                listData.setPhone(data.getStringExtra("Phone"));
                listData.setEmail(data.getStringExtra("Email"));
                Toast.makeText(context,data.getStringExtra("Name"),Toast.LENGTH_SHORT).show();
                myList.add(listData);
                contactAdapter.notifyDataSetChanged();
                lff.WriteIntoFile(myList);
            }
        }
    }
}
