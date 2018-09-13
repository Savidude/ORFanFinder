package com.orfangenes;

import com.orfangenes.constants.Constants;
import com.orfangenes.control.BlastResultsProcessor;
import com.orfangenes.control.Classifier;
import com.orfangenes.control.Sequence;
import com.orfangenes.model.Gene;
import com.orfangenes.model.ORFGene;
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
        if (arguments.get("-type").equals("")) {
            System.err.println("A blast type must be provided");
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
        if (arguments.get("-tax").equals("")) {
            System.err.println("A taxonomy ID must be provided");
            printHelp(1);
            return;
        }
        if (arguments.get("-max_target_seqs").equals("")) {
            System.err.println("The number of target sequences should be provided.");
            printHelp(1);
            return;
        }
        if (arguments.get("-evalue").equals("")) {
            System.err.println("E-value must be provided");
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
        String blastType = arguments.get("-type");
        if (!(blastType.equals(Constants.TYPE_PROTEIN) || blastType.equals(Constants.TYPE_NUCLEOTIDE))) {
            System.err.println("Blast type is invalid");
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

        run(arguments.get("-query"), arguments.get("-out"), organismTaxID, arguments.get("-type"),
                arguments.get("-max_target_seqs"), arguments.get("-evalue"), arguments.get("-nodes"), arguments.get("-names"));
    }

    public static void run(String query, String outputdir, int organismTaxID, String blastType, String max_target_seqs, String evalue, String nodes, String names) {
        // Generating BLAST file
        Sequence sequence = new Sequence(query, outputdir, organismTaxID);
        sequence.generateBlastFile(blastType, outputdir, max_target_seqs, evalue);

        TaxTree taxTree = new TaxTree(nodes, names);

        BlastResultsProcessor processor = new BlastResultsProcessor(outputdir);
        Classifier classifier = new Classifier(sequence, taxTree, organismTaxID);
        Map<Gene, String> geneClassification = classifier.getGeneClassification(processor.getBlastResults());
        generateResult(geneClassification, outputdir, processor, taxTree, sequence.getGenes());
    }

    private static void generateResult (Map<Gene, String> geneClassification, String out,
                                        BlastResultsProcessor blastResultsProcessor, TaxTree tree, List<Gene> inputGenes) {
        JSONArray orfanGenes = new JSONArray();

        // ORFan and Native Gene count
        Map<String, Integer> orfanGeneCount = new LinkedHashMap<>();
        orfanGeneCount.put(Constants.MULTI_DOMAIN_GENE, 0);
        orfanGeneCount.put(Constants.DOMAIN_RESTRICTED_GENE, 0);
        orfanGeneCount.put(Constants.KINGDOM_RESTRICTED_GENE, 0);
        orfanGeneCount.put(Constants.PHYLUM_RESTRICTED_GENE, 0);
        orfanGeneCount.put(Constants.CLASS_RESTRICTED_GENE, 0);
        orfanGeneCount.put(Constants.ORDER_RESTRICTED_GENE, 0);
        orfanGeneCount.put(Constants.FAMILY_RESTRICTED_GENE, 0);
        orfanGeneCount.put(Constants.GENUS_RESTRICTED_GENE, 0);
        orfanGeneCount.put(Constants.ORFAN_GENE, 0);
        orfanGeneCount.put(Constants.STRICT_ORFAN, 0);

        // Iterating through every identified gene
        Iterator it = geneClassification.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();

            ORFGene orfGene = new ORFGene((Gene) pair.getKey(), (String)pair.getValue());
            JSONObject orfanJSON = new JSONObject();
            orfanJSON.put("geneid", orfGene.getId());
            orfanJSON.put("description", orfGene.getDescription());
            orfanJSON.put("orfanLevel", orfGene.getLevel());
            orfanJSON.put("taxonomyLevel", orfGene.getTaxonomy());
            orfanGenes.add(orfanJSON);

            int count = orfanGeneCount.get(orfGene.getLevel());
            count++;
            orfanGeneCount.put(orfGene.getLevel(), count);
        }
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
        JSONArray orfanGenesSummary = new JSONArray();

        JSONObject chartJSON = new JSONObject();
        JSONArray x = new JSONArray();
        JSONArray y = new JSONArray();

        it = orfanGeneCount.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();

            JSONObject summaryObject = new JSONObject();
            summaryObject.put("type", pair.getKey());
            summaryObject.put("count", pair.getValue());
            orfanGenesSummary.add(summaryObject);

            x.add(pair.getKey());
            y.add(pair.getValue());
        }

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
        JSONArray blastTrees = blastResultsProcessor.generateBlastResultsTrees(tree, inputGenes);
        try {
            StringWriter writer3 = new StringWriter();
            blastTrees.writeJSONString(writer3);
            String blastResults = writer3.toString();
            PrintWriter printWriter3 = new PrintWriter(out + "/blastresults.json", "UTF-8");
            printWriter3.println(blastResults);
            printWriter3.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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