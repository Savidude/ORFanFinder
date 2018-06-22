package com.orfangenes.model;

import com.orfangenes.control.Sequence;

public class ORFGene {
    private int id;
    private String description;
    private String level;
    private String taxonomy;

    public ORFGene (Sequence sequence, int geneID, boolean isNativeGene) {
        this.id = geneID;
        this.description = sequence.getDescriptionFromGID(geneID);
        if (isNativeGene) {
            this.level = "Native Gene";
        } else {
            this.level = "Strict ORFan";
        }
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
