package com.orfangenes.control;

import com.orfangenes.model.BlastResult;
import com.orfangenes.model.taxonomy.TaxTree;
import com.orfangenes.constants.Constants;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Classifier {
    private Sequence sequence;
    private TaxTree tree;
    private Lineage lineage;
    private int organismTaxID;

    public Classifier(Sequence sequence, TaxTree tree, Lineage lineage, int organismTaxID) {
        this.sequence = sequence;
        this.tree = tree;
        this.lineage = lineage;
        this.organismTaxID = organismTaxID;
    }

    public Map<Integer, String> getGeneClassification(List<BlastResult> blastResults) {
        Map<Integer, String> classification = new HashMap<>();
        Map<Integer, String> inputTaxHeirarchy = tree.getHeirarchyFromNode(organismTaxID);
        List<Integer> inputIDs = sequence.getGIDs();
        for (int id : inputIDs) {
            Set<Integer> taxIDs = getBlastTaxonomies(blastResults, id);
            String level = Constants.STRICT_ORFAN;
            for (int taxID : taxIDs) {
                if (organismTaxID != taxID) {
                    List<Integer> lineageList = lineage.getLineageForTaxID(taxID);
                    String lvl = getLevel(lineageList, inputTaxHeirarchy);
                    if (lvl != null) {
                        level = lvl;
                        break;
                    }
                }
            }
            classification.put(id, level);
        }
        return classification;
    }

    private String getLevel(List<Integer> lineageList, Map<Integer, String> inputTaxHeirarchy) {
        for (int taxIDinLineage: lineageList) {
            String rank = inputTaxHeirarchy.get(taxIDinLineage);
            if (rank != null) {
                switch (rank) {
                    case Constants.SPECIES: return Constants.NATIVE_GENE;
                    case Constants.GENUS: return Constants.SPECIES_ORFAN;
                    case Constants.FAMILY: return Constants.GENUS_ORFAN;
                    case Constants.ORDER: return Constants.FAMILY_ORFAN;
                    case Constants.CLASS: return Constants.ORDER_ORFAN;
                    case Constants.PHYLUM: return Constants.CLASS_ORFAN;
                    case Constants.SUPERKINGDOM: return Constants.PHYLUM_ORFAN;
                    default: return null;
                }
            }
        }
        return null;
    }

    private Set<Integer> getBlastTaxonomies(List<BlastResult> blastResults, int gid) {
        Set<Integer> taxIDs = new LinkedHashSet<>();
        for (BlastResult result : blastResults) {
            if (result.getQueryid() == gid) {
                taxIDs.add(result.getStaxid());
            }
        }
        return taxIDs;
    }
}
