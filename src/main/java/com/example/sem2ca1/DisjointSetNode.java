package com.example.sem2ca1;

public class DisjointSetNode<T> {
    public DisjointSetNode<?> parent=null;
    public T data;
    public DisjointSetNode(T data) {
        this.data=data;
    }
//Add getters and setters for attributes, etc.
}