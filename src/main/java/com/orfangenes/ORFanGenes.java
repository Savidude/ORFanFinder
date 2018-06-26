package com.orfangenes;

import com.orfangenes.constants.Constants;
import com.orfangenes.control.BlastResultsProcessor;
import com.orfangenes.control.Classifier;
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
        if (arguments.get("-names").equals("")) {
            System.err.println("A names file must be provided");
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
        if (arguments.get("-out").equals("")) {
            System.err.println("An output directory must be provided");
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
        File names = new File(arguments.get("-names"));
        if (!names.exists()) {
            System.err.println("Names file is invalid.");
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
        File out = new File(arguments.get("-out"));
        if (!out.exists()) {
            System.err.println("Output directory is invalid");
            return;
        }
        if ((arguments.get("-out").substring(arguments.get("-out").length() - 1)).equals("/")) {
            arguments.put("-out", arguments.get("-out").substring(0, arguments.get("-out").length() -1));
        }

        // Generating BLAST file
        Sequence sequence = new Sequence(arguments.get("-query"));
//        sequence.generateBlastFile(arguments.get("-out"));
//
//        ArrayList<Integer> inputIDs = sequence.getGIDs();
        TaxTree taxTree = new TaxTree(arguments.get("-nodes"), arguments.get("-names"));
//        TaxNode genusNode = taxTree.getGenusParent(organismTaxID);
        Lineage taxLineage = new Lineage(arguments.get("-lineage"));

        // Generating ORFanGenes result
//        Map<Integer, Boolean> geneClassification = getGeneClassification(organismTaxID, inputIDs, genusNode, taxLineage, arguments.get("-out"));
//        generateResult(geneClassification, sequence, arguments.get("-out"));

        /*
        1. Get BLAST results.
        2. Get non-duplicate tax IDs for each gid in sequence
        3. Get lineage for each tax ID
        4. For each tax ID's lineage, check where it matches in the input organism's taxonomy hierarchy
         */
        BlastResultsProcessor processor = new BlastResultsProcessor(arguments.get("-out"));
        Classifier classifier = new Classifier(sequence, taxTree, taxLineage, organismTaxID);
        Map<Integer, String> geneClassification = classifier.getGeneClassification(processor.getBlastResults());
        generateResult(geneClassification, sequence, arguments.get("-out"), processor, taxTree);
    }

    private static void generateResult (Map<Integer, String> geneClassification, Sequence sequence, String out,
                                        BlastResultsProcessor blastResultsProcessor, TaxTree tree) {
        JSONObject orfanGenes = new JSONObject();
        JSONArray geneInfo = new JSONArray();

        // ORFan and Native Gene count
        Map<String, Integer> orfanGeneCount = new LinkedHashMap<>();
        orfanGeneCount.put(Constants.KINGDOM_ORFAN, 0);
        orfanGeneCount.put(Constants.PHYLUM, 0);
        orfanGeneCount.put(Constants.CLASS, 0);
        orfanGeneCount.put(Constants.ORDER, 0);
        orfanGeneCount.put(Constants.FAMILY, 0);
        orfanGeneCount.put(Constants.GENUS, 0);
        orfanGeneCount.put(Constants.SPECIES, 0);
        orfanGeneCount.put(Constants.NATIVE, 0);

        // Iterating through every identified gene
        Iterator it = geneClassification.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();

            ORFGene orfGene = new ORFGene(sequence, (Integer)pair.getKey(), (String)pair.getValue());
            JSONArray gene = new JSONArray();
            gene.add(orfGene.getId());
            gene.add(orfGene.getDescription());
            gene.add(orfGene.getLevel());
            gene.add("Bacteria");
            geneInfo.add(gene);

            switch (orfGene.getLevel()) {
                case Constants.NATIVE_GENE: {
                    int count = orfanGeneCount.get(Constants.NATIVE);
                    count++;
                    orfanGeneCount.put(Constants.NATIVE, count);
                    break;
                }
                case Constants.SPECIES_ORFAN: {
                    int count = orfanGeneCount.get(Constants.SPECIES);
                    count++;
                    orfanGeneCount.put(Constants.SPECIES, count);
                    break;
                }
                case Constants.GENUS_ORFAN: {
                    int count = orfanGeneCount.get(Constants.GENUS);
                    count++;
                    orfanGeneCount.put(Constants.GENUS, count);
                    break;
                }
                case Constants.FAMILY_ORFAN: {
                    int count = orfanGeneCount.get(Constants.FAMILY);
                    count++;
                    orfanGeneCount.put(Constants.FAMILY, count);
                    break;
                }
                case Constants.ORDER_ORFAN: {
                    int count = orfanGeneCount.get(Constants.ORDER);
                    count++;
                    orfanGeneCount.put(Constants.ORDER, count);
                    break;
                }
                case Constants.CLASS_ORFAN: {
                    int count = orfanGeneCount.get(Constants.CLASS);
                    count++;
                    orfanGeneCount.put(Constants.CLASS, count);
                    break;
                }
                case Constants.PHYLUM_ORFAN: {
                    int count = orfanGeneCount.get(Constants.PHYLUM);
                    count++;
                    orfanGeneCount.put(Constants.PHYLUM, count);
                    break;
                }
                case Constants.STRICT_ORFAN: {
                    int count = orfanGeneCount.get(Constants.KINGDOM_ORFAN);
                    count++;
                    orfanGeneCount.put(Constants.KINGDOM_ORFAN, count);
                    break;
                }
            }
        }
        orfanGenes.put("data", geneInfo);
        // Writing orfanGenes data JSON data into file
        try {
            StringWriter writer = new StringWriter();
            orfanGenes.writeJSONString(writer);
            String orfanGenesData = writer.toString();

            PrintWriter printWriter = new PrintWriter(out + "/ORFanGenes.json", "UTF-8");
            printWriter.println(orfanGenesData);
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Genetating ORFan genes summary data and data to be displayed in the chart
        JSONObject orfanGenesSummary = new JSONObject();
        JSONArray summaryInfo = new JSONArray();

        JSONObject chartJSON = new JSONObject();
        JSONArray x = new JSONArray();
        JSONArray y = new JSONArray();

        it = orfanGeneCount.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();

            JSONArray array = new JSONArray();
            array.add(pair.getKey());
            array.add(pair.getValue());
            summaryInfo.add(array);

            x.add(pair.getKey());
            y.add(pair.getValue());
        }
        orfanGenesSummary.put("data", summaryInfo);

        chartJSON.put("x", x);
        chartJSON.put("y", y);

        // Writing orfanGenesSummary JSON data to file
        try {
            StringWriter writer = new StringWriter();
            orfanGenesSummary.writeJSONString(writer);
            String orfanGenesSummaryString = writer.toString();

            PrintWriter printWriter = new PrintWriter(out + "/ORFanGenesSummary.json", "UTF-8");
            printWriter.println(orfanGenesSummaryString);
            printWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Writing chartJSON  data into file
        try {
            StringWriter writer2 = new StringWriter();
            chartJSON.writeJSONString(writer2);
            String orfanGenesSummaryChart = writer2.toString();
            PrintWriter printWriter2 = new PrintWriter(out + "/ORFanGenesSummaryChart.json", "UTF-8");
            printWriter2.println(orfanGenesSummaryChart);
            printWriter2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Writing blast results to JSON file
        try {
            StringWriter writer3 = new StringWriter();
            blastResultsProcessor.generateTableData(tree).writeJSONString(writer3);
            String blastResults = writer3.toString();
            PrintWriter printWriter3 = new PrintWriter(out + "/blastresults.json", "UTF-8");
            printWriter3.println(blastResults);
            printWriter3.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Map<Integer, Boolean> getGeneClassification(int organismTaxID, ArrayList<Integer> inputIDs,
                                                               TaxNode genusNode, Lineage taxLineage, String out) {
        Map<Integer, Boolean> geneClassification = new LinkedHashMap<>();
        ArrayList<BlastResult> blastResults = getBlastResults(out + "/" + BlastResultsProcessor.BLAST_RESULTS_FILE);
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
            System.err.println("[-names taxonomy_to_names_filename]: Lists the TaxName ID and the name of that rank in a tab-delimited file. If this is provided, extra details are shown.");
            System.err.println("<-tax taxonomy_id>: The Taxnomy ID of the genome being queried.");
        }
    }
}