package com.orfangenes.controllers;

import com.orfangenes.ORFanGenes;
import com.orfangenes.model.entities.InputSequence;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

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
    public String store(@Valid @ModelAttribute("sequence") InputSequence sequence, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "error";
        }

        String organismTaxID = sequence.getOrganismName().split("\\(")[1];
        organismTaxID = organismTaxID.substring(0, organismTaxID.length() - 1);
        int organismTax = Integer.parseInt(organismTaxID);

        if (outputdir.substring(outputdir.length() - 1).equals("/")) {
            outputdir = outputdir.substring(0, outputdir.length() -1 );
        }
        String sessionID = RandomStringUtils.randomAlphanumeric(16);

        try {
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
        return "redirect:/result?sessionid=" + sessionID;
    }

    @RequestMapping(value = "/data/summary", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String getSummary(@RequestBody Map<String, Object> payload) {
        String sessionID = (String) payload.get("sessionid");

        if (outputdir.substring(outputdir.length() - 1).equals("/")) {
            outputdir = outputdir.substring(0, outputdir.length() -1 );
        }
        String output = outputdir + "/" + USERS + "/" + sessionID;
        try {
            return new String(Files.readAllBytes(Paths.get(output + "/ORFanGenesSummary.json")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/data/summary/chart", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String getSummaryChart(@RequestBody Map<String, Object> payload) {
        String sessionID = (String) payload.get("sessionid");

        if (outputdir.substring(outputdir.length() - 1).equals("/")) {
            outputdir = outputdir.substring(0, outputdir.length() -1 );
        }
        String output = outputdir + "/" + USERS + "/" + sessionID;
        try {
            return new String(Files.readAllBytes(Paths.get(output + "/ORFanGenesSummaryChart.json")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/data/genes", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String getOrfanGenes(@RequestBody Map<String, Object> payload) {
        String sessionID = (String) payload.get("sessionid");

        if (outputdir.substring(outputdir.length() - 1).equals("/")) {
            outputdir = outputdir.substring(0, outputdir.length() -1 );
        }
        String output = outputdir + "/" + USERS + "/" + sessionID;
        try {
            return new String(Files.readAllBytes(Paths.get(output + "/ORFanGenes.json")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/data/blast", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String getBlast(@RequestBody Map<String, Object> payload) {
        String sessionID = (String) payload.get("sessionid");

        if (outputdir.substring(outputdir.length() - 1).equals("/")) {
            outputdir = outputdir.substring(0, outputdir.length() -1 );
        }
        String output = outputdir + "/" + USERS + "/" + sessionID;
        try {
            return new String(Files.readAllBytes(Paths.get(output + "/blastresults.json")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
