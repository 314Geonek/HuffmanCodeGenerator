package com.example.huffmanapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class RowAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Root> arrayList;
    private TextView ascii, letter, huffmanCode, counter;
    public RowAdapter(Context context, ArrayList<Root> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }
    @Override
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.table_layout, parent, false);

        ascii = convertView.findViewById(R.id.firstColumn);
        letter = convertView.findViewById(R.id.secondColumn);
        counter = convertView.findViewById(R.id.thirdColumn);
        huffmanCode = convertView.findViewById(R.id.fourthColumn);
        char ch = arrayList.get(position).getLetter();
        letter.setText("\"" + ch+ "\"");
        int ascicode = ch;
        ascii.setText(ascicode+"");
        huffmanCode.setText(arrayList.get(position).getHuffmanCode());
        counter.setText(arrayList.get(position).getCounter()+"");
        return convertView;
    }
}