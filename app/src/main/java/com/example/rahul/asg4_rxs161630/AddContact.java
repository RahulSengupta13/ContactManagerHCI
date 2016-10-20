package com.example.rahul.asg4_rxs161630;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class AddContact extends AppCompatActivity {

    EditText Name,Email,Phone, LName;
    Button Save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ThemeUtils.onActivityCreateSetTheme(this);


        setContentView(R.layout.activity_add_contact);
        Name = (EditText)findViewById(R.id.ed_name);
        Email = (EditText)findViewById(R.id.ed_email);
        Phone = (EditText)findViewById(R.id.ed_phone);
        LName = (EditText)findViewById(R.id.ed_lname);
        Save = (Button)findViewById(R.id.buttonSave);
        try
        {
            String name = getIntent().getExtras().getString(MainActivity.EXTRA_CONTACT_NAME);
            String lname = getIntent().getExtras().getString(MainActivity.EXTRA_CONTACT_NAME);
            String email = getIntent().getExtras().getString(MainActivity.EXTRA_CONTACT_EMAIL);
            String phone = getIntent().getExtras().getString(MainActivity.EXTRA_CONTACT_PHONE);
            String type = getIntent().getExtras().getString("EXTRA_TYPE");
            if(type.equals("EDIT")){
                Name.setText(name);
                Email.setText(email);
                Phone.setText(phone);
            }
            else
            {
                Name.setHint("Enter Contact Name");
                LName.setHint("Enter Last Name");
                Email.setHint("Enter Email Address");
                Phone.setHint("Enter Phone Number");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contactName = Name.getText().toString();
                String contactPhone = Phone.getText().toString();
                String contactEmail = Email.getText().toString();
                String contactLName = LName.getText().toString();
                if(!contactName.equals("")){
                    if(ValidationUtilities.isValidPhone(contactPhone)){
                        if(ValidationUtilities.isValidEmail(contactEmail)) {
                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("Name",contactName);
                            returnIntent.putExtra("Phone",contactPhone);
                            returnIntent.putExtra("Email",contactEmail);
                            returnIntent.putExtra("LName",contactLName);
                            setResult(Activity.RESULT_OK,returnIntent);
                            finish();
                        }
                        else
                        {
                            Email.setError("Invalid Email Address");
                            Email.requestFocus();
                        }
                    }
                    else
                    {
                        Phone.setError("Invalid Phone Number");
                        Phone.requestFocus();
                    }
                }
                else
                {
                    Name.setError("Enter a valid name");
                    Name.requestFocus();
                }
            }
        });
    }

}
