package ynu.edu.model.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ynu.edu.physical.entity.PhysicalData;
import ynu.edu.physical.entity.PredictResult;
import ynu.edu.physical.service.PredictResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
public class ModelService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PredictResultService predictResultService;

    // Python模型接口地址（从配置文件读取）
    @Value("${python.model.cardiovascular-url}")
    private String cardiovascularUrl;

    @Value("${python.model.hypertension-url}")
    private String hypertensionUrl;

    @Value("${python.model.glycuresis-url}")
    private String glycuresisUrl;


    /**
     * 调用三个模型并保存结果
     */
    public PredictResult invokeModels(PhysicalData physicalData) {
        PredictResult result = new PredictResult();

        // 关联体检数据ID
        result.setPhysicalDataId(physicalData.getId());
        result.setUserId(physicalData.getUserId());
        result.setTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        // 调用心血管模型
        Map<String, Object> cardioResult = callCardiovascularModel(physicalData);
        result.setCardiovascularPredict(cardioResult.get("prediction").toString());
        result.setCardiovascularProbability((Double) cardioResult.get("probability"));

//        // 调用高血压模型
//        Map<String, Object> hypertensionResult = callPythonModel(
//                hypertensionUrl,
//                physicalDataToMap(physicalData)
//        );
//        result.setHypertensionPredict(hypertensionResult.get("预测类别").toString());
//        result.setHypertensionProbability((Double) hypertensionResult.get("患病概率"));
//
        // 调用糖尿病模型
        Map<String, Object> glycuresisResult = callGlycuresisModel(physicalData);
//        result.setGlycuresisPredict(glycuresisResult.get("prediction").toString());
        result.setGlycuresisProbability((Double) glycuresisResult.get("probability"));

        // 保存到数据库
        predictResultService.save(result);
        return result;
    }


    /**
     * 将 PhysicalData 转换为 Python 模型所需的 Map
     */
    // ======================= 心血管模型专用方法 =======================
    private Map<String, Object> callCardiovascularModel(PhysicalData data) {
        System.out.println(data);
        Map<String, Object> params = cardiovascularDataToMap(data);
        return restTemplate.postForObject(cardiovascularUrl, params, Map.class);
    }

    private Map<String, Object> cardiovascularDataToMap(PhysicalData data) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", data.getId());
        params.put("age", data.getAge());
        params.put("gender", data.getGender());
        params.put("height", data.getHeight());
        params.put("weight", data.getWeight());
        params.put("ap_hi", data.getApHi());
        params.put("ap_lo", data.getApLo());
        params.put("cholesterol", data.getCholesterol());
        params.put("gluc", data.getGlucose());
        params.put("smoke", data.getSmoke());
        params.put("alco", data.getAlcohol());
        params.put("active", data.getActive());
        params.put("cardio",0);
        System.out.println(params);
        return params;
    }

    // ======================= 糖尿病模型专用方法 =======================
    private Map<String, Object> callGlycuresisModel(PhysicalData data) {
//        System.out.println(data);
        Map<String, Object> params = glycuresisDataToMap(data);
        return restTemplate.postForObject(glycuresisUrl, params, Map.class);
    }

    private Map<String, Object> glycuresisDataToMap(PhysicalData data) {
        Map<String, Object> params = new HashMap<>();
        params.put("age", data.getAge());
        params.put("gender", data.getGender());
        params.put("ethrisk", data.getEthRisk());
        params.put("smoke_cat", data.getSmokeCat());
        params.put("fh_diab", data.getFhDiab());
        params.put("b_cvd", data.getCvd());
        params.put("b_treatedhyp", data.getTreatedhyp());
        params.put("b_learning", data.getLearning());
        params.put("b_manicschiz", data.getManicschiz());
        params.put("b_corticosteroids", data.getCorticosteroids());
        params.put("b_statin", data.getStatin());
        params.put("b_atypicalantipsy", data.getAtypicalantipsy());
        params.put("height", data.getHeight());
        params.put("weight", data.getWeight());
        params.put("town", data.getTown());
//        if (data.getHbalc() != null) {
//            params.put("hbalc", data.getHbalc());
//        }
//        if (data.getFbs() != null) {
//            params.put("fbs", data.getFbs());
//        }
        System.out.println(params);
        return params;
    }
}