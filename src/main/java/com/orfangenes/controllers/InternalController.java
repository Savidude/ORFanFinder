package com.orfangenes.controllers;

import com.orfangenes.ORFanGenes;
import com.orfangenes.model.entities.InputSequence;
import com.orfangenes.util.FileHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import static com.orfangenes.util.Constants.*;

@Slf4j
@Controller
public class InternalController {

  @Value("${data.outputdir}")
  private String outputdir;

  @PostMapping("/analyse")
  public String analyse(@Valid @ModelAttribute("sequence") InputSequence sequence, BindingResult result, Model model) {

      Assert.assertFalse("Error", result.hasErrors());
      final String sessionID = RandomStringUtils.randomAlphanumeric(16);
      String outputPath = outputdir + sessionID;
      String inputFilePath = outputPath + "/" + INPUT_FASTA;

      String organismTaxID = sequence.getOrganismName().split("\\(")[1];
      organismTaxID = organismTaxID.substring(0, organismTaxID.length() - 1);
      int organismTax = Integer.parseInt(organismTaxID);
    try{
      FileHandler.createResultsOutputDir(outputPath);
      FileHandler.saveInputSequence(outputPath, sequence);
      ORFanGenes.run(
              inputFilePath,
              outputPath,
              organismTax,
              sequence.getType(),
              sequence.getMaxTargetSequence(),
              sequence.getMaxEvalue());
    } catch (Exception e) {
     log.error("Analysis Failed: " + e.getMessage());
    }
    return "redirect:/result?sessionid=" + sessionID;
  }

  @PostMapping("/data/summary")
  @ResponseBody
  public String getSummary(@RequestBody Map<String, Object> payload) {
    final String sessionID = (String) payload.get("sessionid");
    String output = outputdir + sessionID;
    return FileHandler.readJSONAsString(output + "/" + FILE_OUTPUT_ORFAN_GENES_SUMMARY);
  }

  @PostMapping("/data/summary/chart")
  @ResponseBody
  public String getSummaryChart(@RequestBody Map<String, Object> payload) {
    final String sessionID = (String) payload.get("sessionid");
    String output = outputdir + sessionID;
    return FileHandler.readJSONAsString(output + "/" + FILE_OUTPUT_ORFAN_GENES_SUMMARY_CHART);
  }

  @PostMapping("/data/genes")
  @ResponseBody
  public String getOrfanGenes(@RequestBody Map<String, Object> payload) {
    final String sessionID = (String) payload.get("sessionid");
    String output = outputdir + sessionID;
    return FileHandler.readJSONAsString(output + "/" + FILE_OUTPUT_ORFAN_GENES);
  }

  @PostMapping("/data/blast")
  @ResponseBody
  public String getBlast(@RequestBody Map<String, Object> payload) {
    final String sessionID = (String) payload.get("sessionid");
    final int id = (Integer) payload.get("id");
    String output = outputdir + sessionID;
    return FileHandler.blastToJSON(output + "/" + FILE_OUTPUT_BLAST_RESULTS, id);
  }
}
