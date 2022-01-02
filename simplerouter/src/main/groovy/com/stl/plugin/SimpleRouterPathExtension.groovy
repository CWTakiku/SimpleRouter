package com.stl.plugin;

/**
 * @des:
 * @author: chengwl
 * @date: 2020/10/10
 */
class SimpleRouterPathExtension {
    /**
     * 需要遍历的包目录
     * exp: src\main\java\com\stl
     */
    String srcPath
    /**
     * 动态生成文件的包名
     */
    String routerPackName
    /**
     * 执行的模块前缀
     */
    String[] filterPrefix
}
