package com.fengtoos.mls.template.util;

import com.fengtoos.mls.template.App;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

@Slf4j
public class DosUtil {

//    public static void main(String[] args) {
//        DosUtil.run();
//    }

    public static void run(){
        String cmd = "startpy.bat"; //
        try {
            Process process = Runtime.getRuntime().exec(cmd);
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(isr);
            String content = br.readLine();
            while (content != null) {
                System.out.println(content);
                content = br.readLine();
            }
        } catch (IOException e) {
            log.error("解析py命令失败，startpy.bat不存在");
            log.error(e.getMessage(), e);
            JOptionPane.showMessageDialog(App.mainGui, "解析py命令失败，startpy.bat不存在");
            e.printStackTrace();
        }
    }

}