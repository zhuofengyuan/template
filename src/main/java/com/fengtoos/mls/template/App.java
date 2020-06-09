package com.fengtoos.mls.template;

import com.fengtoos.mls.template.gui.MainGui;
import com.fengtoos.mls.template.gui.form.Init;
import com.fengtoos.mls.template.util.ComponentUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {

    public static MainGui mainGui;

    public static void main(String[] args) {
        Init.initTheme();
        mainGui = new MainGui();
        ComponentUtil.setPreferSizeAndLocateToCenter(mainGui, 0.3, 0.4);
    }
}
