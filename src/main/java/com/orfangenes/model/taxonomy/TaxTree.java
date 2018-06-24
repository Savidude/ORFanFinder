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

    public TaxTree(String filename) {
        File nodesFile = new File(filename);
        if (!nodesFile.exists()) {
            System.err.println("Failure to open nodes.");
            return;
        }

        // Reading the nodes file line by line
        try (Stream<String> lines = Files.lines(Paths.get(filename), Charset.defaultCharset())) {
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
        String taxID = nodeData[0];
        String parentID = nodeData[1];
        String rank = nodeData[2];
        TaxNode node = new TaxNode(Integer.parseInt(taxID), rank);

        this.tempNodes.add(new Pair<>(node, Integer.parseInt(parentID)));
        this.nodes.put(Integer.parseInt(taxID), node);
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
}
