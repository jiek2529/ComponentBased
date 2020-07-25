package com.jiek.route;

/**
 * 用于注解处理器注册 Activity 时，按包下类实现此接口来逐个注册 Activity 至 JiekRoute.cacheMap 中
 */
public interface IJiekRoute {
    void putActivity();//可参见 ActivityUtil.java的示例
}
