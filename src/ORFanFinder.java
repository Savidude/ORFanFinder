import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class ORFanFinder {

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
            System.err.println("A BLAST file must be provided");
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
            System.err.println("BLAST file is invalid.");
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

        TaxTree taxTree = new TaxTree(arguments.get("-nodes"));
        Lineage taxLineage = new Lineage(arguments.get("-lineage"));
        try (Stream<String> lines = Files.lines(Paths.get(arguments.get("-query")), Charset.defaultCharset())) {
            lines.forEachOrdered(line -> processQuery(line, taxTree, taxLineage, organismTaxID));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processQuery(String line, TaxTree taxTree, Lineage lineage, int organismTaxID) {
        String[] blastResult = line.split("\t");
//        String querySequence = blastResult[0];
//        String subjectSequence = blastResult[1];

//        int queryTaxID = extractTaxIDFromSequence(querySequence);
//        TaxNode genusNode = taxTree.getGenusParent(queryTaxID);

//        int subjectTaxID = extractTaxIDFromSequence(subjectSequence);
//        if (lineage.isNodeInLineage(subjectTaxID, genusNode)) {
//            //TODO: Do something
//            return;
//        }

        String taxID = blastResult[9];
        if (taxID.contains(";")) {
            // If many tax IDs are obtained from the last result, get the first one
            String[] taxIDs = taxID.split(";");
            taxID = taxIDs[0];
        }
        int subjectTaxID = Integer.parseInt(taxID);
        TaxNode genusNode = taxTree.getGenusParent(organismTaxID);
        if (lineage.isNodeInLineage(subjectTaxID, genusNode)) {

        }
    }

    private static int extractTaxIDFromSequence(String sequence) {
        String[] sequenceResult = sequence.split("\\|");
        return  Integer.parseInt(sequenceResult[1]);
    }

    private static void printHelp(int help) {
        if (help == 0) {
            System.err.println("Incorrect number of command line arguments");
            System.err.println("Usage: ORFanFinder.jar <-query BLAST_Output_filename> <-names taxonomy_to_names_filename> <-nodes nodes_filename> <-tax taxonomy_id>");
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
