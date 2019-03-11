import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;

public class Demo01
{
    /*测试脚本引擎执行javascript代码*/
    public static void main(String[] args)
    {
        //获得脚本引擎对象
        ScriptEngineManager sem=new ScriptEngineManager();
        ScriptEngine engine=sem.getEngineByName("javascript");
        //定义变量存储到引擎上下文中
        engine.put("msg","gaoqi is a good man!");
        String str="var user={name:'gaoqi',age:18,schools:['hfut','TS']};";
        str+="print(user.name);";
        System.out.println(str);

        //执行脚本
        try {
            engine.eval(str);
            engine.eval("msg='hfut is a good school!';");
            System.out.println(engine.get("msg"));
            System.out.println("####");
            //定义函数
            engine.eval("function add(a,b){var sum=a+b; return sum;}");
            //取得调用接口
            Invocable jsInvoke=(Invocable)engine;
            //执行js中定义的函数
            Object result1=jsInvoke.invokeFunction("add",new Object[]{13,20});
            System.out.println(result1);

            //执行js文件
            URL url=Demo01.class.getClassLoader().getResources("a.js");
            FileReader fr=new FileReader(url.getPath());
            engine.eval(fr);
            fr.close();

            //导入其他的java包，使用其他包中的java类,若要详细了解细节，可以详细学校Rhino
            String jsCode="importPackage(java.util); var list=Arrays.asList([\"hfut\",\"bj\"])";
            engine.eval(jsCode);
            List<String> list2=(List<String>)engine.get("list");
            for(String temp:list2){
                System.out.println(temp);
            }
        }
        catch (ScriptException e) {
            e.printStackTrace();
        }catch (NoSuchMethodException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
