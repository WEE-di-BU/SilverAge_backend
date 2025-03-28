package ynu.edu.physical.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("physical_data")
public class PhysicalData {
    private Integer id;
    @TableField("user_id")
    private Integer userId;
    private Integer age;
    private Integer gender;
    private String time;
    private Integer height;
    private Integer weight;

    @TableField("ap_hi")
    private Integer apHi;
    @TableField("ap_li")
    private Integer apLo;

    private Integer cholesterol;
    private Integer glucose;
    private Integer smoke;
    private Integer alcohol;
    private Integer active;

    // 新增糖尿病相关字段（驼峰命名，对应数据库下划线）**
    @TableField("ethrisk")
    private Integer ethRisk;        // 种族分类

    @TableField("smoke_cat")
    private Integer smokeCat;       // 吸烟分类

    @TableField("fh_diab")
    private Integer fhDiab;         // 直系亲属是否患糖尿病（1=是，0=否）

    @TableField("b_cvd")
    private Integer cvd;           // 是否曾患心血管疾病（1=是，0=否）

    @TableField("b_treatedhyp")
    private Integer treatedhyp;    // 是否患需治疗的高血压（1=是，0=否）

    @TableField("b_learning")
    private Integer learning;      // 是否有学习障碍（1=是，0=否）

    @TableField("b_manicschiz")
    private Integer manicschiz;    // 是否有躁狂抑郁症或精神分裂症（1=是，0=否）

    @TableField("b_corticosteroids")
    private Integer corticosteroids; // 是否服用类固醇（1=是，0=否）

    @TableField("b_statin")
    private Integer statin;        // 是否服用他汀类药物（1=是，0=否）

    @TableField("b_atypicalantipsy")
    private Integer atypicalantipsy; // 是否服用非典型抗精神病药（1=是，0=否）

    @TableField("town")
    private Double town;            // 城镇变量（0-1 小数）

    // 可选字段（根据需求添加）**
    @TableField("hbalc")
    private Double hbalc;           // 糖化血红蛋白（mmol/L）

    @TableField("fbs")
    private Double fbs;             // 空腹血糖（mmol/L）
}