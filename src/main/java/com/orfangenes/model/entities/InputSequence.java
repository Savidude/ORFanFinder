package com.orfangenes.model.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InputSequence {

    private String organismName;
    private String type;
    private String genesequence;
    private String maxevalue;
    private String maxtargets;
}
