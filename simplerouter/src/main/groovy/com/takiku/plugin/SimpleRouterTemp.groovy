package com.takiku.plugin

import com.takiku.plugin.RouterBean

import java.util.regex.Matcher
import java.util.regex.Pattern;

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


    public static final String SEPARATOR = File.separator

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

    static String getMemberFileTemp(String packageName, String moduleName, String members) {
        return "package " + packageName + ";\n" +
                "import import com.takiku.lib_router.GroupRouter;\n" +
                "\n" +
                "/**\n" +
                " * @author Router\n" +
                " */\n" +
                "public class SimpleRouter" + "\$\$" + "group" + "\$\$" + moduleName + " implements GroupRouter" +
                "{\n" +
                "    public static final String[] " + FILE_NAME_LIST_CLASS_FIELD + " = {\n" + members +
                "    };\n" +
                "}"
    }

    static String createPathTemp(String packageName, String moduleName, List<RouterBean> list) {
        println(list.size() + " size ----")

        println("moduleName--- " + moduleName + " packageName: " + packageName)
        String head = createPathClassHead(packageName, moduleName, list);
        String content = head +
                "/**\n" +
                " * @auther takiku\n" +
                " */\n" +
                "public class SimpleRouter" + "\$\$" + "path" + "\$\$" + moduleName + " implements RouterPath" +
                "  {\n" +
                "         @Override \n" +
                "    public Map<String, SimpleRouterObj> getPathMap() { \n" +
                "         Map<String, SimpleRouterObj> pathMap = new HashMap<>();" + "\n"
        for (RouterBean routerBean : list) {
            content += "         pathMap.put(\"" + routerBean.getPath() + "\"," + "SimpleRouterObj.create(" + routerBean.getClazz() + ".class" + "," + routerBean.getType() + ",\"" + routerBean.getPath() + "\",\"" + routerBean.getGroup() + "\"));"
            content = content + " \n"
        }
        content += "          return pathMap; "
        content += " \n"
        content += "        }" + " \n" +
                "  }"
        return content;
    }

    static String createGroupTemp(String packageName, String moduleName, HashMap<String,String> map){
        String head = createGroupClassHead(packageName,moduleName,map)
        String content = head +
                "/**\n" +
                " * @auther takiku\n" +
                " */\n" +
                "public class SimpleRouter" + "\$\$" + "group" + "\$\$" + moduleName + " implements RouterGroup" +
                "  {\n" +
                "         @Override \n" +
                "    public Map<String, class<? extends RouterPath>> getGroupMap() { \n" +
                "         Map<String, Class<? extends RouterPath>> groupMap = new HashMap<>();" + "\n"
        for (String group:map.keySet()){

            String className = map.get(group)
            className = className.substring(0, className.lastIndexOf("."))
            content+="         groupMap.put(\"" + group + "\"," + className+".class" + ");" +"\n"
        }
        content += "          return groupMap; "
        content += " \n"
        content += "        }" + " \n" +
                "  }"
        return content
    }

    private static String createGroupClassHead(String packageName, String moduleName, HashMap<String,String> map){
        String content = "package " + packageName + ";\n" +
                "import com.takiku.lib_router.RouterPath;\n" +
                "import com.takiku.lib_router.RouterGroup;\n"+
                "import com.takiku.lib_router.SimpleRouterObj; \n" +
                "import java.util.HashMap;\n" +
                "import java.lang.Override;\n" +
                "import java.lang.String;\n"+
                "import java.util.Map;\n"+
                "import java.lang.Class;\n"

        return content;
    }

    private static String createPathClassHead(String packageName, String moduleName, List<RouterBean> list) {
        String content = "package " + packageName + ";\n" +
                "import com.takiku.lib_router.RouterPath;\n" +
                "import com.takiku.lib_router.SimpleRouterObj; \n" +
                "import java.util.HashMap;\n" +
                "import java.lang.Override;\n" +
                "import java.lang.String;"+
                "import java.util.Map;\n"
        for (RouterBean routerBean : list) {
            println("import --- "+ routerBean.packageName + "." + routerBean.getClazz() + ";\n")
            content += "import " + routerBean.packageName + "." + routerBean.getClazz() + ";\n"
        }
        return content;
    }


    static RouterBean parsing(String annotationsStr, String clzName, String path, String srcPath, String packageName) {
        RouterBean routerBean = new RouterBean();
        def index = clzName.lastIndexOf(SEPARATOR)
        routerBean.setPackageName(packageName)
        if (index != -1) {
            clzName = clzName.substring(index + 1)
            clzName = clzName.substring(0, clzName.lastIndexOf("."))
            routerBean.setClazz(clzName)
        } else {
            return null;
        }
        int pathIndex = annotationsStr.indexOf("path");
        int groupIndex = annotationsStr.indexOf("group");
        Pattern p1 = Pattern.compile("\"(.*?)\"");
        Matcher m = p1.matcher(annotationsStr);
        ArrayList<String> list = new ArrayList<String>();
        if (annotationsStr.contains("CALL")) {
            routerBean.setType("SimpleRouterObj.SimpleRouterRegisterType.CALL")
        } else {
            routerBean.setType("SimpleRouterObj.SimpleRouterRegisterType.ACTIVITY")
        }
        while (m.find()) {
            list.add(m.group().trim().replace("\"", ""))
        }

        if (groupIndex == -1) {
            if (list.size() > 0) {
                routerBean.setPath(list.get(0))
            }
            int index1 = path.indexOf(srcPath);
            if (index1 != -1) {
                String pre = path.substring(0, index1)
                int index2 = pre.lastIndexOf(SEPARATOR)
                if (index2 != -1) {
                    String group = pre.substring(index2 + 1)
                    routerBean.setGroup(group)
                    routerBean.setModuleName(group)
                }
            }
        } else if (groupIndex != -1 && pathIndex != -1) {
            if (list.size() > 1) {
                if (groupIndex < pathIndex) {
                    routerBean.setGroup(list.get(0))
                    routerBean.setPath(list.get(1))
                } else {
                    routerBean.setGroup(list.get(1))
                    routerBean.setPath(list.get(0))
                }
            }
            int index1 = path.indexOf(srcPath);
            if (index1 != -1) {
                String pre = path.substring(0, index1)
                int index2 = pre.lastIndexOf(SEPARATOR)
                if (index2 != -1) {
                    String group = pre.substring(index2 + 1)
                    routerBean.setModuleName(group)
                }
            }
        }
        println("path: " + routerBean.getPath() + "group: " + routerBean.group + " class: " + routerBean.getClazz() + " type " + routerBean.getType() + " module:" + routerBean.getModuleName())
        return routerBean;
    }
}
