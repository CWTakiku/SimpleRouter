package com.takiku.lib_router;

import java.util.Map;

/**
 * @author chengwl
 * @des
 * @date:2022/4/4
 */
public interface RouterGroup {
    Map<String, Class<? extends RouterPath>> getGroupMap();
}
