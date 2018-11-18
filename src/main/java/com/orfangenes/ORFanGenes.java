package com.orfangenes;

import com.orfangenes.control.ResultsGenerator;
import com.orfangenes.control.BlastResultsProcessor;
import com.orfangenes.control.Classifier;
import com.orfangenes.control.Sequence;
import com.orfangenes.model.Gene;
import com.orfangenes.model.taxonomy.TaxTree;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.*;
import static com.orfangenes.util.Constants.*;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

@Slf4j
public class ORFanGenes {

    public static void main (String[] args) {
        log.info("Starting application....");
        log.info("Program arguments: " + Arrays.toString(args));
        try {
            CommandLine cmd = ORFanGenes.parseArgs(args);
            run(    cmd.getOptionValue(ARG_QUERY),
                    cmd.getOptionValue(ARG_OUT),
                    Integer.parseInt(cmd.getOptionValue(ARG_QUERY)),
                    cmd.getOptionValue(ARG_TYPE),
                    cmd.getOptionValue(ARG_MAX_TARGET_SEQS),
                    cmd.getOptionValue(ARG_EVALUE));
            log.info("Analysis Completed!");
        } catch (ParseException e) {
            log.error("Error : ", e);
        }
    }

    public static void run(String query, String outputdir, int organismTaxID, String blastType, String max_target_seqs, String evalue) {

        final String namesFile = getFilePath("names.dmp");
        final String nodesFile = getFilePath("nodes.dmp");

        // Generating BLAST file
        Sequence sequence = new Sequence(blastType, query, outputdir, organismTaxID);
        sequence.generateBlastFile(outputdir, max_target_seqs, evalue);

        TaxTree taxTree = new TaxTree(nodesFile, namesFile);
        BlastResultsProcessor processor = new BlastResultsProcessor(outputdir);
        Classifier classifier = new Classifier(sequence, taxTree, organismTaxID);
        Map<Gene, String> geneClassification = classifier.getGeneClassification(processor.getBlastResults());
        ResultsGenerator.generateResult(geneClassification, outputdir, processor, taxTree, sequence.getGenes());
    }

    private static String getFilePath(String filename){
        String filepath = null;
        try {
            URL url = ORFanGenes.class.getClassLoader().getResource(filename);
            if (url == null) {
                throw new IllegalStateException(filename + " file not found!");
            }
            filepath = url.toURI().getPath();
        } catch (URISyntaxException e) {
                log.error("File not found");
        }
        return filepath;
    }


//    private static void printHelp(int help) {
//        if (help == 0) {
//            System.err.println("Incorrect number of command line arguments");
//            System.err.println("Usage: ORFanGenes.jar <-query BLAST_Output_filename> <-names taxonomy_to_names_filename> <-nodes nodes_filename> <-tax taxonomy_id>");
//        } else if (help < 2) {
//            System.err.println("Use --help for more detailed information.");
//        } else if (help == 2) {
//            System.err.println("<-query BLAST_Output_filename>: The resulting tabular file from a BLAST where the genome was run against the database, such as nr.");
//            System.err.println("<-nodes nodes_filename>: Lists the taxonomy ID, the taxonomy ID of its parent, and the taxonomy rank in a tab-delimited file.");
//            System.err.println("[-names taxonomy_to_names_filename]: Lists the TaxName ID and the name of that rank in a tab-delimited file. If this is provided, extra details are shown.");
//            System.err.println("<-tax taxonomy_id>: The Taxnomy ID of the genome being queried.");
//        }
//    }

    public static CommandLine parseArgs(String[] args) throws ParseException {
        Options options = new Options();
        options.addOption(ARG_QUERY,true, "Input Sequence file in FASTA format");
        options.addOption(ARG_TYPE,true, "Input Sequence type(protein/dna)");
        options.addOption(ARG_TAX,true, "NCBI taxonomy ID of the species");
        options.addOption(ARG_MAX_TARGET_SEQS,true, "Number of target sequences");
        options.addOption(ARG_EVALUE,true, "BLAST E-Value Threshold");
        options.addOption(ARG_OUT,true, "Output directory");
        CommandLineParser parser = new DefaultParser();
        return parser.parse(options, args);
//        Map<String, String> arguments = new HashMap<>();
//
////        // Displaying help messages for help requests and invalid arguments
////        for(int i = 0; i < args.length; i+=2) {
////            if (args[i].equals("-h")) {
////                printHelp(1);
////                return;
////            } else if (args[i].equals("--help")) {
////                printHelp(2);
////            } else {
////                if (args[i].charAt(0) != '-') {
////                    System.out.println("Invalid argument " + args[i]);
////                    printHelp(1);
////                    return;
////                } else {
////                    arguments.put(args[i], args[i+1]);
////                }
////            }
////        }
//
//        // Displaying error messages for empty arguments
//
//
//
//        // Displaying error messages for invalid files
//        File blast = new File(arguments.get("-query"));
//        if (!blast.exists()) {
//            System.err.println("Input sequence file is invalid.");
//            return;
//        }
//        String blastType = arguments.get("-type");
//        if (!(blastType.equals(Constants.TYPE_PROTEIN) || blastType.equals(Constants.TYPE_NUCLEOTIDE))) {
//            System.err.println("Blast type is invalid");
//            return;
//        }
//        File nodes = new File(arguments.get("-nodes"));
//        if (!nodes.exists()) {
//            System.err.println("Nodes file is invalid.");
//            return;
//        }
//        File names = new File(arguments.get("-names"));
//        if (!names.exists()) {
//            System.err.println("Names file is invalid.");
//            return;
//        }
//        int organismTaxID;
//        try {
//            organismTaxID = Integer.parseInt(arguments.get("-tax"));
//        } catch (NumberFormatException e) {
//            System.err.println("Organism TaxID must be an number.");
//            return;
//        }
//        File out = new File(arguments.get("-out"));
//        if (!out.exists()) {
//            System.err.println("Output directory is invalid");
//            return;
//        }
//        if ((arguments.get("-out").substring(arguments.get("-out").length() - 1)).equals("/")) {
//            arguments.put("-out", arguments.get("-out").substring(0, arguments.get("-out").length() -1));
//        }
    }
}