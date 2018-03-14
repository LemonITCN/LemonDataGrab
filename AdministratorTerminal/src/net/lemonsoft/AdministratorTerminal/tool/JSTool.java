package net.lemonsoft.AdministratorTerminal.tool;

import com.yahoo.platform.yui.compressor.JavaScriptCompressor;
import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * Created by LiuRi on 16/8/24.
 */
public class JSTool {

    /**
     * 压缩JS字符串
     *
     * @param jsStr 要压缩的JS代码字符串
     * @return 压缩后的JS字符串
     */
    public static String compressJS(String jsStr) {
        try {
            Reader reader = new StringReader(jsStr);

            JavaScriptCompressor compressor = new JavaScriptCompressor(reader, new ErrorReporter() {
                @Override
                public void warning(String s, String s1, int i, String s2, int i1) {
                }

                @Override
                public void error(String s, String s1, int i, String s2, int i1) {
                }

                @Override
                public EvaluatorException runtimeError(String s, String s1, int i, String s2, int i1) {
                    return null;
                }
            });
            StringWriter writer = new StringWriter();
            compressor.compress(writer, -1, true, false, false, false);
            System.out.println("编译了一段js:" + writer.toString());
            return writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
