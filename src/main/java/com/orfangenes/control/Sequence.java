package com.orfangenes.control;

import com.orfangenes.constants.Constants;
import com.orfangenes.model.Gene;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Sequence {
    private String sequenceFileName;
    private List<Gene> genes;
    private int fileCount;

    private static final String GI = ">gi";

    public Sequence (String filename, String out, int inputTax) {
        File nodesFile = new File(filename);
        if (!nodesFile.exists()) {
            System.err.println("Failure to open sequence.");
            return;
        }
        this.genes = getGeneData(filename, inputTax);
        this.sequenceFileName = filename;
        this.fileCount = divideSequence(filename, out);
    }

    public void generateBlastFile(String type, String out, String max_target_seqs, String evalue) {
        List<String> command = new ArrayList<>();
        if (type.equals(Constants.TYPE_PROTEIN)) {
            command = Arrays.asList(
                    "blastp",
                    "-query",
                    sequenceFileName,
                    "-db",
                    "nr",
                    "-outfmt",
                    "6 qseqid sseqid pident length mismatch gapopen qstart qend sstart send evalue bitscore staxids",
                    "-max_target_seqs",
                    max_target_seqs,
                    "-evalue",
                    evalue,
                    "-out",
                    out + "/blastResults.bl",
                    "-remote"
            );
        } else if (type.equals(Constants.TYPE_NUCLEOTIDE)) {
            command = Arrays.asList(
                    "blastn",
                    "-query",
                    sequenceFileName,
                    "-db",
                    "nr",
                    "-outfmt",
                    "6 qseqid sseqid pident length mismatch gapopen qstart qend sstart send evalue bitscore staxids",
                    "-max_target_seqs",
                    max_target_seqs,
                    "-evalue",
                    evalue,
                    "-out",
                    out + "/blastResults.bl",
                    "-remote"
            );
        }

        try {
            // print the blast command to the terminal
            System.out.println("BLAST Command: " + command.toString());
            System.out.println("Be patient...This will take 2-15 min...");

            //execute the blast command
            ProcessBuilder pb = new ProcessBuilder(command);
            Process p = pb.start();

            String line;
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = input.readLine()) != null) {
                System.out.println(line);
            }
            input.close();

            // wait until the command get executed
            if (p.waitFor() != 0) {
                // error occured
                throw new RuntimeException("BLAST error occured");
            } else {
                System.out.println("BLAST successfully Completed!!");
            }
            System.out.println((p.exitValue() == 0) ? "Blast ran Successfully":"Blast Failed with " + p.exitValue() + " value");
        } catch (IOException ex) {
            System.err.println("IOError: " + ex.getMessage());
        } catch (InterruptedException ex) {
            System.err.println("InterruptedException: " + ex.getMessage());
        }
    }

    public List<Gene> getGenes() {
        return genes;
    }

    private ArrayList<Gene> getGeneData(String sequenceFileName, int inputTax) {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines( Paths.get(sequenceFileName), StandardCharsets.UTF_8))
        {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        String inputSequence = contentBuilder.toString();
        String[] sequences = inputSequence.split("\n\n");
        ArrayList<Gene> genes = new ArrayList<>();

        for (String sequence: sequences) {
            Gene gene = new Gene();
            gene.setTaxID(inputTax);

            String[] sequenceData = sequence.split("\\|");
            if (sequenceData.length > 1) {
                int offset = 2;
                if (sequenceData[0].equals(GI)) {
                    gene.setGeneID(Integer.parseInt(sequenceData[1]));
                    offset = 0;
                }

                String geneInfo = sequenceData[4 - offset];
                String[] lines = geneInfo.split("\n");
                gene.setDescription(lines[0]);

                String sequenceString = "";
                for (int i = 1; i < lines.length; i++) {
                    sequenceString += lines[i];
                }
                gene.setSequence(sequenceString);
                genes.add(gene);
            }
        }
        return genes;
    }

    private int divideSequence(String sequenceFileName, String out) {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines( Paths.get(sequenceFileName), StandardCharsets.UTF_8))
        {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        String inputSequence = contentBuilder.toString();
        String[] sequences = inputSequence.split("\n\n");

        String currentSequence = "";
        int fileNo = 1;
        for (double i = 0; i < (sequences.length - 1); i++) {
            if (((i / 2.0) == 1.0)) {
                createSequenceFile(out, currentSequence, fileNo);
                currentSequence = "";
                fileNo++;
            }

            currentSequence += sequences[(int)i] + "\n\n";
        }
        if (!currentSequence.equals("")) {
            createSequenceFile(out, currentSequence, fileNo);
        }
        return fileNo;
    }

    private void createSequenceFile(String out, String sequence, int fileNo) {
        try {
            PrintWriter writer = new PrintWriter(out + "/sequence" + Integer.toString(fileNo) + ".fasta", "UTF-8");
            writer.println(sequence);
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}
