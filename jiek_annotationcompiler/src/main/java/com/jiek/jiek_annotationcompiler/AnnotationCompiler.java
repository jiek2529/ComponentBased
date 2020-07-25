package com.jiek.jiek_annotationcompiler;

import com.google.auto.service.AutoService;
import com.jiek.jiek_annotation.Route;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

/**
 * 让编译时，反射生成需要的工具代码
 */
@AutoService(Processor.class) // 注册注解处理器
public class AnnotationCompiler extends AbstractProcessor {
    //生成文件对象
    Filer filter;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filter = processingEnvironment.getFiler();
    }

    private void l(String msg) {
    }

    /**
     * 声明处理器支持的 JDK 版本
     *
     * @return
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return processingEnv.getSourceVersion();//super.getSupportedSourceVersion();
    }

    /**
     * 声明处理器需处理的注解
     *
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new HashSet<>();
        types.add(Route.class.getCanonicalName());
        return types;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
//        TypeElement//类节点
//        ExecutableElement//方法节点
//        VariableElement//成员节点
        //获取当前引入此注解处理器的的子模块的所有节点,是集合
        Set<? extends Element> elementsAnnotatedWith = roundEnvironment.getElementsAnnotatedWith(Route.class);
        Map<String, String> map = new HashMap<>();
        for (Element element : elementsAnnotatedWith) {
            Route annotation = ((TypeElement) element).getAnnotation(Route.class);
            String key = annotation.value();
            Name acitivityName = ((TypeElement) element).getQualifiedName();//带包名的类名
            map.put(key, acitivityName + ".class");
        }
        //写文件
        if (map.size() > 0) {
            Writer writer = null;
            String activityFileName = "AcitivtyUtil" + System.currentTimeMillis();
            try {
                JavaFileObject classFile = filter.createSourceFile("com.jiek.util." + activityFileName);
                writer = classFile.openWriter();

                StringBuffer sb = new StringBuffer();
                sb.append("//annotation compiler 生成的代码\n");
                sb.append("package com.jiek.util;\n\n" +
                        "import com.jiek.route.IJiekRoute;\n" +
                        "import com.jiek.route.JiekRoute;\n" +
                        "\n" +
                        "public class " + activityFileName + " implements IJiekRoute {\n" +
                        "    @Override\n" +
                        "    public void putActivity() {\n");
                Iterator<String> iterator = map.keySet().iterator();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    String className = map.get(key);
                    sb.append("JiekRoute.getInstance().addActivity(\"" + key + "\", " + className + ");\n");
                }

                sb.append("}\n" + "}\n");
                writer.write(sb.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return false;
    }
}
