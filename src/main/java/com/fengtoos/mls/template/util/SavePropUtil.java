package com.fengtoos.mls.template.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fengtoos.mls.template.App;

import javax.swing.*;
import java.io.*;

public class SavePropUtil {

    public static void saveProp(JSONObject map) {
        try {
            File outFile = new File("conf/save2path.json");
            if (!outFile.getParentFile().exists()) {
                outFile.getParentFile().mkdirs();
            }
            Writer fw = new FileWriter("conf/save2path.json");
            fw.write(map.toString());
            fw.flush();
            fw.close();
        } catch (Exception e1) {
            JOptionPane.showMessageDialog(null, "创建历史配置文档失败");
            JOptionPane.showMessageDialog(App.mainGui, "创建历史配置文档失败");
        }
    }

    public static JSONObject loadProp() {
        //获取JSON文件内容，转化为字符串类型
        StringBuilder json = new StringBuilder();
        try {
            String temp = "";
            Reader fw = new FileReader("conf/save2path.json");
            BufferedReader bfr = new BufferedReader(fw);
            while ((temp = bfr.readLine()) != null) {
                json.append(temp);
            }
            bfr.close();
            fw.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(App.mainGui, "加载配置文档失败");
            return new JSONObject();
        }

        //将字符串转化为json
        return JSON.parseObject(json.toString());
    }
}
