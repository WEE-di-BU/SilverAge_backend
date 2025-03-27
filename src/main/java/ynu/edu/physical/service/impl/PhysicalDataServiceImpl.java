package ynu.edu.physical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import ynu.edu.physical.entity.PhysicalData;
import ynu.edu.physical.mapper.PhysicalDataMapper;
import ynu.edu.physical.service.PhysicalDataService;

@Service
public class PhysicalDataServiceImpl extends ServiceImpl<PhysicalDataMapper, PhysicalData> implements PhysicalDataService {}