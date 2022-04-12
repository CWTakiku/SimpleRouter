package com.takiku.plugin;

/**
 * @author chengwl
 * @des
 * @date:2022/4/4
 */
public class RouterBean {
    private String path;
    private String group;
    private String Type;
    private String clazz;
    private String moduleName;
    private String packageName;

    public String getPath() {
        return path == null ? "" : path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getGroup() {
        return group == null ? "" : group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getType() {
        return Type == null ? "" : Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getClazz() {
        return clazz == null ? "" : clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getModuleName() {
        return moduleName == null ? "" : moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getPackageName() {
        return packageName == null ? "" : packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
