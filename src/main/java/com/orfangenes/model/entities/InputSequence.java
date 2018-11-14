package com.orfangenes.model.entities;

public class InputSequence {

    private String organismName;
    private String type;
    private String genesequence;
    private String maxevalue;
    private String maxtargets;

    public String getOrganismName() {
        return organismName;
    }

    public String getType() {
        return type;
    }

    public String getGenesequence() {
        return genesequence;
    }

    public String getMaxevalue() {
        return maxevalue;
    }

    public String getMaxtargets() {
        return maxtargets;
    }

    public void setOrganismName(String organismName) {
        this.organismName = organismName;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setGenesequence(String genesequence) {
        this.genesequence = genesequence;
    }

    public void setMaxevalue(String maxevalue) {
        this.maxevalue = maxevalue;
    }

    public void setMaxtargets(String maxtargets) {
        this.maxtargets = maxtargets;
    }
}
