package com.example.rahul.asg4_rxs161630;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class EditContact extends AppCompatActivity {

    EditText Name,Email,Phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        String name = getIntent().getExtras().getString(MainActivity.EXTRA_CONTACT_NAME);
        String email = getIntent().getExtras().getString(MainActivity.EXTRA_CONTACT_EMAIL);
        String phone = getIntent().getExtras().getString(MainActivity.EXTRA_CONTACT_PHONE);
        Name = (EditText)findViewById(R.id.ed_name);
        Email = (EditText)findViewById(R.id.ed_email);
        Phone = (EditText)findViewById(R.id.ed_phone);
        Name.setText(name);
        Email.setText(email);
        Phone.setText(phone);
    }
}
