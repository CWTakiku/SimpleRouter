package com.stl.plugin

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
        def packageName = extension.getRouterPackName()
        // 配置的包名目录
        def packageNamePath = packageName.replace(".", SEPARATOR)
        // 项目目录
        def rootParentDir = new File(getProject().projectDir.path).getParentFile()
        // Java文件目录
        def javaPath = "${SEPARATOR}src${SEPARATOR}main${SEPARATOR}java${SEPARATOR}"

        // 需要遍历的目录包，默认遍历配置的包名目录
        def srcPath = extension.getSrcPath()

        // 未配置默认扫描包名目录
        if (srcPath == null || srcPath.isEmpty()) {
            srcPath = javaPath + packageNamePath
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

        // 遍历解析的组件全路径
        def strSimpleMembers = ""

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

            if (canEach && path.contains(srcPath)) {
                File file = new File(path)
                // 防止溢出：一行一行读取
                def findRouter = false
                file.eachLine("UTF-8") {
                    if (it.contains("@SimpleRouterClassRegister")) {
                        findRouter = true
                    }
                    return
                }

                if (findRouter) {
                    def clzName = path.substring(path.indexOf(srcPath) + keyLen)
                    clzName = clzName.replace(SEPARATOR, ".")
                    clzName = clzName.replaceAll(".(java|kt)", "")
                    strSimpleMembers += "            \"" + clzName + "\",\n"
                }
            }
        }

        // 创建组件成员文件
        def memberStr = SimpleRouterTemp.getMemberFileTemp(packageName, strSimpleMembers)
        def memberFilePath = getProject().projectDir.path + javaPath + SEPARATOR + packageNamePath + SEPARATOR + SimpleRouterTemp.FILE_NAME_LIST_CLASS
        createFileByDeleteOld(memberFilePath, memberStr)

        // 创建初始化类文件
        def initFilePath = getProject().projectDir.path + javaPath + SEPARATOR + packageNamePath + SEPARATOR + SimpleRouterTemp.FILE_NAME_INIT_CLASS
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
        File file = new File(path)
        if (!file.exists()) {
            if (file.isDirectory()) {
                file.mkdir()
            } else {
                file.getParentFile().mkdir()
            }
        }
    }
}
