package cn.lzscxb;

import cn.lzscxb.business.service.impl.FengProblemQueueServiceImpl;
import cn.lzscxb.common.core.redis.RedisCache;
import cn.lzscxb.domain.constant.CacheConstants;
import cn.lzscxb.domain.entity.FengProblemQueue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Auther: Likfees
 * @Date: 2022/12/21 19:22
 * @Description: 定时任务
 */
@Component
@Slf4j
public class FengTaskScheduled {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private FengProblemQueueServiceImpl fengProblemQueueService;

    @Scheduled(fixedRate = 100)
    public void executeQuque() {
//        log.info("定时任务");
        Integer ququeId = redisCache.cacheListLPop(CacheConstants.QUQUE_EXECUTE);
        if (ququeId == null) {
            return;
        }
        try {
            log.info("定时任务 - 正在执行任务 queueId: {}", ququeId);
            FengProblemQueue fengProblemQueue = fengProblemQueueService.excuteQuque(ququeId);
            if (fengProblemQueue != null && fengProblemQueue.getStatus() == 4 && fengProblemQueue.getRetryCount() < 3) { // 执行异常，重新执行
                redisCache.cacheListRpush(CacheConstants.QUQUE_EXECUTE, ququeId);
            }
            log.info("定时任务 - 执行任务已完成！ ququeId: {}", ququeId);
        } catch (Exception e) {
            FengProblemQueue fengProblemQueue = fengProblemQueueService.selectFengProblemQueueById(Long.valueOf(ququeId));
            if (fengProblemQueue.getRetryCount() < 3) {
                redisCache.cacheListRpush(CacheConstants.QUQUE_EXECUTE, ququeId);
            }
            log.error("ququqId: {}, 定时任务处理异常, msg: {}", ququeId, e.getMessage());
            e.printStackTrace();
        }
    }

}
