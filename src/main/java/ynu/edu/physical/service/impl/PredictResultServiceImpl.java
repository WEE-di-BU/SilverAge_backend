package ynu.edu.physical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import ynu.edu.physical.entity.PredictResult;
import ynu.edu.physical.mapper.PredictResultMapper;
import ynu.edu.physical.service.PredictResultService;

@Service
public class PredictResultServiceImpl extends ServiceImpl<PredictResultMapper, PredictResult> implements PredictResultService {
}
