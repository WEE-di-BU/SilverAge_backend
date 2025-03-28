package ynu.edu.physical.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;
import ynu.edu.common.R;
import ynu.edu.physical.entity.PredictResult;
import ynu.edu.physical.service.PredictResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/predictResult")
@Tag(description = "预测结果管理", name = "predictResult")
public class PredictResultController {

    @Autowired
    private PredictResultService predictResultService;


    /**
     * 分页查询
     */
    @Operation(summary = "分页查询预测结果", description = "支持用户ID、时间、疾病类型过滤")
    @GetMapping("/page")
    public R<Page<PredictResult>> page(
            @ParameterObject Page<PredictResult> page,
            @ParameterObject PredictResult queryParams) {

        LambdaQueryWrapper<PredictResult> wrapper = Wrappers.lambdaQuery();

        // 用户ID精准查询
        if (queryParams.getUserId() != null && queryParams.getUserId() > 0) {
            wrapper.eq(PredictResult::getUserId, queryParams.getUserId());
        }

        // 时间范围模糊查询（支持年月或年月日）
        if (queryParams.getTime() != null) {
            wrapper.like(PredictResult::getTime, queryParams.getTime());
        }

        // 疾病类型模糊查询（示例：心血管预测结果）
        if (queryParams.getCardiovascularPredict() != null) {
            wrapper.like(PredictResult::getCardiovascularPredict, queryParams.getCardiovascularPredict());
        }

        return R.ok(predictResultService.page(page, wrapper));
    }


    /**
     * 新增预测结果
     */
    @Operation(summary = "新增预测结果", description = "必填：userId, time")
    @PostMapping("/save")
    public R<Boolean> save(@RequestBody PredictResult predictResult) {
        // 校验必填字段
        if (predictResult.getUserId() == null || predictResult.getTime() == null) {
            return R.failed("userId 和 time 不能为空");
        }
        return R.ok(predictResultService.save(predictResult));
    }


    /**
     * 修改预测结果（通过 ID）
     */
    @Operation(summary = "修改预测结果", description = "根据 ID 更新指定记录")
    @PutMapping("/update/{id}")
    public R<Boolean> update(
            @PathVariable("id") Integer id,
            @RequestBody PredictResult predictResult) {

        // 校验路径 ID 与请求体 ID 是否一致
        if (!id.equals(predictResult.getId())) {
            return R.failed("路径 ID 与请求体 ID 不一致");
        }
        return R.ok(predictResultService.updateById(predictResult));
    }


    /**
     * 删除单个预测结果
     */
    @Operation(summary = "删除单个预测结果", description = "根据 ID 删除记录")
    @DeleteMapping("/delete/{id}")
    public R<Boolean> delete(@PathVariable("id") Integer id) {
        return R.ok(predictResultService.removeById(id));
    }


    /**
     * 批量删除预测结果
     */
    @Operation(summary = "批量删除预测结果", description = "通过 ID 列表删除多条记录")
    @PostMapping("/deleteBatch")
    public R<Boolean> deleteBatch(@RequestBody Integer[] ids) {
        return R.ok(predictResultService.removeBatchByIds(Arrays.asList(ids)));
    }


    /**
     * 根据用户 ID 查询最新预测结果
     */
//    @Operation(summary = "查询用户最新预测结果", description = "按时间倒序返回最近一条记录")
//    @GetMapping("/latest/{userId}")
//    public R<PredictResult> getLatestByUserId(@PathVariable("userId") Integer userId) {
//        return R.ok(predictResultService.getOne(
//                Wrappers.lambdaQuery<PredictResult>()
//                        .eq(PredictResult::getUserId, userId)
//                        .orderByDesc(PredictResult::getTime)
//                        .last("LIMIT 1")
//        ));
//    }
}