package com.example.huffmanapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import com.google.android.material.textfield.TextInputEditText;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.blox.treeview.BaseTreeAdapter;
import de.blox.treeview.TreeNode;
import de.blox.treeview.TreeView;

public class MainActivity extends AppCompatActivity {

    private AppCompatTextView translatedTextView;
    private AppCompatTextView entropyTextView;
    private TextInputEditText inputTextField;
    private AppCompatTextView keyWordResult;
    private TreeView huffmanTree;
    private ListView listView;
    private ArrayList<Node> nodeArrayList;
    private Map<Character, String> codedLetters;
    private RowAdapter rowAdapter;
    private List<Node> node;
    private BaseTreeAdapter treeAdapter;
    private String userText;
    private double entropy;
    private double keyWordLength;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputTextField = findViewById(R.id.inputText);
        entropyTextView = findViewById(R.id.entropyResult);
        keyWordResult = findViewById(R.id.keyWOrdResult);
        translatedTextView = findViewById(R.id.translatedText);
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
        huffmanTree.setOnTouchListener((v, event) -> {
            @SuppressLint("ClickableViewAccessibility") int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    break;

                case MotionEvent.ACTION_UP:
                    v.getParent().requestDisallowInterceptTouchEvent(false);
                    break;
            }
            v.onTouchEvent(event);
            return true;
        });
        listView.setOnTouchListener((v, event) -> {
            @SuppressLint("ClickableViewAccessibility") int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    break;

                case MotionEvent.ACTION_UP:
                    v.getParent().requestDisallowInterceptTouchEvent(false);
                    break;
            }
            v.onTouchEvent(event);
            return true;
        });
    }


    public void calculateHuffman(View view) {
        nodeArrayList =new ArrayList<>();
        listView.setAdapter(null);
        entropyTextView.setText(getString(R.string.entropia));
        userText = Objects.requireNonNull(inputTextField.getText()).toString();
        if(userText.length()>0)
        calcCharactersList();
        codedLetters = new HashMap<>();
    }
    private void calcCharactersList()
    {   node= new ArrayList<>();
        for(int i=0;i<userText.length();i++)
        {   boolean wasOccurred = false;
            for (Node m: node)
            {
                wasOccurred = m.getLetter()==userText.charAt(i);
                if(wasOccurred)
                {
                    m.setCounter(m.getCounter()+1);
                    break;
                }
            }
            if(!wasOccurred)
                node.add(new Node(userText.charAt(i)));
        }
        nodeArrayList = new ArrayList<>();
        calcEntropy();
        treeMaker();
        codedLetters = new HashMap<>();
        TreeNode father = new TreeNode(node.get(0).getLetter()!=0 ? node.get(0).getLetter() +"\n1" : node.get(0).getCounter());
        calcHuffman(node.get(0), "", father);
        rowAdapter = new RowAdapter(this, nodeArrayList);
        treeAdapter.setRootNode(father);
        listView.setAdapter(rowAdapter);
        translateText();
        calcKeyWordLength();
    }
    private void translateText()
    {   String translatedTextt="";
        for(int i=0;i<userText.length();i++)
        {
            translatedTextt = translatedTextt.concat(codedLetters.get(userText.charAt(i)));
        }
        translatedTextView.setText("Tekst w kodzie Huffmana: ".concat(translatedTextt));

    }
    private void calcHuffman(Node node, final String code, TreeNode fatherNode)
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
            nodeArrayList.add(node);
        }
    }



    private void treeMaker() {
        Node left, right;
        while (node.size()>1){
            left = findTheSmallestInList();
            right = findTheSmallestInList();
            node.add(new Node(left, right));
        }}

    private Node findTheSmallestInList()
    {   int index=0;
        Node newNode = node.get(0);
        for (int i = 1; i< node.size(); i++) {
            if(newNode.getCounter() > node.get(i).getCounter())
            {
                newNode= node.get(i);
                index=i;
            }
        }
        node.remove(index);
        return newNode;
    }

    private void calcEntropy()
    {
        entropy=0;
        node.forEach((tmp)->{
            double p = tmp.getCounter()/(double)userText.length();
            entropy += p * (Math.log(1.0/p) / Math.log(2));
        });
                DecimalFormat df = new DecimalFormat("#.#####");
        entropyTextView.setText(entropyTextView.getText().toString().concat("\n").concat(df.format(entropy)));
    }
    private void calcKeyWordLength()
    {
        keyWordLength=0;
        nodeArrayList.forEach((tmp)->{
            double p = tmp.getCounter()/(double)userText.length();
            keyWordLength += p * tmp.getHuffmanCode().length();
        });
        DecimalFormat df = new DecimalFormat("#.#####");
        keyWordResult.setText(getText(R.string.meanKeyWordLength).toString().concat("\n").concat(df.format(keyWordLength)));
    }

    @SuppressLint("SetTextI18n")
    public void sheSellsGenerateText(View view) {
        inputTextField.setText("She sells seashells by the seashore, The shells she sells are seashells, I'm sure. So if she sells seashells on the seashore, Then I'm sure she sells seashore shells.");
    }
}