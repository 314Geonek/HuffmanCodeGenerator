package com.example.huffmanapp;

public class Root {
    private char letter;
    private int counter;
    private Root nodeLeft, nodeRight;
    private String huffmanCode;

    public Root(char letter) {
        this.letter = letter;
        this.counter = 1;
        this.nodeLeft =null;
        this.nodeRight = null;
    }
    public Root(Root left, Root right)
    {
        nodeLeft=left;
        nodeRight=right;
        counter = nodeLeft.getCounter() + nodeRight.getCounter();
    }

    public String getHuffmanCode() {
        return huffmanCode;
    }
    public void setHuffmanCode(String huffmanCode) {
        this.huffmanCode = huffmanCode;
    }
    public char getLetter() {
        return letter;
    }
    public int getCounter() {
        return counter;
    }
    public void setCounter(int counter) {
        this.counter = counter;
    }
    public Root getNodeLeft() {
        return nodeLeft;
    }
    public Root getNodeRight() {
        return nodeRight;
    }
}
