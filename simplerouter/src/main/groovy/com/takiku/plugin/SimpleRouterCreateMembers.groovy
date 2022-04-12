package com.takiku.plugin


import groovy.io.FileType
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * @des:
 * @author: chengwl
 * @date: 2020/10/10
 */
class SimpleRouterCreateMembers extends DefaultTask {
    public static final String SEPARATOR = File.separator

    @TaskAction
    private void createRouterMembers() throws IOException {
        SimpleRouterPathExtension extension = getProject().getExtensions().getByType(SimpleRouterPathExtension.class)
        // 配置的包名
        def packageName = "com.takiku.simple_router"
        // 配置的包名目录
        def packageNamePath = packageName.replace(".", SEPARATOR)
        // 项目目录
        def rootParentDir = new File(getProject().projectDir.path).getParentFile()
        // Java文件目录
        def javaPath = "${SEPARATOR}src${SEPARATOR}main${SEPARATOR}java${SEPARATOR}"
        def generatedPath = "${SEPARATOR}build${SEPARATOR}generated${SEPARATOR}source${SEPARATOR}SimpleRouter" +
                "${SEPARATOR}debug${SEPARATOR}com${SEPARATOR}takiku${SEPARATOR}simple_router"

        // 需要遍历的目录包，默认遍历配置的包名目录
        def srcPath = extension.getSrcPath()

        // 未配置默认扫描包名目录
        if (srcPath == null || srcPath.isEmpty()) {
            srcPath = javaPath
        }

        // 去除配置的路径在不同系统运行时，分隔符的影响
        srcPath = srcPath.replace("\\", SEPARATOR)
        srcPath = srcPath.replace("/", SEPARATOR)

        // 未设置 src/main/java 时
        if (!srcPath.contains("src${SEPARATOR}main${SEPARATOR}java")) {
            srcPath = javaPath + srcPath
        }

        // 添加最左端的路径分隔符
        srcPath = SEPARATOR + srcPath

        // 剔除重复路径分隔符影响
        srcPath = srcPath.replace("${SEPARATOR}${SEPARATOR}", SEPARATOR)

        // 遍历解析目录
        println("SimpleRouter traverse dir: " + srcPath)

         Map<String, List<RouterBean>> mAllPathMap = new HashMap<>();
         Map<String, String> mAllGroupMap = new HashMap<>();
        // 遍历解析获取文件信息
        rootParentDir.traverse(type: FileType.FILES, nameFilter: ~/.*\.(java|kt)/) {
            def path = it.getPath()
            def keyLen = javaPath.length()

            // 是否能执行遍历
            def canEach = false
            def filterPrefix = extension.getFilterPrefix()
            for (prefix in filterPrefix) {
                // 文件路径包含前缀则进行解析
                if (path.contains("${SEPARATOR}${prefix}")) {
                    canEach = true
                    break
                }
            }
            def routerBean;
            if (canEach && path.contains(srcPath)) {
                File file = new File(path)
                // 防止溢出：一行一行读取
                def findRouter = false
                def  routerPackage = ""
                file.eachLine("UTF-8") {
                    if (it.contains("package"))
                        routerPackage = it.substring(it.indexOf("package")+"package".length())
                        routerPackage = routerPackage.trim().replaceAll(";","")
                    if (it.contains("@SimpleRouterClassRegister")) {
                        findRouter = true
                        def clzName = path.substring(path.indexOf(srcPath) + keyLen)
                        routerBean =  SimpleRouterTemp.parsing(it,clzName,path,srcPath,routerPackage)
                    }
                    return
                }

                if (findRouter) {
                    if (checkRouterPaht(routerBean)){
                        List<RouterBean> routerBeans = mAllPathMap.get(routerBean.getGroup());
                        if (null == routerBeans || routerBeans.isEmpty()) { // 没有该群组
                            routerBeans = new ArrayList<>();
                            routerBeans.add(routerBean);
                            mAllPathMap.put(routerBean.getGroup(), routerBeans);// 加入该群组里的path集合
                        } else { //直接添加到该群组
                            routerBeans.add(routerBean);
                        }
                    }
                }
            }
        }
        def HashMap<String,HashMap<String,String>> moduleGroupMap = new HashMap();
        for (String group :mAllPathMap.keySet()){
            def moduleName ="takiku"
            List<RouterBean> list = mAllPathMap.get(group)
            if (list!=null&&list.size()>0){
                moduleName = list.get(0).getModuleName()
            }
            def pathClassName =  "SimpleRouter" + "\$\$" + "path" + "\$\$" + group+".java"
            def pathStr = SimpleRouterTemp.createPathTemp(packageName,moduleName, mAllPathMap.get(group))
            def memberFilePath = rootParentDir.path+ SEPARATOR + moduleName + generatedPath + SEPARATOR  + pathClassName
            println("----moduleName: "+moduleName+" group: "+group)
            createFileByDeleteOld(memberFilePath, pathStr)
            if (moduleGroupMap.containsKey(moduleName)){
                HashMap<String,String> groupPath = moduleGroupMap.get(moduleName);
                groupPath.put(group,pathClassName)
                moduleGroupMap.put(moduleName,groupPath)
            }else {
                HashMap<String,String> hashMap = new HashMap<>()
                hashMap.put(group,pathClassName)
                moduleGroupMap.put(moduleName,hashMap)
            }
        }
        println("moduleGroupMap size: "+ moduleGroupMap.size())
        for (String moduleName :moduleGroupMap.keySet()){
            def groupClassName = "SimpleRouter" + "\$\$" + "group" + "\$\$" + moduleName+".java"
            def groupStr = SimpleRouterTemp.createGroupTemp(packageName,moduleName,moduleGroupMap.get(moduleName))
            def memberFilePath = rootParentDir.path+ SEPARATOR + moduleName + generatedPath + SEPARATOR  + groupClassName
            createFileByDeleteOld(memberFilePath, groupStr)
           // println("groupClassName: "+groupClassName+" groupStr: "+groupStr)
        }






        // 创建初始化类文件
        println("--- projectDir---"+getProject().projectDir.path+" javapaht: "+javaPath+" packageNamePath:  "+packageNamePath)
        def initFilePath = getProject().projectDir.path +  generatedPath + SEPARATOR + SimpleRouterTemp.FILE_NAME_INIT_CLASS
        println("----"+initFilePath+"------")
        createRouterInitFile(initFilePath, packageName)
    }


    /**
     * 创建初始化文件
     *
     * @param filePath 初始化文件
     * @param packageName 包名
     */
    private static void createRouterInitFile(String filePath, String packageName) {
        // 剔除路径分隔符影响
        filePath = filePath.replace("${SEPARATOR}${SEPARATOR}", SEPARATOR)
        def fileTemp = SimpleRouterTemp.getInitFileTemp(packageName)
        createFileByDeleteOld(filePath, fileTemp)
    }

     boolean checkRouterPaht(RouterBean routerBean){
        return true;
    }

    /**
     * 创建文件
     *
     * @param path 路径
     * @param content 内容
     */
    private static void createFileByDeleteOld(String path, String content) {
        // 剔除路径分隔符影响
        path = path.replace("${SEPARATOR}${SEPARATOR}", SEPARATOR)
        createDir(path)
        File file = new File(path)
        if (file.exists()) {
            file.delete()
        }
        file.write(content)
        file.createNewFile()
    }


    /**
     * 创建文件夹
     *
     * @param path 文件
     */
    private static void createDir(String path) {
        // 剔除路径分隔符影响
        path = path.replace("${SEPARATOR}${SEPARATOR}", SEPARATOR)
        println("path------"+ path)

        try {
            File file = new File(path)
            File fileParent = file.getParentFile();
            if(!fileParent.exists()){
                fileParent.mkdirs();
            }
            if (!file.exists()) {
                if (file.isDirectory()) {
                    file.mkdir()
                } else {
                    file.getParentFile().mkdir()
                }
            }
        }catch(Exception e){

        println("---- FileNotFoundException---- \n");
    }

    }
}
