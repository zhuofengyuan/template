package com.fengtoos.mls.template;

import com.fengtoos.mls.template.gui.MainGui;
import com.fengtoos.mls.template.util.ComponentUtil;

import javax.swing.*;

public class App {

    public static MainGui mainGui;

    public static void main(String[] args) {
        //更改默认的选择文件的外观为windows的风格
        if (UIManager.getLookAndFeel().isSupportedLookAndFeel()) {
            final String platform = UIManager.getSystemLookAndFeelClassName();
            if (!UIManager.getLookAndFeel().getName().equals(platform)) {
                try {
                    UIManager.setLookAndFeel(platform);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
        mainGui = new MainGui();
        ComponentUtil.setPreferSizeAndLocateToCenter(mainGui, 0.3, 0.4);
    }
}
