package com.orfangenes;

import com.orfangenes.control.Lineage;
import com.orfangenes.control.Sequence;
import com.orfangenes.model.BlastResult;
import com.orfangenes.model.ORFGene;
import com.orfangenes.model.taxonomy.TaxNode;
import com.orfangenes.model.taxonomy.TaxTree;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;
import java.util.*;

public class ORFanGenes {

    public static void main (String[] args) {
        Map<String, String> arguments = new HashMap<>();

        // Displaying help messages for help requests and invalid arguments
        for(int i = 0; i < args.length; i+=2) {
            if (args[i].equals("-h")) {
                printHelp(1);
                return;
            } else if (args[i].equals("--help")) {
                printHelp(2);
            } else {
                if (args[i].charAt(0) != '-') {
                    System.out.println("Invalid argument " + args[i]);
                    printHelp(1);
                    return;
                } else {
                    arguments.put(args[i], args[i+1]);
                }
            }
        }

        // Displaying error messages for empty arguments
        if (arguments.get("-query").equals("")) {
            System.err.println("A input sequence file must be provided");
            printHelp(1);
            return;
        }
        if (arguments.get("-nodes").equals("")) {
            System.err.println("A nodes file must be provided");
            printHelp(1);
            return;
        }
        if (arguments.get("-lineage").equals("")) {
            System.err.println("A lineage file must be provided");
            printHelp(1);
            return;
        }
        if (arguments.get("-tax").equals("")) {
            System.err.println("A taxonomy ID must be provided");
            printHelp(1);
            return;
        }

        // Displaying error messages for invalid files
        File blast = new File(arguments.get("-query"));
        if (!blast.exists()) {
            System.err.println("Input sequence file is invalid.");
            return;
        }
        File nodes = new File(arguments.get("-nodes"));
        if (!nodes.exists()) {
            System.err.println("Nodes file is invalid.");
            return;
        }
        File lineage = new File(arguments.get("-lineage"));
        if (!lineage.exists()) {
            System.err.println("Lineage file is invalid.");
            return;
        }
        int organismTaxID;
        try {
            organismTaxID = Integer.parseInt(arguments.get("-tax"));
        } catch (NumberFormatException e) {
            System.err.println("Organism TaxID must be an number.");
            return;
        }

        //Generating BLAST file
        Sequence sequence = new Sequence(arguments.get("-query"));
//        sequence.generateBlastFile();

        ArrayList<Integer> inputIDs = sequence.getGIDs();
        TaxTree taxTree = new TaxTree(arguments.get("-nodes"));
        TaxNode genusNode = taxTree.getGenusParent(organismTaxID);
        Lineage taxLineage = new Lineage(arguments.get("-lineage"));

        //Generating ORFanGenes result
        Map<Integer, Boolean> geneClassification = getGeneClassification(organismTaxID, inputIDs, genusNode, taxLineage);
        generateResult(geneClassification, sequence);
    }

    private static void generateResult (Map<Integer, Boolean> geneClassification, Sequence sequence) {
        JSONObject result = new JSONObject();
        JSONArray list = new JSONArray();

        Map<String, Integer> orfanGeneCount = new HashMap<>();
        orfanGeneCount.put("Superkingdom", 0);
        orfanGeneCount.put("Class", 0);
        orfanGeneCount.put("Family", 0);
        orfanGeneCount.put("Phylum", 0);
        orfanGeneCount.put("Genus", 0);
        orfanGeneCount.put("Species", 0);
        orfanGeneCount.put("Strict", 0);

        Iterator it = geneClassification.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();

            ORFGene orfGene = new ORFGene(sequence, (Integer)pair.getKey(), (Boolean)pair.getValue());
//            JSONObject gene = new JSONObject();
//            gene.put("id", orfGene.getId());
//            gene.put("description", orfGene.getDescription());
//            gene.put("level", orfGene.getLevel());
//            gene.put("taxonomy", orfGene.getTaxonomy());
//            list.add(gene);
            JSONArray gene = new JSONArray();
            gene.add(orfGene.getId());
            gene.add(orfGene.getDescription());
            gene.add(orfGene.getLevel());
            gene.add("Bacteria");
            list.add(gene);

            //TODO: Update to all gene levels
            switch (orfGene.getLevel()) {
                case "Strict ORFan": {
                    int count = orfanGeneCount.get("Strict");
                    count++;
                    orfanGeneCount.put("Strict", count);
                }
            }
        }

        result.put("data", list);
        try {
            StringWriter writer = new StringWriter();
            result.writeJSONString(writer);
            String orfanGenesData = writer.toString();
            System.out.println(writer.toString());

            PrintWriter printWriter = new PrintWriter("ORFanGenes.json", "UTF-8");
            printWriter.println(orfanGenesData);
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject result2 = new JSONObject();
        JSONArray list2 = new JSONArray();

        JSONObject chartJSON = new JSONObject();
        JSONArray x = new JSONArray();
        JSONArray y = new JSONArray();

        it = orfanGeneCount.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();

            JSONArray array = new JSONArray();
            array.add(pair.getKey());
            array.add(pair.getValue());
            list2.add(array);

            x.add(pair.getKey());
            y.add(pair.getValue());
        }
        result2.put("data", list2);

        chartJSON.put("x", x);
        chartJSON.put("y", y);

        try {
            StringWriter writer = new StringWriter();
            result.writeJSONString(writer);
            String orfanGenesSummary = writer.toString();

            PrintWriter printWriter = new PrintWriter("ORFanGenesSummary.json", "UTF-8");
            printWriter.println(orfanGenesSummary);
            printWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            StringWriter writer2 = new StringWriter();
            chartJSON.writeJSONString(writer2);
            String orfanGenesSummaryChart = writer2.toString();
            PrintWriter printWriter2 = new PrintWriter("ORFanGenesSummarychart.json", "UTF-8");
            printWriter2.println(orfanGenesSummaryChart);
            printWriter2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Map<Integer, Boolean> getGeneClassification(int organismTaxID, ArrayList<Integer> inputIDs,
                                                               TaxNode genusNode, Lineage taxLineage) {
        Map<Integer, Boolean> geneClassification = new HashMap<>();
        ArrayList<BlastResult> blastResults = getBlastResults(Sequence.BLAST_RESULTS_FILE);
        for (int id: inputIDs) {
            boolean isNativeGene = false;
            for (BlastResult result: blastResults) {
                if (result.getQueryid() == id) {
                    if (organismTaxID != result.getStaxid()) {
                        if (taxLineage.isNodeInLineage(result.getStaxid(), genusNode)) {
                            isNativeGene = true;
                            break;
                        }
                    }
                }
            }
            geneClassification.put(id, isNativeGene);
        }
        return geneClassification;
    }

    private static ArrayList<BlastResult> getBlastResults(String blastResultsFileName) {
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
        return blastResults;
    }

    private static void printHelp(int help) {
        if (help == 0) {
            System.err.println("Incorrect number of command line arguments");
            System.err.println("Usage: ORFanGenes.jar <-query BLAST_Output_filename> <-names taxonomy_to_names_filename> <-nodes nodes_filename> <-tax taxonomy_id>");
        } else if (help < 2) {
            System.err.println("Use --help for more detailed information.");
        } else if (help == 2) {
            System.err.println("<-query BLAST_Output_filename>: The resulting tabular file from a BLAST where the genome was run against the database, such as nr.");
            System.err.println("<-nodes nodes_filename>: Lists the taxonomy ID, the taxonomy ID of its parent, and the taxonomy rank in a tab-delimited file.");
            System.err.println("[-names taxonomy_to_names_filename]: Lists the Taxonomy ID and the name of that rank in a tab-delimited file. If this is provided, extra details are shown.");
            System.err.println("<-tax taxonomy_id>: The Taxnomy ID of the genome being queried.");
        }
    }
}