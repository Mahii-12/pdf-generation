package com.pdfgen.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    private String name;
    private String quantity;
    private Double rate;
    private Double amount;
}
