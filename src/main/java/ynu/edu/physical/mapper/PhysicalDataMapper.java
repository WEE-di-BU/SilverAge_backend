package ynu.edu.physical.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ynu.edu.physical.entity.PhysicalData;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PhysicalDataMapper extends BaseMapper<PhysicalData> {
}