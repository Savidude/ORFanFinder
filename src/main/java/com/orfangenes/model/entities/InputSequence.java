package com.orfangenes.model.entities;

import com.orfangenes.constants.Constants;

public class InputSequence {

    private String organismName;
    private String genesequence;
    private String maxevalue;
    private String maxtargets;

    public String getOrganismName() {
        return organismName;
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

    public void setGenesequence(String genesequence) {
        this.genesequence = genesequence;
    }

    public void setMaxevalue(String maxevalue) {
        this.maxevalue = maxevalue;
    }

    public void setMaxtargets(String maxtargets) {
        this.maxtargets = maxtargets;
    }

    public String getType() {
        String[] sequences = this.genesequence.split("\n\n");

        // Getting first sequence. All sequences do not need to b e processed since the input sequence should only have one type of gene.
        String sequence = sequences[0];
        String[] lines = sequence.split("\n");
        String sequenceString = "";
        for (int i = 1; i < lines.length; i++) {
            sequenceString += lines[i];
        }
        char[] characters = sequenceString.toCharArray();
        for (char character : characters) {
            if ((character != 'A') && (character != 'T') && (character != 'C') && (character != 'G')) {
                return Constants.TYPE_PROTEIN;
            }
        }
        return Constants.TYPE_NUCLEOTIDE;
    }
}
