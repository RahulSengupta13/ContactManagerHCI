/*=============================================================================
 |       Author:  Rahul Sengupta
 |     Language:  Java
 |
 +-----------------------------------------------------------------------------
 |
 |  Description:  A Contact Manager for Android.
 |
 |        Input:  The program takes in the First Name, Last Name, Email and Mobile Number as inputs and saves them on a text file in the     |                External Memory.
 |
 |       Output:  A text file with maintained contacts in the External Memory.
 |
 |    Algorithm:  -
 |
 |   Required Features Not Included:  -
 |
 |   Known Bugs:  -
 |
 *===========================================================================*/


package com.example.rahul.asg4_rxs161630;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Console;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    ListView lvContact;
    Context context = MainActivity.this;
    ArrayList<ListData> myList;
    ListViewAdapter contactAdapter;
    FileManager lff = new FileManager(context);
    public final static String EXTRA_CONTACT_NAME = "com.example.rahul.asg4_rx2161630.NAME";
    public final static String EXTRA_CONTACT_LNAME = "com.example.rahul.asg4_rx2161630.LNAME";
    public final static String EXTRA_CONTACT_PHONE = "com.example.rahul.asg4_rx2161630.PHONE";
    public final static String EXTRA_CONTACT_EMAIL = "com.example.rahul.asg4_rx2161630.EMAIL";
    public final static String EXTRA_CONTACT_INDEX = "com.example.rahul.asg4_rx2161630.INDEX";
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
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set the Theme for the Application
        ThemeUtils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lvContact = (ListView) findViewById(R.id.lv_contact);

        //Read Contacts from file & sort according to First Name
        myList = lff.ReadFromFile();
        Collections.sort(myList, new Comparator<ListData>() {
            @Override
            public int compare(ListData o1, ListData o2) {
                return  o1.name.compareTo(o2.name);
            }
        });
        contactAdapter = new ListViewAdapter(context,myList);

        //Set the Adapter
        lvContact.setAdapter(contactAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,AddContact.class);
                intent.putExtra("EXTRA_TYPE","ADD");
                startActivityForResult(intent,0);
            }
        });

        //Define listener for listview
        lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                try
                {
                    final ListData data = (ListData) lvContact.getItemAtPosition(position);
                    PopupMenu popup = new PopupMenu(context, view);
                    popup.getMenuInflater().inflate(R.menu.listview_popup,popup.getMenu());
                    popup.show();
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId())
                            {
                                case R.id.call_contact:
                                    try{
                                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + data.getPhone()));
                                        startActivity(intent);
                                    }
                                    catch(SecurityException e)
                                    {
                                        e.printStackTrace();
                                    }
                                    break;
                                case R.id.email_contact:
                                    Intent email = new Intent(Intent.ACTION_SEND);
                                    email.setType("plain/text");
                                    email.putExtra(Intent.EXTRA_EMAIL, new String[] { data.getEmail() });
                                    if (email.resolveActivity(getPackageManager()) != null) {
                                        startActivity(Intent.createChooser(email, ""));
                                    }
                                    break;
                                case R.id.update_contact:
//                                    Intent intent = new Intent(context,EditContact.class);
                                    Intent intent = new Intent(context,AddContact.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("TYPE","EDIT");
                                    bundle.putString(EXTRA_CONTACT_NAME,data.getName());
                                    bundle.putString(EXTRA_CONTACT_EMAIL,data.getEmail());
                                    bundle.putString(EXTRA_CONTACT_PHONE,data.getPhone());
                                    bundle.putString(EXTRA_CONTACT_LNAME,data.getLName());
                                    bundle.putInt(EXTRA_CONTACT_INDEX,position);
                                    intent.putExtras(bundle);
                                    startActivityForResult(intent,1);
                                    break;
                            }
                            return true;
                        }
                    });
                }
                catch(Exception e){
                    e.printStackTrace();
                }
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
        else if(id == R.id.action_add_contact)
        {
            Intent intent = new Intent(context,AddContact.class);
            intent.putExtra("TYPE","ADD");
            startActivityForResult(intent,0);
        }
        else if(id == R.id.sort_first)
        {
            Collections.sort(myList, new Comparator<ListData>() {
                @Override
                public int compare(ListData o1, ListData o2) {
                    return  o1.name.compareTo(o2.name);
                }
            });
            contactAdapter.notifyDataSetChanged();
        }
        else if(id == R.id.sort_last)
        {
            Collections.sort(myList, new Comparator<ListData>() {
                @Override
                public int compare(ListData o1, ListData o2) {
                    return  o1.lname.compareTo(o2.lname);
                }
            });
            contactAdapter.notifyDataSetChanged();
        }
        else if(id == R.id.theme_dark_btn)
        {
            ThemeUtils.changeToTheme(this, ThemeUtils.THEME_DARK);
        }
        else if(id == R.id.theme_light_btn)
        {
            ThemeUtils.changeToTheme(this, ThemeUtils.THEME_WHITE);
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
                listData.setName(data.getStringExtra(MainActivity.EXTRA_CONTACT_NAME));
                listData.setPhone(data.getStringExtra(MainActivity.EXTRA_CONTACT_PHONE));
                listData.setEmail(data.getStringExtra(MainActivity.EXTRA_CONTACT_EMAIL));
                listData.setLName(data.getStringExtra(MainActivity.EXTRA_CONTACT_LNAME));
                listData.setImgResId(R.mipmap.contact_image);
                myList.add(listData);
                Collections.sort(myList, new Comparator<ListData>() {
                    @Override
                    public int compare(ListData o1, ListData o2) {
                        return  o1.name.compareTo(o2.name);
                    }
                });
                contactAdapter.notifyDataSetChanged();
                lff.WriteIntoFile(myList);
            }
        }
        if(requestCode == 1)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                String type = data.getStringExtra("TYPE");
                int index = data.getIntExtra(MainActivity.EXTRA_CONTACT_INDEX,-2);
                if(type.equals("DELETE"))
                {
                    myList.remove(index);
                    contactAdapter.notifyDataSetChanged();
                    lff.WriteIntoFile(myList);
                }
                else if(type.equals("EDIT"))
                {
                    myList.get(index).setName(data.getStringExtra(MainActivity.EXTRA_CONTACT_NAME));
                    myList.get(index).setLName(data.getStringExtra(MainActivity.EXTRA_CONTACT_LNAME));
                    myList.get(index).setPhone(data.getStringExtra(MainActivity.EXTRA_CONTACT_PHONE));
                    myList.get(index).setEmail(data.getStringExtra(MainActivity.EXTRA_CONTACT_EMAIL));
                    Collections.sort(myList, new Comparator<ListData>() {
                        @Override
                        public int compare(ListData o1, ListData o2) {
                            return  o1.name.compareTo(o2.name);
                        }
                    });
                    contactAdapter.notifyDataSetChanged();
                    lff.WriteIntoFile(myList);
                }
            }
        }
    }
}
