package woaini.fenger.bot.core.utils;


import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONPath;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

/**
 * @author yefeng
 * {@code @Date} 2021 2021/6/18 上午10:58
 * @Version 1.0
 * 模板字符串工具类 freemarker使用的是
 */
@Slf4j
public class StringTemplateUtils {

    /**
     * 匹配 ${}里面的内容用
     */
   public static final Pattern regex = Pattern.compile("\\$\\{([^}]*)\\}");

   public static final String LEFT_TOKEN = "{";

   public static final String RIGHT_TOKEN = "}";


    public static String parseSimple(String text, Object... args){
        return parse(LEFT_TOKEN,RIGHT_TOKEN,text,args);
    }
    /**
     * @Desc  myatis框架中的GenericTokenParser类 得到
     * @MethodName parse
     * @param openToken 左面替换符号
     * @param closeToken 右面替换符号
     * @param text 模板文本
     * @param args 参数值
     * @Author 孔晓文
     * {@code @Date} 2022/3/9 下午3:51
     **/
    public static String parse(String openToken, String closeToken, String text, Object... args) {
        if (args == null || args.length <= 0) {
            return text;
        }
        int argsIndex = 0;
        if (text == null || text.isEmpty()) {
            return "";
        }
        char[] src = text.toCharArray();
        int offset = 0;
        // search open token
        int start = text.indexOf(openToken, offset);
        if (start == -1) {
            return text;
        }
        final StringBuilder builder = new StringBuilder();
        StringBuilder expression = null;
        while (start > -1) {
            if (start > 0 && src[start - 1] == '\\') {
                // this open token is escaped. remove the backslash and continue.
                builder.append(src, offset, start - offset - 1).append(openToken);
                offset = start + openToken.length();
            } else {
                // found open token. let's search close token.
                if (expression == null) {
                    expression = new StringBuilder();
                } else {
                    expression.setLength(0);
                }
                builder.append(src, offset, start - offset);
                offset = start + openToken.length();
                int end = text.indexOf(closeToken, offset);
                while (end > -1) {
                    if (end > offset && src[end - 1] == '\\') {
                        // this close token is escaped. remove the backslash and continue.
                        expression.append(src, offset, end - offset - 1).append(closeToken);
                        offset = end + closeToken.length();
                        end = text.indexOf(closeToken, offset);
                    } else {
                        expression.append(src, offset, end - offset);
                        offset = end + closeToken.length();
                        break;
                    }
                }
                if (end == -1) {
                    // close token was not found.
                    builder.append(src, start, src.length - start);
                    offset = src.length;
                } else {
                    ///仅仅修改了该else分支下的个别行代码
                    String value = (argsIndex <= args.length - 1) ?
                            (args[argsIndex] == null ? "" : args[argsIndex].toString()) : expression.toString();
                    builder.append(value);
                    offset = end + closeToken.length();
                    argsIndex++;

                }
            }
            start = text.indexOf(openToken, offset);
        }
        if (offset < src.length) {
            builder.append(src, offset, src.length - offset);
        }
        return builder.toString();
    }


        /**
         * 根据模板组装 ${name} -> 数据
          * @param name 模板名称
         * @param templateString 模板字符串
         * @param data 模板数据
         * @return 组装好的字符串
         */
    public static String execString(String name,String templateString, Map<String,Object> data){
        Template template = null;
        String resultString = null;
        try {
           template = new Template(name,templateString,new Configuration(new Version("2.3.30")));
           //模板空值设置
            //获取模板所有的插槽
            List<String> elements = getTemplateElement(templateString);
            elements.forEach(element->{
                Object o = data.get(element);
                if (o != null) {
                    return;
                }
                //空值处理为""
                data.put(element,"");
            });

            StringWriter result = new StringWriter();
            template.process(data,result);
            resultString = result.toString();
        }catch (IOException | TemplateException e) {
            log.error("模板转换错误,存在非法参数:{}",e.getMessage());
            Assert.notNull(template, "模板转换错误,存在非法参数");
        }
        return resultString;
    }
    /**
     * 根据模板组装 ${name} -> 数据 名称自动用模板字符串md5加密
     * @param templateString 模板字符串
     * @param data 模板数据
     * @return 组装好的字符串
     */
    public static String execString(String templateString, Map<String,Object> data){
        return execString(SecureUtil.md5(templateString),templateString,data);
    }

    /**
     * 验证json表达式是否正确
     * @param dataString 返回的数据字符串
     * @param expressionStr 表达式json
     * @return
     */
    public static boolean execJsonPathExpression(String dataString,String expressionStr){
        Map<String, Object> expression = JSONObject.parseObject(expressionStr, HashMap.class);
        AtomicBoolean result = new AtomicBoolean(true);

        expression.forEach((k,v)->{
            Object d = JSONPath.eval(dataString,k);
            if (d == null) {
                return;
            }
            if (!d.equals(v)){
                result.set(false);
            }
        });
        return result.get();
    }

    /**
     * 获取一个模板里面的所有参数 原理用正则
     * @param templateString 模板字符串
     * @return ${name} 返回的就是name
     */
    public static List<String> getTemplateElement(String templateString){
        List<String> result = new ArrayList<>();
        if (StrUtil.isEmpty(templateString)){
            return new ArrayList<>();
        }
        Matcher matcher = regex.matcher(templateString);
        while (matcher.find()){
            result.add(matcher.group(1));
        }
        return result;
    }

    /**
     * @param source 源
     * @Desc 转换uft8字符串   \u624b\u673a\u53f7\u53c2\u6570\u9519\u8bef -> 手机格式错误
     * @MethodName convertUft8
     * @author yefeng
     * {@code @Date} 2022-11-08 16:00:01
     **/
    public static String convertUft8(String source){
        return new String(source.getBytes(StandardCharsets.UTF_8),StandardCharsets.UTF_8);
    }
}
