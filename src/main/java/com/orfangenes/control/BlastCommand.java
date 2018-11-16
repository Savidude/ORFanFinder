package com.orfangenes.control;

import com.orfangenes.util.Constants;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import static com.orfangenes.util.Constants.*;

/**
 *  This class looks for the similar DNA/Protein sequences in the NCBI database
 *  by running the blast command.
 */
@Slf4j
@Builder
public class BlastCommand extends Thread {

    private String fileNumber;
    private String sequenceType;
    private String out;
    private String max_target_seqs;
    private String evalue;

    @Override
    public void run() {
        final String programme = (sequenceType.equals(Constants.TYPE_PROTEIN))? "blastp": "blastn";
        List<String> command = Arrays.asList(
                "/usr/local/ncbi/blast/bin/" + programme,
                "-query", out + "/" + SEQUENCE + this.fileNumber + FASTA_EXT,
                "-db", "nr",
                "-outfmt", "6 qseqid sseqid pident length mismatch gapopen qstart qend sstart send evalue bitscore staxids",
                "-max_target_seqs", this.max_target_seqs,
                "-evalue", this.evalue,
                "-out", this.out + "/" + BLAST_RESULTS + this.fileNumber + BLAST_EXT,
                "-remote");
        try {
            log.info("Executing Blast Command:{}", command.toString());
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            Process process = processBuilder.start();
//
//            String line;
//            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            while ((line = input.readLine()) != null) {
//                System.out.println(line);
//            }
//            input.close();

            // wait until the command get executed
            if (process.waitFor() != 0) {
                throw new RuntimeException("BLAST error occured");
            } else {
                log.info("BLAST successfully completed!!");
            }
        } catch (IOException ex) {
            log.error("IOError: " + ex.getMessage());
        } catch (InterruptedException ex) {
            log.error("InterruptedException: " + ex.getMessage());
        }
    }
}
