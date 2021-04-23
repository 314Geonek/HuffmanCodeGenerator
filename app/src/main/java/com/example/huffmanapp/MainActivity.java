package com.example.huffmanapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Node;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.blox.treeview.BaseTreeAdapter;
import de.blox.treeview.TreeNode;
import de.blox.treeview.TreeView;

public class MainActivity extends AppCompatActivity {
    private String userText;
    private List<Root> root;
    private double entropy;
    private TreeView huffmanTree;
   // private Map<String, String> fullCodes, lettersCodes;
    private AppCompatTextView entropiaResult;
    private TextInputEditText inputTextField;
    private AppCompatTextView translatedText;
    private TableLayout tableLayout;
    private ListView listView;
    private ArrayList<Root> arrayList;
    private Map<Character, String> codedLetters;
    private RowAdapter rowAdapter;
    private BaseTreeAdapter treeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputTextField = findViewById(R.id.inputText);
        entropiaResult = findViewById(R.id.entropiaResult);
        tableLayout = findViewById(R.id.lettersTable);
        translatedText = findViewById(R.id.translatedText);
        listView = findViewById(R.id.listView);
        huffmanTree = findViewById(R.id.tree);
        treeAdapter = new BaseTreeAdapter<TreeViewHolder>(this, R.layout.node) {
            @NonNull
            @Override
            public TreeViewHolder onCreateViewHolder(View view) {
                return new TreeViewHolder(view);
            }

            @Override
            public void onBindViewHolder(TreeViewHolder viewHolder, Object data, int position) {
            viewHolder.nodeCode.setText(data.toString()); }
        };
        huffmanTree.setAdapter(treeAdapter);
    }


    public void calculateHuffman(View view) {
        arrayList=new ArrayList<>();
        listView.setAdapter(null);
        entropiaResult.setText("");
        userText = inputTextField.getText().toString();
        if(userText.length()>0)
        calcCharactersList();
        codedLetters = new HashMap<>();
    }
    private void calcCharactersList()
    {   root = new ArrayList<>();
        for(int i=0;i<userText.length();i++)
        {   boolean wasOccurred = false;
            for (Root m:root)
            {
                wasOccurred = m.getLetter()==userText.charAt(i);
                if(wasOccurred)
                {
                    m.setCounter(m.getCounter()+1);
                    break;
                }
            }
            if(!wasOccurred)
                root.add(new Root(userText.charAt(i)));
        }
        arrayList= new ArrayList<>();
        calcEntropy();
        treeMaker();
        codedLetters = new HashMap<>();
        TreeNode father = new TreeNode(root.get(0).getLetter()!=0 ? root.get(0).getLetter() +"\n1" : root.get(0).getCounter());
        calcHuffman(root.get(0), "", father);
        rowAdapter = new RowAdapter(this, arrayList);
        treeAdapter.setRootNode(father);
        listView.setAdapter(rowAdapter);
        translateText();
    }
    private void translateText()
    {   String translatedTextt="";
        for(int i=0;i<userText.length();i++)
        {
            translatedTextt = translatedTextt.concat(codedLetters.get(userText.charAt(i)));
        }
        translatedText.setText("Tekst w kodzie Huffmana: ".concat(translatedTextt));

    }
    private void calcHuffman(Root node, final String code, TreeNode fatherNode)
    {   TreeNode left, right;
        if(node.getNodeLeft()!=null)
        {

            String nodeText = node.getNodeLeft().getLetter()!= 0 ? node.getNodeLeft().getCounter()+"\n\""+node.getNodeLeft().getLetter()+"\"\n" + code.concat(Integer.toString(0)) : Integer.toString(node.getNodeLeft().getCounter());
            left= new TreeNode(nodeText);
            calcHuffman(node.getNodeLeft(),code+0, left);
            fatherNode.addChild(left);
        }
        if(node.getNodeRight()!=null)
        {   String nodeText = node.getNodeRight().getLetter() != '\0' ? node.getNodeRight().getCounter()+"\n\"" + node.getNodeRight().getLetter() +"\"\n"+code.concat(Integer.toString(1)) : Integer.toString(node.getNodeRight().getCounter());
            right = new TreeNode(nodeText);
            calcHuffman(node.getNodeRight(), code+1, right);
            fatherNode.addChild(right);
        }
        else if(node.getNodeLeft()==null) {
            node.setHuffmanCode(code.length()>0? code : "1");
            codedLetters.put(node.getLetter(),code.length()>0 ? code : "1");
            arrayList.add(node);
        }
    }



    private void treeMaker() {
        Root left, right;
        while (root.size()>1){
            left = findTheSmallestInList();
            right = findTheSmallestInList();
            root.add(new Root(left, right));
        }}

    private Root findTheSmallestInList()
    {   int index=0;
        Root newNode = root.get(0);
        for (int i=1; i<root.size();i++) {
            if(newNode.getCounter() > root.get(i).getCounter())
            {
                newNode= root.get(i);
                index=i;
            }
        }
        root.remove(index);
        return newNode;
    }

    private void calcEntropy()
    {
        entropy=0;
        root.forEach((tmp)->{
            double p = tmp.getCounter()/(double)userText.length();
            entropy += p * (Math.log(1.0/p) / Math.log(2));
        });
        DecimalFormat df = new DecimalFormat("#.#####");
        entropiaResult.setText(getText(R.string.entropia).toString().concat(df.format(entropy)));
    }
}