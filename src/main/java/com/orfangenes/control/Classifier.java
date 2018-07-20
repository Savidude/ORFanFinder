package com.orfangenes.control;

import com.orfangenes.model.BlastResult;
import com.orfangenes.model.taxonomy.TaxTree;
import com.orfangenes.constants.Constants;

import java.util.*;

public class Classifier {
    private static final String GENE_TYPE = "geneType";
    private static final String PREV_RANK = "prevRank";
    private static final String NEXT_RANK = "nextRank";

    private Sequence sequence;
    private TaxTree tree;
    private int organismTaxID;

    public Classifier(Sequence sequence, TaxTree tree, int organismTaxID) {
        this.sequence = sequence;
        this.tree = tree;
        this.organismTaxID = organismTaxID;
    }

    public Map<Integer, String> getGeneClassification(List<BlastResult> blastResults) {
        Map<Integer, String> classification = new HashMap<>();

        Map<String, Integer> inputTaxHierarchy = tree.getHeirarchyFromNode(organismTaxID);

        // Getting list of input IDs from FASTA sequence
        List<Integer> inputIDs = sequence.getGIDs();
        for (int id : inputIDs) { // For each input ID
            Set<Integer> taxIDs = getBlastTaxonomies(blastResults, id);

            // Getting the hierarchy for each taxonomy
            List<Map<String, Integer>> hierarchies = new ArrayList<>();
            for (int taxID: taxIDs) {
                Map<String, Integer> taxHierarchy = tree.getHeirarchyFromNode(taxID);
                hierarchies.add(taxHierarchy);
            }
            String level = getLevel(hierarchies, inputTaxHierarchy, Constants.SUPERKINGDOM, 0);
            if (level == null) {
                level = Constants.STRICT_ORFAN;
            }
            classification.put(id, level);
        }
        return classification;
    }

    // Recursive method
    private String getLevel(List<Map<String, Integer>> hierarchies, Map<String, Integer> inputTaxHierarchy,
                            String currentRank, int levelsSkipped) {
        Map<String, String> rankInfo;
        if (currentRank != null) {
            rankInfo = getRankInfo(currentRank);
        } else {
            return null;
        }

        // Getting tax id of the current rank in the input sequence
        int rankTaxID = 0;
        try {
            rankTaxID = inputTaxHierarchy.get(currentRank);
        } catch (NullPointerException e) {
            getLevel(hierarchies, inputTaxHierarchy, rankInfo.get(NEXT_RANK), ++levelsSkipped);
        }
        Set<Integer> taxonomiesAtCurrentRank = new HashSet<>();

        for (Map<String, Integer> currentTaxHierarchy: hierarchies) {
            try {
                int currentRankId = currentTaxHierarchy.get(currentRank);
                taxonomiesAtCurrentRank.add(currentRankId);
            } catch (NullPointerException e) {
                // Do nothing
            }
        }

        if (taxonomiesAtCurrentRank.size() == 1 && taxonomiesAtCurrentRank.contains(rankTaxID)) {
            if (currentRank.equals(Constants.SPECIES)) {
                return Constants.ORFAN_GENE;
            }
            return getLevel(hierarchies, inputTaxHierarchy, rankInfo.get(NEXT_RANK), 0);
        } else if (taxonomiesAtCurrentRank.size() > 1 || (taxonomiesAtCurrentRank.size() == 1 && !taxonomiesAtCurrentRank.contains(rankTaxID))){
            for (int i = 0; i < levelsSkipped; i--) {
                rankInfo = getRankInfo(rankInfo.get(PREV_RANK));
            }
            return rankInfo.get(GENE_TYPE);
        } else {
            return getLevel(hierarchies, inputTaxHierarchy, rankInfo.get(NEXT_RANK), ++levelsSkipped);
        }
    }

    private Map<String, String> getRankInfo(String currentRank) {
        String geneType = null;
        String prevRank = null;
        String nextRank = null;
        switch (currentRank) {
            case Constants.SUPERKINGDOM: geneType = Constants.MULTI_DOMAIN_GENE; prevRank = null; nextRank = Constants.KINGDOM; break;
            case Constants.KINGDOM: geneType = Constants.DOMAIN_RESTRICTED_GENE; prevRank = Constants.SUPERKINGDOM; nextRank = Constants.PHYLUM; break;
            case Constants.PHYLUM: geneType = Constants.KINGDOM_RESTRICTED_GENE;  prevRank = Constants.KINGDOM; nextRank = Constants.CLASS; break;
            case Constants.CLASS: geneType = Constants.PHYLUM_RESTRICTED_GENE; prevRank = Constants.PHYLUM; nextRank = Constants.ORDER; break;
            case Constants.ORDER: geneType = Constants.CLASS_RESTRICTED_GENE; prevRank = Constants.CLASS; nextRank = Constants.FAMILY; break;
            case Constants.FAMILY: geneType = Constants.ORDER_RESTRICTED_GENE; prevRank = Constants.ORDER; nextRank = Constants.GENUS; break;
            case Constants.GENUS: geneType = Constants.FAMILY_RESTRICTED_GENE; prevRank = Constants.FAMILY; nextRank = Constants.SPECIES; break;
            case Constants.SPECIES: geneType = Constants.GENUS_RESTRICTED_GENE; prevRank = Constants.GENUS; nextRank = null; break;
        }

        Map<String, String> rankInfo = new HashMap<>();
        rankInfo.put(GENE_TYPE, geneType);
        rankInfo.put(PREV_RANK, prevRank);
        rankInfo.put(NEXT_RANK, nextRank);
        return rankInfo;
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
