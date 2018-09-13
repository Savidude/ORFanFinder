package com.orfangenes.control;

import com.orfangenes.constants.Constants;
import com.orfangenes.model.BlastResult;
import com.orfangenes.model.Gene;
import com.orfangenes.model.taxonomy.TaxNode;
import com.orfangenes.model.taxonomy.TaxTree;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class BlastResultsProcessor {
    private static final String BLAST_RESULTS_FILE = "blastResults.bl";

    private List<BlastResult> blastResults;

    public BlastResultsProcessor(String outputDir) {
        String blastResultsFileName = outputDir + "/" + BLAST_RESULTS_FILE;
        ArrayList<BlastResult> blastResults = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(new File(blastResultsFileName));
            while (scanner.hasNextLine()) {
                BlastResult result = new BlastResult(scanner.nextLine());
                blastResults.add(result);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.blastResults = blastResults;
    }

    public List<BlastResult> getBlastResults() {
        return blastResults;
    }

    public JSONArray generateBlastResultsTrees(TaxTree taxTree, List<Gene> genes) {
        JSONArray blastTrees = new JSONArray();
        for (Gene gene: genes) {
            JSONObject blastResult = new JSONObject();
            blastResult.put("description", gene.getDescription());
            try {
                // Getting tax IDs for the gene
                Set<Integer> taxIDs = new LinkedHashSet<>();
                for (BlastResult result : blastResults) {
                    if (result.getQueryid() == gene.getGeneID()) {
                        taxIDs.add(result.getStaxid());
                    }
                }

                // Getting the hierarchy for each taxonomy
                List<Map<String, Integer>> hierarchies = new ArrayList<>();
                for (int taxID: taxIDs) {
                    Map<String, Integer> taxHierarchy = taxTree.getHeirarchyFromNode(taxID);
                    hierarchies.add(taxHierarchy);
                }

                if (hierarchies.size() > 0) {
                    // Creating a tax tree for the BLAST results
                    TaxTree tree = (TaxTree)taxTree.clone();
                    for (Map<String, Integer> hierarchy: hierarchies) {
                        if (hierarchy != null) {
                            createTree(tree, hierarchy, Constants.SUPERKINGDOM);
                        }
                    }

                    Set<Integer> taxonomiesAtSuperkingdom = new HashSet<>();
                    for (Map<String, Integer> currentTaxHierarchy: hierarchies) {
                        try {
                            int currentRankId = currentTaxHierarchy.get(Constants.SUPERKINGDOM);
                            taxonomiesAtSuperkingdom.add(currentRankId);
                        } catch (NullPointerException e) {
                            // Do nothing
                        }
                    }
                    if (taxonomiesAtSuperkingdom.size() > 1) {
                        // TODO: Add root to tree
                    } else {
                        Map<String, Integer> hierarchy = hierarchies.get(0);
                        int superkingdomTaxID = hierarchy.get(Constants.SUPERKINGDOM);
                        TaxNode superkingdomNode = tree.getNode(superkingdomTaxID);
                        JSONObject jsonTree = createJSONNode(superkingdomNode);
                        blastResult.put("tree", jsonTree);
                        blastTrees.add(blastResult);
                    }
                }
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        return blastTrees;
    }

    private JSONObject createJSONNode (TaxNode node) {
        JSONObject jsonNode = new JSONObject();
        jsonNode.put("name", node.getName());

        if (node.getChildren().size() > 0) {
            JSONArray children = new JSONArray();
            for (TaxNode child: node.getChildren()) {
                children.add(createJSONNode(child));
            }
            jsonNode.put("children", children);
        }
        return jsonNode;
    }

    private void createTree (TaxTree tree, Map<String, Integer> hierarchy, String currentRank) {
        int currentRankTaxID = hierarchy.get(currentRank);
        TaxNode currentRankNode = tree.getNode(currentRankTaxID);
        String nextRank = getNextRank(currentRank);

        boolean isComplete = false;
        while (nextRank != null && !isComplete) {
            try {
                currentRankNode.addChild(tree.getNode(hierarchy.get(nextRank)));
                isComplete = true;
                createTree(tree, hierarchy, nextRank);
            } catch (NullPointerException e) {
                nextRank = getNextRank(nextRank);
            }
        }
    }

    private String getNextRank(String currentRank) {
        String nextRank = null;
        switch (currentRank) {
            case Constants.SUPERKINGDOM: nextRank = Constants.KINGDOM; break;
            case Constants.KINGDOM: nextRank = Constants.PHYLUM; break;
            case Constants.PHYLUM: nextRank = Constants.CLASS; break;
            case Constants.CLASS:  nextRank = Constants.ORDER; break;
            case Constants.ORDER: nextRank = Constants.FAMILY; break;
            case Constants.FAMILY: nextRank = Constants.GENUS; break;
            case Constants.GENUS: nextRank = Constants.SPECIES; break;
            case Constants.SPECIES: nextRank = null; break;
        }
        return nextRank;
    }
}
