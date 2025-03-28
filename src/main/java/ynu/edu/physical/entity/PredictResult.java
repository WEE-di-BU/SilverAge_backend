package ynu.edu.physical.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("predict_result")
public class PredictResult {
    private Integer id;                  // 自增主键
    private Integer userId;              // 用户ID
    private Integer physicalDataId;      // 关联体检数据ID（新增）
    private String time;                 // 预测时间（格式：yyyy-MM-dd HH:mm:ss）
    private String cardiovascularPredict; // 心血管预测结果（如："低风险"）
    private Double cardiovascularProbability; // 心血管患病概率（新增）
    private String hypertensionPredict;   // 高血压预测结果
    private Double hypertensionProbability;   // 高血压患病概率
    private String glycuresisPredict;     // 糖尿病预测结果
    private Double glycuresisProbability;     // 糖尿病患病概率
}