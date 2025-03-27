package ynu.edu.physical.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;
import ynu.edu.common.R; // 导入 R 类
import ynu.edu.physical.entity.PhysicalData;
import ynu.edu.physical.service.PhysicalDataService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/physicalData")
@Tag(description = "physicalData", name = "体检数据")
public class PhysicalDataController {

    @Autowired
    private PhysicalDataService physicalDataService;

    /**
     * 根据idList查询出所有 PhysicalData
     * @param ids idList 如果是notIn且idList没有传递则返回所有数据,如果是in且idList没有传递则返回空
     *            isIn 为0表示not in 为1表示in
     * @return
     */
    @Operation(summary = "根据idList查询", description = "根据idList查询")
    @PostMapping("/listByIds/{isIn}")
    public R getPhysicalDataListByIds(@RequestBody Long[] ids, @PathVariable String isIn) {
        List<Long> longs = Arrays.asList(ids);
        LambdaQueryWrapper<PhysicalData> wrapper = Wrappers.lambdaQuery();
        if (ids.length > 0) {
            if (isIn.equals("1")) {
                wrapper.in(PhysicalData::getId, longs);
            } else if (isIn.equals("0")) {
                wrapper.notIn(PhysicalData::getId, longs);
            }
        } else {
            if (isIn.equals("1")) {
                return R.ok(new ArrayList<>());
            }
        }
        return R.ok(physicalDataService.list(wrapper));
    }

    /**
     * 分页查询
     * @param page 分页对象
     * @param physicalData 体检数据
     * @return
     */
    @Operation(summary = "分页查询", description = "分页查询")
    @GetMapping("/page")
    public R getPhysicalDataPage(@ParameterObject Page page, @ParameterObject PhysicalData physicalData) {
        LambdaQueryWrapper<PhysicalData> wrapper = Wrappers.lambdaQuery();
        // 使用实体类中存在的字段（例如 year）进行模糊查询
        wrapper.like(physicalData.getYear() != null && !physicalData.getYear().isEmpty(),
                PhysicalData::getYear,
                physicalData.getYear());
        // 添加对 userId 的模糊查询条件
        if (physicalData.getUserId() != 0) {
            String userIdStr = String.valueOf(physicalData.getUserId());
            wrapper.like(true, PhysicalData::getUserId, userIdStr);
        }
        return R.ok(physicalDataService.page(page, wrapper));
    }

    /**
     * 通过id查询体检数据
     * @param id id
     * @return R
     */
    @Operation(summary = "通过id查询", description = "通过id查询")
    @GetMapping("/{id}")
    public R getById(@PathVariable("id") Long id) {
        return R.ok(physicalDataService.getById(id));
    }

    /**
     * 新增体检数据
     * @param physicalData 体检数据
     * @return R
     */
    @Operation(summary = "新增体检数据", description = "新增体检数据")
    @PostMapping
    public R save(@RequestBody PhysicalData physicalData) {
        return R.ok(physicalDataService.save(physicalData));
    }

    /**
     * 修改体检数据
     * @param physicalData 体检数据
     * @return R
     */
    @Operation(summary = "修改体检数据", description = "修改体检数据")
    @PutMapping
    public R updateById(@RequestBody PhysicalData physicalData) {
        return R.ok(physicalDataService.updateById(physicalData));
    }

    /**
     * 通过id删除体检数据
     * @param ids id列表
     * @return R
     */
    @Operation(summary = "通过id删除体检数据", description = "通过id删除体检数据")
    @DeleteMapping
    public R removeById(@RequestBody Long[] ids) {
        return R.ok(physicalDataService.removeBatchByIds(Arrays.asList(ids)));
    }

    /**
     * 导出excel 表格
     * @param physicalData 查询条件
     * @param ids 导出指定ID
     * @return excel 文件流
     */
    @GetMapping("/export")
    public List<PhysicalData> export(PhysicalData physicalData, Long[] ids) {
        return physicalDataService.list(Wrappers.lambdaQuery(physicalData).in(ids != null && ids.length > 0, PhysicalData::getId, ids));
    }
}