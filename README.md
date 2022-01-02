# SimpleRouterPlugins
路由插件，方便项目集成路由功能

### 自动集成步骤

##### 1、在项目的根目录build.gradle文件中配置私有Maven库地址，添加路由插件
```gradle
allprojects{
    repositories{
        maven { url "" }
      
    }
    dependencies {
        classpath
    }
}
```

#####  2、添加路由
项目添加`SimpleRouter`需要三步操作
- ###### 第一步：添加SDK、在module的build.gradle文件添加路由依赖
```gradle
dependencies {
    implementation "com.bailun.android:lib_router:0.0.7"
}
```
- ###### 第二步：添加构建插件、 在module的build.gradle添加插件引用，自行配置插件参数
```gradle
apply plugin: 'com.stl.simplerouter'

pathExtension {
    // 应用包名前缀，路由会扫描路径下的java文件
    srcPath "src\\main\\java\\com\\stl" 
    // 扫描后生成的路由表类的包名，此包名对应该module的包名
    routerPackName "com.stl.lib_router"
    // 匹配模块名前缀，包含该数组的前缀模块才会执行扫描
    filterPrefix "app", "module"
}
```

```java
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SimpleRouterApp.init(this);
    }
}
```
######  `SimpleRouterApp` 这个是自动生成的初始化类
- 方法`SimpleRouterApp.init()`：路由初始化
- 方法`SimpleRouterApp.get()`：返回`SimpleRouter`单例，用以跳转Activity、或Fragment





