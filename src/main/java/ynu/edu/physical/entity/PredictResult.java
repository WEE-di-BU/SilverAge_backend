package ynu.edu.physical.entity;

import lombok.Data;

@Data
public class PredictResult {
    private int id;
    private int userId;
    private int year;
    private String cardiovascularPredict;
    private String hypertensionPredict;
    private String glycuresisPredict;
}
