package com.orfangenes.model;

public class ORFGene {
    private int id;
    private String description;
    private String level;
    private String taxonomy;

    public ORFGene (Gene gene, String level) {
        this.id = gene.getGeneID();
        this.description = gene.getDescription();
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
