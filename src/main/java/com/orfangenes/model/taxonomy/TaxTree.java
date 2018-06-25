package com.orfangenes.model.taxonomy;

import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

public class TaxTree {
    private ArrayList<Pair<TaxNode, Integer>> tempNodes = new ArrayList<>();
    private Map<Integer, TaxNode> nodes = new HashMap<>();

    private Map<Integer, String> names = new HashMap<>();

    public TaxTree(String nodesFilename, String namesFilename) {
        File nodesFile = new File(nodesFilename);
        if (!nodesFile.exists()) {
            System.err.println("Failure to open nodes.");
            return;
        }

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
    //scientific name	|

    private void processName(String nameString) {
        String[] nameData = nameString.split("\t\\|\t");
        String type = nameData[3];
        if (type.equals("scientific name\t|")) {
            int taxID = Integer.parseInt(nameData[0]);
            String name = nameData[1];
            names.put(taxID, name);
        }
    }

    public TaxNode getGenusParent(int taxID) {
        TaxNode node = nodes.get(taxID);
        while (!node.getParent().getRank().equals("genus") && node.getParent() != null && node.getParent().getID() != 1){
            node = node.getParent();
        }

        return (node.getParent().getID() == 1) ? null : node.getParent();
    }

    public Map<Integer, String> getHeirarchyFromNode(int taxID) {
        TaxNode node = nodes.get(taxID);
        Map<Integer, String> hierarchy = new LinkedHashMap<>();

        while (node.getParent() != null && node.getParent().getID() != 1) {
            node = node.getParent();
            if (!node.getRank().equals("no rank")) {
                hierarchy.put(node.getID(), node.getRank());
            }
        }
        return hierarchy;
    }

    public TaxNode getNode (int taxID) {
        return this.nodes.get(taxID);
    }
}
