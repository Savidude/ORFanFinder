package com.orfangenes.model.taxonomy;

public class TaxNode {

    private int nID;
    private String name;
    private String nRank;
    private TaxNode parent;

    public TaxNode(int nID, String name, String nRank) {
        this.nID = nID;
        this.name = name;
        this.nRank = nRank;
    }

    public void setParent(TaxNode parent) {
        this.parent = parent;
    }

    public TaxNode getParent() {
        return parent;
    }

    public String getRank() {
        return nRank;
    }

    public int getID() {
        return nID;
    }

    public String getName() {
        return name;
    }
}