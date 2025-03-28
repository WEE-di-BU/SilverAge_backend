package ynu.edu.model.controller;

import org.springframework.web.bind.annotation.*;
import ynu.edu.common.R;
import ynu.edu.physical.entity.PhysicalData;
import ynu.edu.physical.entity.PredictResult;
import ynu.edu.model.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/model")
public class ModelController {

    @Autowired
    private ModelService modelService;


    /**
     * 单个体检数据预测（POST）
     */
    @PostMapping("/predict")
    public R<PredictResult> predict(@RequestBody PhysicalData physicalData) {
        return R.ok(modelService.invokeModels(physicalData));
    }


    /**
     * 批量体检数据预测（POST）
     */
    @PostMapping("/predictBatch")
    public R<Integer> predictBatch(@RequestBody PhysicalData[] physicalDataList) {
        int successCount = 0;
        for (PhysicalData data : physicalDataList) {
            try {
                modelService.invokeModels(data);
                successCount++;
            } catch (Exception e) {
                // 记录失败日志
            }
        }
        return R.ok(successCount);
    }
}