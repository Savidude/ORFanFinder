package com.orfangenes.control;

import com.orfangenes.model.BlastResult;
import com.orfangenes.model.taxonomy.TaxNode;
import com.orfangenes.model.taxonomy.TaxTree;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BlastResultsProcessor {
    public static final String BLAST_RESULTS_FILE = "blastResults.bl";

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

    public JSONArray generateTableData (TaxTree taxTree) {
        JSONArray blastResultsJSON = new JSONArray();

        int i = 1;
        for (BlastResult result: blastResults) {
            String querySequence = result.getQseqid();
            int staxid = result.getStaxid();

            if (staxid > 0) {
                TaxNode resultNode = taxTree.getNode(staxid);
                if (resultNode != null) {
                    String level = resultNode.getRank();
                    String name = resultNode.getName();
                    String parentName = resultNode.getParent().getName();

                    JSONObject resultData = new JSONObject();
                    resultData.put("no", i);
                    resultData.put("sequence", querySequence);
                    resultData.put("level", level);
                    resultData.put("name", name);
                    resultData.put("parent", parentName);
                    blastResultsJSON.add(resultData);
                    i++;
                }
            }
        }
        return blastResultsJSON;
    }
}
