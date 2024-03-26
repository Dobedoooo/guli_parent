package com.atguigu.commonutils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtil {
    public static String getMessage(Exception e) {
        StringWriter sw = null;
        PrintWriter pw = null;
        try {
            sw = new StringWriter();
            pw = new PrintWriter(sw);
            // 将错误信息输出到 printWriter 中
            e.printStackTrace(pw);
            pw.flush(); sw.flush();
        } finally {
            if(sw != null) {
                try {
                    sw.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
            if(pw != null) {
                pw.close();
            }
        }
        return sw.toString();
    }
}
