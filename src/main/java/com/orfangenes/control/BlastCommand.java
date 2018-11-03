package com.orfangenes.control;

import com.orfangenes.constants.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlastCommand extends Thread {
    private static final String SEQUENCE = "sequence";
    private static final String FASTA_EXT = ".fasta";
    private static final String BLAST_RESULTS = "blastResults";
    private static final String BLAST_EXT = ".bl";

    private String fileNo;
    private String type;
    private String out;
    private String max_target_seqs;
    private String evalue;


    public BlastCommand(String fileNo, String type, String out, String max_target_seqs, String evalue) {
        this.fileNo = fileNo;
        this.type = type;
        this.out = out;
        this.max_target_seqs = max_target_seqs;
        this.evalue = evalue;
    }

    @Override
    public void run() {
        List<String> command = new ArrayList<>();
        if (type.equals(Constants.TYPE_PROTEIN)) {
            command = Arrays.asList(
                    "/usr/local/ncbi/blast/bin/blastp",
                    "-query",
                    out + "/" + SEQUENCE + this.fileNo + FASTA_EXT,
                    "-db",
                    "nr",
                    "-outfmt",
                    "6 qseqid sseqid pident length mismatch gapopen qstart qend sstart send evalue bitscore staxids",
                    "-max_target_seqs",
                    this.max_target_seqs,
                    "-evalue",
                    this.evalue,
                    "-out",
                    this.out + "/" + BLAST_RESULTS + this.fileNo + BLAST_EXT,
                    "-remote"
            );
        } else if (type.equals(Constants.TYPE_NUCLEOTIDE)) {
            command = Arrays.asList(
                    "/usr/local/ncbi/blast/bin/blastn",
                    "-query",
                    SEQUENCE + this.fileNo + FASTA_EXT,
                    "-db",
                    "nr",
                    "-outfmt",
                    "6 qseqid sseqid pident length mismatch gapopen qstart qend sstart send evalue bitscore staxids",
                    "-max_target_seqs",
                    this.max_target_seqs,
                    "-evalue",
                    this.evalue,
                    "-out",
                    this.out + "/" + BLAST_RESULTS + this.fileNo + BLAST_EXT,
                    "-remote"
            );
        }

        try {
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
}
