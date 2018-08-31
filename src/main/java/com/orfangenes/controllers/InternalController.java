package com.orfangenes.controllers;

import com.orfangenes.ORFanGenes;
import com.orfangenes.model.entities.InputSequence;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@Controller
public class InternalController {

    @Value("${data.outputdir}")
    private String outputdir;
    @Value("${data.namesfile}")
    private String namesfile;
    @Value("${data.nodesfile}")
    private String nodesfile;

    private static final String USERS = "users";
    private static final String INPUT_FASTA = "input.fasta";

    @RequestMapping(value = "/store", method = RequestMethod.POST)
    public String store(@Valid @ModelAttribute("sequence") InputSequence sequence, BindingResult result) {
        if (result.hasErrors()) {
            return "error";
        }

        String organismTaxID = sequence.getOrganismName().split("\\(")[1];
        organismTaxID = organismTaxID.substring(0, organismTaxID.length() - 1);
        int organismTax = Integer.parseInt(organismTaxID);

        if (outputdir.substring(outputdir.length() - 1).equals("/")) {
            outputdir = outputdir.substring(0, outputdir.length() -1 );
        }

        try {
            String sessionID = RandomStringUtils.randomAlphanumeric(16);
            String output = outputdir + "/" + USERS + "/" + sessionID;
            File f = new File(output);
            f.mkdir();

            String inputFile = output + "/" + INPUT_FASTA;
            FileOutputStream out = new FileOutputStream(inputFile);
            out.write(sequence.getGenesequence().getBytes());
            out.close();

            ORFanGenes.run(inputFile, output, organismTax, sequence.getType(), sequence.getMaxtargets(), sequence.getMaxevalue(), nodesfile, namesfile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "welcome";
    }
}
