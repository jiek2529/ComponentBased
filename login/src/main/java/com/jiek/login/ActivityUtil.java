package com.jiek.login;

import com.jiek.route.IJiekRoute;
import com.jiek.route.JiekRoute;

/**
 * 示例，用 key 关联对应子库的 Activity。
 * 每个子库相互不可见，故用注解处理器，按子库逐个用 APT 动态创建注解对应的 Activity。
 * JiekRoute.startActivity 时使用。
 * <p>
 * 不在jiekRoute指定的包中。只是示例
 */
//@Deprecated
public class ActivityUtil implements IJiekRoute {
    @Override
    public void putActivity() {
        JiekRoute.getInstance().addActivity("login/login", LoginActivity.class);
//        JiekRoute.getInstance().addActivity("login/register", RegisterActivity.class);
//        JiekRoute.getInstance().addActivity("login/resetpwd", ResetPwdActivity.class);
    }
}
