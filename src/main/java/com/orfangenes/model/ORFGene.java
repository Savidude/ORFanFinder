package com.orfangenes.model;

import com.orfangenes.control.Sequence;

public class ORFGene {
    private int id;
    private String description;
    private String level;
    private String taxonomy;

    public ORFGene (Sequence sequence, int geneID, String level) {
        this.id = geneID;
        this.description = sequence.getDescriptionFromGID(geneID);
        this.level = level;
        this.taxonomy = "species";
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getLevel() {
        return level;
    }

    public String getTaxonomy() {
        return taxonomy;
    }
}
