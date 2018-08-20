package com.orfangenes.model.taxonomy;

import com.orfangenes.constants.Constants;
import javafx.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class TaxTree {
    private ArrayList<Pair<TaxNode, Integer>> tempNodes = new ArrayList<>();
    private Map<Integer, TaxNode> nodes = new HashMap<>();

    private Map<Integer, String> names = new HashMap<>();

    public TaxTree(String nodesFilename, String namesFilename) {
        // Reading the names file line by line
        try (Stream<String> lines = Files.lines(Paths.get(namesFilename), Charset.defaultCharset())) {
            lines.forEachOrdered(line -> processName(line));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Reading the nodes file line by line
        try (Stream<String> lines = Files.lines(Paths.get(nodesFilename), Charset.defaultCharset())) {
            lines.forEachOrdered(line -> processNode(line));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set nodes to their parents and construct tree
        for (Pair<TaxNode, Integer> node: tempNodes) {
            TaxNode parent = this.nodes.get(node.getValue());
            node.getKey().setParent(parent);
        }

//        Iterator it = this.names.entrySet().iterator();
//        try (PrintWriter out = new PrintWriter("names.txt")) {
//            while (it.hasNext()) {
//                Map.Entry pair = (Map.Entry)it.next();
//                String nameData = Integer.toString((Integer) pair.getKey()) + "\t|\t" + pair.getValue();
//                out.println(nameData);
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
    }

    private void processNode(String nodeString) {
        String[] nodeData = nodeString.split("\t\\|\t");
        int taxID = Integer.parseInt(nodeData[0]);
        int parentID = Integer.parseInt(nodeData[1]);
        String rank = nodeData[2];
        String name = names.get(taxID);
        TaxNode node = new TaxNode(taxID, name, rank);

        this.tempNodes.add(new Pair<>(node, parentID));
        this.nodes.put(taxID, node);
    }

    private void processName(String nameString) {
        String[] nameData = nameString.split("\t\\|\t");
        String type = nameData[3];
        if (type.equals("scientific name\t|")) {
            try {
                int taxID = Integer.parseInt(nameData[0]);
                String name = nameData[1];
                names.put(taxID, name);
            } catch (NumberFormatException e) {
                // Do nothing
            }
        }

//        String[] nameData = nameString.split("\t\\|\t");
//        int taxID = Integer.parseInt(nameData[0]);
//        String name = nameData[1];
//        this.names.put(taxID, name);
    }

    public Map<String, Integer> getHeirarchyFromNode(int taxID) {
        TaxNode node = nodes.get(taxID);
        Map<String, Integer> hierarchy = new LinkedHashMap<>();

        try {

            if (node.getRank().equals(Constants.SPECIES)) {
                hierarchy.put(Constants.SPECIES, node.getID());

                try {
                    while (node.getParent() != null && node.getParent().getID() != 1) {
                        node = node.getParent();
                        if (!node.getRank().equals("no rank") && !node.getRank().contains("sub") && !node.getRank().contains("parv") && !node.getRank().contains("infra")
                                && !node.getRank().contains("superphylum") && !node.getRank().contains("superclass") && !node.getRank().contains("superorder") && !node.getRank().contains("superfamily") && !node.getRank().contains("supergenus") && !node.getRank().contains("superspecies")) {
                            hierarchy.put(node.getRank(), node.getID());
                        }
                    }
                } catch (NullPointerException e) {
                    // Do nothing
                }
            }

            return hierarchy;
        } catch (NullPointerException e) {
            return null;
        }
    }

    public TaxNode getNode (int taxID) {
        return this.nodes.get(taxID);
    }
}
