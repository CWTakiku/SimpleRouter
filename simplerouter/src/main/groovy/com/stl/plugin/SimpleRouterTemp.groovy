package com.stl.plugin;

/**
 * 模板文件
 *
 * @author why* @since 2021/1/20
 */
class SimpleRouterTemp {
    /**
     * 生成的集合类 集合字段名
     */
    public static final String FILE_NAME_LIST_CLASS_FIELD = "ROUTER_PATH"
    /**
     * 生成的集合类 类名
     */
    public static final String FILE_NAME_LIST_CLASS = "SimpleRouterMembers.java"
    /**
     * 生成的初始化类 类名
     */
    public static final String FILE_NAME_INIT_CLASS = "SimpleRouterApp.java"

    /**
     * 获取初始化模板文件
     *
     * @return 初始化模板文件
     */
    static String getInitFileTemp(String packageName) {
        return "package " + packageName + ";\n" +
                "\n" +
                "import android.app.Application;\n" +
                "\n" +
                "import com.stl.lib_router.SimpleRouter;\n" +
                "\n" +
                "import java.util.Arrays;\n" +
                "\n" +
                "/**\n" +
                " * @author Router\n" +
                " */\n" +
                "public class SimpleRouterApp {\n" +
                "\n" +
                "    /**\n" +
                "     * 路由初始化类，自动生成\n" +
                "     * <p>\n" +
                "     * 请勿使用 SimpleRouter.getInstance().init()进行初始化\n" +
                "     * 使用 SimpleRouterApp.init() 即可\n" +
                "     *\n" +
                "     * @param application application\n" +
                "     */\n" +
                "    public static void init(Application application) {\n" +
                "        SimpleRouter.getInstance().init(application, Arrays.asList(SimpleRouterMembers." + FILE_NAME_LIST_CLASS_FIELD + "));\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * 代理 SimpleRouter\n" +
                "     *\n" +
                "     * @return SimpleRouter\n" +
                "     */\n" +
                "    public static SimpleRouter get() {\n" +
                "        return SimpleRouter.getInstance();\n" +
                "    }\n" +
                "}"
    }

    static String getMemberFileTemp(String packageName, String members) {
        return "package " + packageName + ";\n" +
                "\n" +
                "/**\n" +
                " * @author Router\n" +
                " */\n" +
                "public class SimpleRouterMembers {\n" +
                "    public static final String[] " + FILE_NAME_LIST_CLASS_FIELD + " = {\n" + members +
                "    };\n" +
                "}"
    }
}
