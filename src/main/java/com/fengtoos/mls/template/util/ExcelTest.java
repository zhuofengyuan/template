package com.fengtoos.mls.template.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 读取上篇中的xls文件的内容，并打印出来
 *
 * @author Administrator
 */
public class ExcelTest {

//    public static void main(String[] args) throws Exception {
//        readTable("F:\\fengtoos\\外包\\20200529\\data.xlsx");
//    }


    //通过对单元格遍历的形式来获取信息 ，这里要判断单元格的类型才可以取出值
    public static List<Map<String, Object>> readTable(File file, String imgPath) throws Exception {
        InputStream ips = new FileInputStream(file);
        XSSFWorkbook wb = new XSSFWorkbook(ips);
        XSSFSheet yellow = wb.getSheetAt(0);
        XSSFSheet green = wb.getSheetAt(1);
        XSSFSheet purple = wb.getSheetAt(2);
        //第一個表的數據
        List<Map<String, Object>> list = getYellow(yellow, imgPath);
        getGreen(green, list);
        getPurple(purple, list);
        for(Map<String, Object> item : list){
            List<Map<String, Object>> l = (List<Map<String, Object>>) item.get("jzdlist");
            item.put("f", l.get(0));
            item.put("jzdlist", l.subList(1, l.size()));
        }
        System.out.println(Arrays.toString(list.toArray()));
        return list;
    }

    private static List<Map<String, Object>> getPurple(XSSFSheet purple, List<Map<String, Object>> list){
        int i = 0;
        String previous = null;
        for (Iterator ite = purple.rowIterator();ite.hasNext(); i++) {
            XSSFRow row = (XSSFRow) ite.next();
            if(i == 0){
                continue;
            }
            Map<String, Object> rowm = new HashMap<String, Object>();
            setStringValue(row, rowm, 0, "number");
            setStringValue(row, rowm, 1, "dh");
            setStringValue(row, rowm, 2, "bz");
            setStringValue(row, rowm, 3, "x");
            setStringValue(row, rowm, 4, "y");
            setStringValue(row, rowm, 5, "b");
            setStringValue(row, rowm, 6, "l");
            setStringValue(row, rowm, 7, "jx");
            Map<String, Object> o = list.stream().filter(item -> item.get("number").equals(rowm.get("number"))).collect(Collectors.toList()).get(0);
            if(previous!= null && !previous.equals(rowm.get("number"))) {
                final String fpre =  previous;
                Map<String, Object> ob = list.stream().filter(item -> fpre.equals(item.get("number"))).collect(Collectors.toList()).get(0);
                List<Map<String, Object>> listChild = (List<Map<String, Object>>) ob.get("jzdlist");
                //再补充剩余的行数
                if (listChild.size() < 8) {
                    for (int j = list.size() + 1; j < 9; j++) {
                        Map<String, Object> rowx = new HashMap<String, Object>();
                        rowx.put("number", previous);
                        rowx.put("dh", "");
                        rowx.put("bz", "");
                        rowx.put("x", "");
                        rowx.put("y", "");
                        rowx.put("b", "");
                        rowx.put("l", "");
                        rowx.put("jx", "");
                        listChild.add(rowx);
                    }
                }
                i = 0;
            }
            ((List<Map<String, Object>>) o.get("jzdlist")).add(rowm);
            previous = rowm.get("number").toString();
        }

        final String fpre1 =  previous;
        Map<String, Object> oa = list.stream().filter(item -> item.get("number").equals(fpre1)).collect(Collectors.toList()).get(0);
        List<Map<String, Object>> listChild = (List<Map<String, Object>>) oa.get("jzdlist");
        //再补充剩余的行数
        if (listChild.size() < 16) {
            for (int j = listChild.size() + 1; j < 17; j++) {
                Map<String, Object> rowx = new HashMap<String, Object>();
                rowx.put("number", previous);
                rowx.put("dh", "");
                rowx.put("bz", "");
                rowx.put("x", "");
                rowx.put("y", "");
                rowx.put("b", "");
                rowx.put("l", "");
                rowx.put("jx", "");
                listChild.add(rowx);
            }
        }
        return list;
    }

    private static List<Map<String, Object>> getGreen(XSSFSheet green, List<Map<String, Object>> list){
        int i = 0;
        String previous = null;
        for (Iterator ite = green.rowIterator();ite.hasNext(); i++) {
            XSSFRow row = (XSSFRow) ite.next();
            if(i == 0){
                continue;
            }
            Map<String, Object> rowm = new HashMap<String, Object>();
            setStringValue(row, rowm, 0, "number");
            setStringValue(row, rowm, 1, "qd");
            setStringValue(row, rowm, 2, "zd");
            setStringValue(row, rowm, 3, "xl");
            setStringValue(row, rowm, 4, "zj");
            setStringValue(row, rowm, 5, "xy");
            setStringValue(row, rowm, 6, "bz");
            Map<String, Object> o = list.stream().filter(item -> item.get("number").equals(rowm.get("number"))).collect(Collectors.toList()).get(0);
            if(previous!= null && !previous.equals(rowm.get("number"))) {
                final String fpre =  previous;
                Map<String, Object> ob = list.stream().filter(item -> fpre.equals(item.get("number"))).collect(Collectors.toList()).get(0);
                List<Map<String, Object>> listChild = (List<Map<String, Object>>) ob.get("xlzdlist");
                //再补充剩余的行数
                if (listChild.size() < 8) {
                    for (int j = list.size() + 1; j < 9; j++) {
                        Map<String, Object> rowx = new HashMap<String, Object>();
                        rowx.put("number", previous);
                        rowx.put("qd", "");
                        rowx.put("zd", "");
                        rowx.put("xl", "");
                        rowx.put("zj", "");
                        rowx.put("xy", "");
                        rowx.put("bz", "");
                        listChild.add(rowx);
                    }
                }
                i = 0;
            }
            ((List<Map<String, Object>>) o.get("xlzdlist")).add(rowm);
            previous = rowm.get("number").toString();
        }

        final String fpre1 =  previous;
        Map<String, Object> oa = list.stream().filter(item -> item.get("number").equals(fpre1)).collect(Collectors.toList()).get(0);
        List<Map<String, Object>> listChild = (List<Map<String, Object>>) oa.get("xlzdlist");
        //再补充剩余的行数
        if (listChild.size() < 8) {
            for (int j = listChild.size() + 1; j < 9; j++) {
                Map<String, Object> rowx = new HashMap<String, Object>();
                rowx.put("number", previous);
                rowx.put("qd", "");
                rowx.put("zd", "");
                rowx.put("xl", "");
                rowx.put("zj", "");
                rowx.put("xy", "");
                rowx.put("bz", "");
                listChild.add(rowx);
            }
        }
        return list;
    }

    private static List<Map<String, Object>> getYellow(XSSFSheet yellow, String imgPath){
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        int i = 0;
        for (Iterator ite = yellow.rowIterator();ite.hasNext(); i++) {
            XSSFRow row = (XSSFRow) ite.next();
            if(i == 0){
                continue;
            }
            Map<String, Object> rowm = new HashMap<String, Object>();
            setStringValue(row, rowm, 0, "number");
            setStringValue(row, rowm, 1, "code");
            setStringValue(row, rowm, 2, "location");
            setStringValue(row, rowm, 3, "name");
            setStringValue(row, rowm, 4, "person");
            setStringValue(row, rowm, 5, "id");
            setStringValue(row, rowm, 6, "phone");
            setStringValue(row, rowm, 7, "sztf");
            setStringValue(row, rowm, 8, "htz");
            setStringValue(row, rowm, 9, "htrq");
            setStringValue(row, rowm, 10, "glblc");
            rowm.put("img", ImageUtil.image2Base64(imgPath + "\\" + rowm.get("number") + ".jpg"));
            rowm.put("xlzdlist", new ArrayList<Map<String, Object>>());
            rowm.put("jzdlist", new ArrayList<Map<String, Object>>());
            list.add(rowm);
        }
        return list;
    }

    private static void setStringValue(XSSFRow row, Map<String, Object> rowm, int index, String name){
        if(row.getCell(index) == null){
            rowm.put(name, "");
            return;
        }
        Cell cell = row.getCell(index);
        if("htrq".equals(name)){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            double value = cell.getNumericCellValue();
            Date date = DateUtil.getJavaDate(value);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            rowm.put(name,  sdf.format(date));
            rowm.put("year", cal.get(Calendar.YEAR) + "");
            rowm.put("month", cal.get(Calendar.MONTH) + 1);
            rowm.put("day", cal.get(Calendar.DAY_OF_MONTH));
            return;
        }
        row.getCell(index).setCellType(CellType.STRING);
        rowm.put(name, row.getCell(index).getRichStringCellValue().toString());
    }
}