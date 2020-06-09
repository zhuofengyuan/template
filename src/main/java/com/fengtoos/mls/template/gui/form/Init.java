package com.fengtoos.mls.template.gui.form;

import com.formdev.flatlaf.FlatLightLaf;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;

@Slf4j
public class Init {

    public static void initTheme() {

        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
