package com.orfangenes.model;

public class Gene {
    private int geneID;
    private String sequence;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private int taxID;

    public int getGeneID() {
        return geneID;
    }

    public void setGeneID(int geneID) {
        this.geneID = geneID;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public int getTaxID() {
        return taxID;
    }

    public void setTaxID(int taxID) {
        this.taxID = taxID;
    }
}
