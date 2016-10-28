package com.example.rahul.asg4_rxs161630;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

//Adapter for the listview
public class ListViewAdapter extends BaseAdapter {
    ArrayList<ListData> myList = new ArrayList();
    LayoutInflater inflater;
    Context context;
    public ListViewAdapter(Context context, ArrayList myList){
        this.myList = myList;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }
    @Override
    public int getCount() {
        return myList.size();
    }
    @Override
    public ListData getItem(int position) {
        return myList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder myViewHolder;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.layout_listview_contacts, parent, false);
            myViewHolder = new ViewHolder(convertView);
            convertView.setTag(myViewHolder);
        }
        else{
            myViewHolder = (ViewHolder)convertView.getTag();
        }
        ListData currentListData = getItem(position);
        myViewHolder.name.setText(currentListData.getName());
        myViewHolder.email.setText(currentListData.getEmail());
        myViewHolder.phone.setText(currentListData.getPhone());
        myViewHolder.lname.setText(currentListData.getLName()+", ");
        myViewHolder.image.setImageResource(R.mipmap.contact_image);
        return convertView;
    }
    private class ViewHolder{
        TextView name,email,phone,lname;
        ImageView image;
        public ViewHolder(View item){
            name = (TextView)item.findViewById(R.id.contact_name);
            email = (TextView)item.findViewById(R.id.contact_email);
            phone = (TextView)item.findViewById(R.id.contact_phone);
            lname = (TextView)item.findViewById(R.id.contact_Lname);
            image = (ImageView)item.findViewById(R.id.contact_image);
        }
    }

}
