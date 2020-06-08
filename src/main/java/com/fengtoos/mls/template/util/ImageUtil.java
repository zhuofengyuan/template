package com.fengtoos.mls.template.util;

import com.fengtoos.mls.template.App;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

@Slf4j
public class ImageUtil {

    public static String image2Base64(String imgFile) {
        InputStream in = null;
        byte[] data = null;
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(App.mainGui, "找不到图片：" + imgFile);
            log.error("找不到图片：" + imgFile);
        }
        return Base64.getEncoder().encodeToString(data);
    }
}
