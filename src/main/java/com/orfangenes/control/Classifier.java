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

//        Map<Integer, String> inputTaxHeirarchy = tree.getHeirarchyFromNode(organismTaxID);
        Map<String, Integer> inputTaxHeirarchy = tree.getHeirarchyFromNode(organismTaxID);
        List<Integer> inputIDs = sequence.getGIDs();
        for (int id : inputIDs) {
            Set<Integer> taxIDs = getBlastTaxonomies(blastResults, id);

//            String level = Constants.STRICT_ORFAN;

            // Getting the hierarchy for each taxonomy
//            Map<Integer, Map<String, Integer>> hierarchies = new HashMap<>();
            List<Map<String, Integer>> hierarchies = new ArrayList<>();
            for (int taxID: taxIDs) {
                Map<String, Integer> taxHierarchy = tree.getHeirarchyFromNode(taxID);
                hierarchies.add(taxHierarchy);
            }
            String level = getLevel(hierarchies, inputTaxHeirarchy, Constants.PHYLUM);
            if (level == null) {
                level = Constants.STRICT_ORFAN;
            }

//            for (int taxID : taxIDs) {
//                if (organismTaxID != taxID) {
//                    List<Integer> lineageList = lineage.getLineageForTaxID(taxID);
//                    if (lineageList != null) {
//                        String lvl = getLevel(lineageList, inputTaxHeirarchy);
//                        if (lvl != null) {
//                            level = lvl;
//                            break;
//                        }
//                    }
//                }
//            }
            classification.put(id, level);
        }
        return classification;
    }

    private String getLevel(List<Map<String, Integer>> hierarchies, Map<String, Integer> inputTaxHeirarchy, String currentRank) {

        // Getting tax id of the current rank in the input sequence
        int rankTaxID = inputTaxHeirarchy.get(currentRank);
        Set<Integer> taxonomiesAtCurrentRank = new HashSet<>();

        for (Map<String, Integer> currentTaxHierarchy: hierarchies) {
            try {
                int currentRankId = currentTaxHierarchy.get(currentRank);
                taxonomiesAtCurrentRank.add(currentRankId);
            } catch (NullPointerException e) {
                // Do nothing
            }
        }

        String geneType = null;
        String nextRank = null;
        switch (currentRank) {
            case Constants.PHYLUM: geneType = Constants.NATIVE_GENE; nextRank = Constants.CLASS; break;
            case Constants.CLASS: geneType = Constants.PHYLUM_ORFAN; nextRank = Constants.ORDER; break;
            case Constants.ORDER: geneType = Constants.CLASS_ORFAN; nextRank = Constants.FAMILY; break;
            case Constants.FAMILY: geneType = Constants.ORDER_ORFAN; nextRank = Constants.GENUS; break;
            case Constants.GENUS: geneType = Constants.FAMILY_ORFAN; nextRank = Constants.SPECIES; break;
            case Constants.SPECIES: geneType = Constants.GENUS_ORFAN; nextRank = null; break;
        }
        if (taxonomiesAtCurrentRank.size() == 1 && taxonomiesAtCurrentRank.contains(rankTaxID)) {
            return getLevel(hierarchies, inputTaxHeirarchy, nextRank);
        } else if (taxonomiesAtCurrentRank.size() > 1){
            return geneType;
        } else {
            return null;
        }
    }

//    private String getLevel(List<Integer> lineageList, Map<Integer, String> inputTaxHeirarchy) {
//        for (int taxIDinLineage: lineageList) {
//            String rank = inputTaxHeirarchy.get(taxIDinLineage);
//            if (rank != null) {
//                switch (rank) {
//                    case Constants.SPECIES: return Constants.NATIVE_GENE;
//                    case Constants.GENUS: return Constants.SPECIES_ORFAN;
//                    case Constants.FAMILY: return Constants.GENUS_ORFAN;
//                    case Constants.ORDER: return Constants.FAMILY_ORFAN;
//                    case Constants.CLASS: return Constants.ORDER_ORFAN;
//                    case Constants.PHYLUM: return Constants.CLASS_ORFAN;
//                    case Constants.SUPERKINGDOM: return Constants.PHYLUM_ORFAN;
//                    default: return null;
//                }
//            }
//
//        }
//        return null;
//    }

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
