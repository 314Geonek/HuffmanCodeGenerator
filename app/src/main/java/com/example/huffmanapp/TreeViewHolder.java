package com.example.huffmanapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import de.blox.treeview.TreeView;

public class TreeViewHolder {
    public TextView  nodeCode;
    TreeViewHolder(View view){
        nodeCode = view.findViewById(R.id.huffmanCode);
    }

}
