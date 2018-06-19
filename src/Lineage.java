import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Lineage {
    private Map<Integer, ArrayList<Integer>> taxonomies = new HashMap<>();

    public Lineage(String filename) {
        File lineageFile = new File(filename);
        if (!lineageFile.exists()) {
            System.err.println("Failure to open lineage.");
            return;
        }

        //Reading the lineage file line by line
        try (Stream<String> lines = Files.lines(Paths.get(filename), Charset.defaultCharset())) {
            lines.forEachOrdered(line -> processTaxonomy(line));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processTaxonomy(String line) {
        String[] taxData = line.split("\t\\|\t");
        String taxID = taxData[0];

        String lineageString = taxData[1];
        lineageString = lineageString.replace("\t|", "");
        String[] lineage =lineageString.split(" ");
        ArrayList<Integer> lineageList = new ArrayList<>();
        for (String tax: lineage) {
            try {
                lineageList.add(Integer.parseInt(tax));
            } catch (NumberFormatException e) {
                //Do nothing
            }
        }
        this.taxonomies.put(Integer.parseInt(taxID), lineageList);
    }

    public boolean isNodeInLineage(int subjectSequence, TaxNode queryGenus) {
        ArrayList<Integer> lineage = this.taxonomies.get(subjectSequence);
        return lineage.contains(queryGenus.getID());
    }
}
