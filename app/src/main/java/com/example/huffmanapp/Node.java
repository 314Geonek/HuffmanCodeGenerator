package com.example.huffmanapp;

public class Node {
    private char letter;
    private int counter;
    private Node nodeLeft, nodeRight;
    private String huffmanCode;

    public Node(char letter) {
        this.letter = letter;
        this.counter = 1;
        this.nodeLeft =null;
        this.nodeRight = null;
    }
    public Node(Node left, Node right)
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
    public Node getNodeLeft() {
        return nodeLeft;
    }
    public Node getNodeRight() {
        return nodeRight;
    }
}
