package com.fengtoos.mls.template.service;

import com.fengtoos.mls.template.util.ImageUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class SurveyExcelService extends BaseService{

    @Override
    protected void execute(XSSFWorkbook wb, List<Map<String, Object>> list, String imgPath) throws ParseException {
        XSSFSheet main = wb.getSheetAt(3);
        log.info("正在解析" + main.getSheetName());
        list = this.getData(main, list, imgPath);
    }

    private List<Map<String, Object>> getData(XSSFSheet yellow, List<Map<String, Object>> list, String imgPath) throws ParseException {
        int i = 0;
        for (Iterator ite = yellow.rowIterator();ite.hasNext(); i++) {
            XSSFRow row = (XSSFRow) ite.next();
            if(i == 0){
                continue;
            }
            Map<String, Object> rowm = new HashMap<String, Object>();
            setStringValue(row, rowm, 0, "number");
            setStringValue(row, rowm, 1, "code");
            setStringValue(row, rowm, 4, "dc");
            setStringValue(row, rowm, 5, "jc");
            setStringValue(row, rowm, 6, "dcrq");
            setStringValue(row, rowm, 7, "location");
            setStringValue(row, rowm, 8, "z");
            setStringValue(row, rowm, 9, "c");
            setStringValue(row, rowm, 10, "name");
            setStringValue(row, rowm, 11, "zw");
            setStringValue(row, rowm, 12, "id");
            setStringValue(row, rowm, 13, "z1");
            setStringValue(row, rowm, 14, "c1");
            setStringValue(row, rowm, 15, "name1");
            setStringValue(row, rowm, 16, "zw1");
            setStringValue(row, rowm, 17, "id1");
            setStringValue(row, rowm, 18, "tf");
            setStringValue(row, rowm, 19, "gl");
            rowm.put("img", ImageUtil.image2Base64(imgPath + "\\" + rowm.get("number") + ".jpg"));
            list.add(rowm);
        }
        return list;
    }

    private void setStringValue(XSSFRow row, Map<String, Object> rowm, int index, String name) throws ParseException {
        if(row.getCell(index) == null){
            rowm.put(name, "");
            return;
        }
        Cell cell = row.getCell(index);
        if("dcrq".equals(name)){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
            SimpleDateFormat orgsdf = new SimpleDateFormat("yyyy年MM月dd日");
            String value = cell.getStringCellValue();
            if("".equals(value)){
                rowm.put(name, "");
                return ;
            }
//            double value = cell.getNumericCellValue();
//            Date date = DateUtil.getJavaDate(value);
            Date date = orgsdf.parse(value);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            rowm.put(name,  sdf.format(date));
            return;
        }
        row.getCell(index).setCellType(CellType.STRING);
        if("location".equals(name)){
            rowm.put(name, row.getCell(index).getRichStringCellValue().toString().replace("\n", "<w:br/>"));
            return;
        }
        rowm.put(name, row.getCell(index).getRichStringCellValue().toString());
    }
}
