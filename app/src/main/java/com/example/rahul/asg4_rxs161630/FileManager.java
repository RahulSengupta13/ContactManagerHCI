package com.example.rahul.asg4_rxs161630;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * Created by rahul on 10/20/2016.
 */

public class FileManager {

    Context context;
    public FileManager(Context context)
    {
        String state = Environment.getExternalStorageState();
        this.context = context;
    }

    public ArrayList ReadFromFile()
    {
        try {
            ArrayList myList = new ArrayList();
            File root = new File(Environment.getExternalStorageDirectory(), "ContactsManager");
            if (!root.exists()) {
                root.mkdirs();
            }
            File ContactsFile = new File(root,"Contacts.txt");
            if(ContactsFile.length()>0)
            {
                Toast.makeText(context, "File has entries", Toast.LENGTH_SHORT).show();
                FileReader fileReader = new FileReader(ContactsFile);
                BufferedReader reader = new BufferedReader(fileReader);
                String line;
                while((line=reader.readLine())!=null){
                    String[] contact = line.split("\t");
                    ListData data = new ListData();
                    data.setName(contact[0]);
                    data.setPhone(contact[1]);
                    data.setEmail(contact[2]);
                    myList.add(data);
                }
                fileReader.close();
                reader.close();
                return myList;
            }
            else
            {
                Toast.makeText(context, "File has no entries", Toast.LENGTH_SHORT).show();
                return new ArrayList();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList();
        }
    }

    public void WriteIntoFile(ArrayList<ListData> myList)
    {
        try{
            File root = new File(Environment.getExternalStorageDirectory(), "ContactsManager");
            if (!root.exists()) {
                root.mkdirs();
            }
            File ContactsFile = new File(root,"Contacts.txt");
            FileWriter fileWriter = new FileWriter(ContactsFile);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            for (ListData data :myList) {
                writer.write(data.getName()+"\t"+data.getPhone()+"\t"+data.getEmail()+"\n");
                System.out.println(data.getName()+"\t"+data.getPhone()+"\t"+data.getEmail());
            }
            writer.close();
            fileWriter.close();
            Toast.makeText(context,"File Updated", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
