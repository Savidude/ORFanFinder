package com.orfangenes.control;

import com.orfangenes.util.Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Sequence {
    public static final String BLAST_RESULTS_FILE = "blastResults.bl";

    private String sequenceFileName;

    public Sequence (String filename) {
        File nodesFile = new File(filename);
        if (!nodesFile.exists()) {
            System.err.println("Failure to open sequence.");
            return;
        }
        this.sequenceFileName = filename;
    }

    public void generateBlastFile() {
//        String blastCommand = "blastp -db nr -query " + this.sequenceFileName + " -out blastResults2.bl " +
//                "-outfmt \"6 qseqid sseqid pident length mismatch gapopen qstart qend sstart send evalue bitscore staxids\" " +
//                "-remote -max_target_seqs 1000 -evalue 1e-3";
//
//        StringBuffer output = new StringBuffer();
//        Process process;
//        try {
//            process = Runtime.getRuntime().exec(blastCommand);
//            process.waitFor();
//
//            BufferedReader reader =
//                    new BufferedReader(new InputStreamReader(process.getInputStream()));
//
//            String line = "";
//            while ((line = reader.readLine())!= null) {
//                output.append(line + "\n");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println(output.toString());

        Map<String, String> settings = Util.getSettings();
        List<String> command = Arrays.asList(
                settings.get("blast"),
                "-query",
                sequenceFileName,
                "-db",
                "nr",
                "-outfmt",
                "6 qseqid sseqid pident length mismatch gapopen qstart qend sstart send evalue bitscore staxids",
                "-max_target_seqs",
                settings.get("defalt_maxtargetseq"),
                "-evalue",
                settings.get("defalt_maxevalue"),
                "-out",
                "blastResults.bl", //TODO: use working directory: settings.get("workingdir") + "blastResults.bl"
                "-remote"
        );

        try {
            // print the blast command to the terminal
            System.out.println("BLAST Command: " + command.toString());
            System.out.println("Be patient...This will take 2-15 min...");

            //execute the blast command
            ProcessBuilder pb = new ProcessBuilder(command);
            Process p = pb.start();

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

    public ArrayList<Integer> getGIDs() {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines( Paths.get(this.sequenceFileName), StandardCharsets.UTF_8))
        {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        String inputSequence = contentBuilder.toString();
        String[] sequences = inputSequence.split("\n\n");
        ArrayList<Integer> ids = new ArrayList<>();
        for (String sequence: sequences) {
            String[] sequenceData = sequence.split("\\|");
            if (sequenceData.length > 1) {
                ids.add(Integer.parseInt(sequenceData[1]));
            }
        }

        return ids;
    }

    public String getSeqenceFromGID (int gid) {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(this.sequenceFileName), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String inputSequence = contentBuilder.toString();
        String[] sequences = inputSequence.split("\n\n");
        for (String sequence : sequences) {
            String[] sequenceData = sequence.split(" ");
            String[] sequenceIDs = sequenceData[0].split("\\|");
            int sequenceGID = Integer.parseInt(sequenceIDs[1]);
            if (gid == sequenceGID) {
                return sequenceData[0].replace(">", "");
            }
        }
        return null;
    }

    public String getDescriptionFromGID (int gid) {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(this.sequenceFileName), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String inputSequence = contentBuilder.toString();
        String[] sequences = inputSequence.split("\n\n");
        for (String sequence : sequences) {
            String[] lines = sequence.split("\n");
            String header = lines[0];
            String[] headerData = header.split("\\|");
            int sequenceGID = Integer.parseInt(headerData[1]);
            if (gid == sequenceGID) {
                String description = headerData[4];
                return description.substring(1);
            }
        }
        return null;
    }
}
