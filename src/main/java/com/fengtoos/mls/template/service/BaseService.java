package com.fengtoos.mls.template.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class BaseService {

    public List<Map<String, Object>> readTable(File file, String imgPath) {
        InputStream ips = null;
        XSSFWorkbook wb = null;
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            ips = new FileInputStream(file);
            wb = new XSSFWorkbook(ips);
            execute(wb, list, imgPath);
        } catch (IOException e) {
            e.printStackTrace();
            if(e instanceof FileNotFoundException){
                log.error("系统找不到或文件不存在：" + file.getPath());
            }
            log.error(e.getMessage(), e);
        } finally {
            try {
                wb.close();
                ips.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    protected void execute(XSSFWorkbook wb, List<Map<String, Object>> list, String imgPath) {}
}
