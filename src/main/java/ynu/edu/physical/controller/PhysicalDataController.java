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
import ynu.edu.physical.entity.PhysicalData;
import ynu.edu.physical.service.PhysicalDataService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/physicalData")
@Tag(description = "体检数据管理", name = "physicalData")
public class PhysicalDataController {

    @Autowired
    private PhysicalDataService physicalDataService;


    /**
     * 分页查询体检数据
     */
    @Operation(summary = "分页查询体检数据", description = "支持用户ID、时间、指标过滤")
    @GetMapping("/page")
    public R<Page<PhysicalData>> page(
            @ParameterObject Page<PhysicalData> page,
            @ParameterObject PhysicalData queryParams) {

        LambdaQueryWrapper<PhysicalData> wrapper = Wrappers.lambdaQuery();

        // 用户ID精准查询（非0有效）
        if (queryParams.getUserId() != null && queryParams.getUserId() > 0) {
            wrapper.eq(PhysicalData::getUserId, queryParams.getUserId());
        }

        // 体检时间模糊查询（格式：2025-03 或 2025-03-27）
        if (queryParams.getTime() != null && !queryParams.getTime().isEmpty()) {
            wrapper.like(PhysicalData::getTime, queryParams.getTime());
        }


        return R.ok(physicalDataService.page(page, wrapper));
    }


    /**
     * 新增体检数据
     */
    @Operation(summary = "新增体检数据", description = "必填：userId, time, 基础指标")
    @PostMapping("/save")
    public R<Boolean> save(@RequestBody PhysicalData physicalData) {
        // 校验必填字段
        if (physicalData.getUserId() == null || physicalData.getTime() == null) {
            return R.failed("userId 和 time 不能为空");
        }
        return R.ok(physicalDataService.save(physicalData));
    }


    /**
     * 修改体检数据（通过 ID）
     */
    @Operation(summary = "修改体检数据", description = "根据 ID 更新指定记录")
    @PutMapping("/update/{id}")
    public R<Boolean> update(
            @PathVariable("id") Long id,
            @RequestBody PhysicalData physicalData) {

        // 校验路径 ID 与请求体 ID 一致
        if (!id.equals(physicalData.getId())) {
            return R.failed("路径 ID 与请求体 ID 不一致");
        }
        return R.ok(physicalDataService.updateById(physicalData));
    }


    /**
     * 删除单个体检数据
     */
    @Operation(summary = "删除单个体检数据", description = "根据 ID 删除记录")
    @DeleteMapping("/delete/{id}")
    public R<Boolean> delete(@PathVariable("id") Long id) {
        return R.ok(physicalDataService.removeById(id));
    }


    /**
     * 批量删除体检数据
     */
    @Operation(summary = "批量删除体检数据", description = "通过 ID 列表删除多条记录")
    @PostMapping("/deleteBatch")
    public R<Boolean> deleteBatch(@RequestBody Long[] ids) {
        return R.ok(physicalDataService.removeBatchByIds(Arrays.asList(ids)));
    }


    /**
     * 根据 ID 列表查询（支持 IN/NOT IN）
     */
    @Operation(summary = "根据 ID 列表查询", description = "isIn=1 表示 IN，isIn=0 表示 NOT IN")
    @PostMapping("/listByIds/{isIn}")
    public R<List<PhysicalData>> listByIds(
            @PathVariable String isIn,
            @RequestBody(required = false) Long[] ids) { // ids 可选

        LambdaQueryWrapper<PhysicalData> wrapper = Wrappers.lambdaQuery();

        if (ids != null && ids.length > 0) {
            if ("1".equals(isIn)) {
                wrapper.in(PhysicalData::getId, ids);
            } else {
                wrapper.notIn(PhysicalData::getId, ids);
            }
        } else if ("1".equals(isIn)) {
            return R.ok(new ArrayList<>()); // IN 无 ID 时返回空
        }

        return R.ok(physicalDataService.list(wrapper));
    }


    /**
     * 导出 Excel（根据条件查询）
     */
//    @Operation(summary = "导出体检数据", description = "支持 ID 列表或查询条件过滤")
//    @GetMapping("/export")
//    public R<List<PhysicalData>> export(
//            @Parameter(description = "逗号分隔的 ID 列表，如 1,2,3")
//            @RequestParam(required = false) String ids,
//            @ParameterObject PhysicalData queryParams) {
//
//        LambdaQueryWrapper<PhysicalData> wrapper = Wrappers.lambdaQuery(queryParams);
//
//        if (ids != null && !ids.isEmpty()) {
//            List<Long> idList = Arrays.stream(ids.split(",")).map(Long::parseLong).toList();
//            wrapper.in(PhysicalData::getId, idList);
//        }
//
//        return R.ok(physicalDataService.list(wrapper));
//    }
}