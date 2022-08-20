package com.shadow.task.xxl.localadmin;

import com.xxl.job.core.biz.AdminBiz;
import com.xxl.job.core.biz.model.HandleCallbackParam;
import com.xxl.job.core.biz.model.RegistryParam;
import com.xxl.job.core.biz.model.ReturnT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 模拟 XXL 回调 调度器的请求 FIXME - 为了代码不报错
 */
@Slf4j
@Service
public class LocalAdminBiz  implements AdminBiz {

    @Override
    public ReturnT<String> callback(List<HandleCallbackParam> list) {
        return ReturnT.SUCCESS;
    }

    @Override
    public ReturnT<String> registry(RegistryParam registryParam) {
        // do nothing
        return null;
    }

    @Override
    public ReturnT<String> registryRemove(RegistryParam registryParam) {
        // do nothing
        return null;
    }
}