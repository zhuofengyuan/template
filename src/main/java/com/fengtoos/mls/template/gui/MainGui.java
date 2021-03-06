package com.fengtoos.mls.template.gui;

import com.alibaba.fastjson.JSONObject;
import com.fengtoos.mls.template.service.ReportExcelService;
import com.fengtoos.mls.template.service.SurveyExcelService;
import com.fengtoos.mls.template.util.DosUtil;
import com.fengtoos.mls.template.util.FreeMarkerUtil;
import com.fengtoos.mls.template.util.SavePropUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static javax.swing.JFileChooser.DIRECTORIES_ONLY;

@Slf4j
public class MainGui extends JFrame{//实现监听器的接口
    private JPanel p0, p1, p2, p3, p4, p5;

    private JTextField dataName;
    private JTextField wordOutName;
    private JTextField imgName;
    private JButton dataChoose;
    private JButton wordChoose;
    private JButton imgChoose;
    private JButton register;
    private JButton word2pdf;
    private JButton download;
    private JComboBox selectTemplate;
    private JFileChooser dataChooser; //数据导入选择器
    private JFileChooser wordOutChooser; //word路径选择器
    private JFileChooser imgChooser; //图片路径选择器
    private JFileChooser downloadChooser;

    private void init(){
        dataChooser = new JFileChooser();
        FileFilter filter = new FileNameExtensionFilter("Excel表格（xlsx）", "XLSX");
        dataChooser.setFileFilter(filter);

        wordOutChooser = new JFileChooser();
        wordOutChooser.setFileSelectionMode(DIRECTORIES_ONLY);

        imgChooser = new JFileChooser();
        imgChooser.setFileSelectionMode(DIRECTORIES_ONLY);

        downloadChooser = new JFileChooser();
        downloadChooser.setFileSelectionMode(DIRECTORIES_ONLY);
        
        this.setName("宗图生成器");
        this.setTitle("宗图生成器");
        Image image = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/icon/logo.png"));
        this.setIconImage(image);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//设置窗口的点击右上角的x的处理方式，这里设置的是退出程序
    }

    public MainGui() {
        init();
        p0 = new JPanel();
        p0.setPreferredSize(new Dimension(550, 40));
        p0.add(new JLabel("宗图生成器"));
        this.add(p0);

        //------------------------数据导入路径---------------------
        p1 = new JPanel();
        p1.setPreferredSize(new Dimension(550, 40));
        p1.add(new JLabel("\t数据路径:"));
        dataName = new JTextField(20);
        dataName.setEnabled(false);
        dataName.setPreferredSize(new Dimension(dataName.getWidth(), dataName.getHeight() + 28));
        p1.add(dataName);
        dataChoose = new JButton("选择文件");
        dataChoose.addActionListener(e -> {

            int i = dataChooser.showOpenDialog(this.getContentPane());// 显示文件选择对话框

            // 判断用户单击的是否为“打开”按钮
            if (i == JFileChooser.APPROVE_OPTION) {

                File selectedFile = dataChooser.getSelectedFile();// 获得选中的文件对象
                dataName.setText(selectedFile.getPath());// 显示选中文件的名称
            }
        });
        p1.add(dataChoose);

        //------------------------数据输出目录---------------------
        p2 = new JPanel();
        p2.setPreferredSize(new Dimension(550, 40));
        p2.add(new JLabel("\t生成路径:"));
        wordOutName = new JTextField(20);
        wordOutName.setEnabled(false);
        wordOutName.setPreferredSize(new Dimension(wordOutName.getWidth(), wordOutName.getHeight() + 28));
        p2.add(wordOutName);
        wordChoose = new JButton("选择路径");
        wordChoose.addActionListener(e -> {

            int i = wordOutChooser.showOpenDialog(this.getContentPane());// 显示文件选择对话框

            // 判断用户单击的是否为“打开”按钮
            if (i == JFileChooser.APPROVE_OPTION) {

                File selectedFile = wordOutChooser.getSelectedFile();// 获得选中的文件对象
                wordOutName.setText(selectedFile.getPath());// 显示选中文件的名称
            }
        });
        p2.add(wordChoose);

        //------------------------图片导入路径---------------------
        p3 = new JPanel();
        p3.setPreferredSize(new Dimension(550, 40));
        p3.add(new JLabel("\t图片路径:"));
        imgName = new JTextField(20);
        imgName.setEnabled(false);
        imgName.setPreferredSize(new Dimension(imgName.getWidth(), imgName.getHeight() + 28));
        p3.add(imgName);
        imgChoose = new JButton("选择路径");
        imgChoose.addActionListener(e -> {

            int i = imgChooser.showOpenDialog(this.getContentPane());// 显示文件选择对话框

            // 判断用户单击的是否为“打开”按钮
            if (i == JFileChooser.APPROVE_OPTION) {

                File selectedFile = imgChooser.getSelectedFile();// 获得选中的文件对象
                imgName.setText(selectedFile.getPath());// 显示选中文件的名称
            }
        });
        p3.add(imgChoose);

        //------------------------操作列---------------------
        p4 = new JPanel();
        register = new JButton("生成Word");
        word2pdf = new JButton("转换PDF");
        register.addActionListener(e -> {
//            try {
            String filename = "", templateFileName = "";
            log.info("本次选择的值为：{}， 索引为：{}", this.selectTemplate.getSelectedItem(), this.selectTemplate.getSelectedIndex());
            List<Map<String, Object>> list;
            File imgPath = imgChooser.getSelectedFile();
            if(this.selectTemplate.getSelectedIndex() == 0){
                list = new ReportExcelService().readTable(dataChooser.getSelectedFile(), imgPath==null?null:imgPath.getPath());
                filename = "-调查表.doc";
                templateFileName = "template2020060202.xml.ftl";
            } else {
                list = new SurveyExcelService().readTable(dataChooser.getSelectedFile(), imgPath==null?null:imgPath.getPath());
                filename = "-协议书.doc";
                templateFileName = "template2020060901.xml.ftl";
            }
            String hasImg = "";
            for (Map<String, Object> item : list) {
                if(StringUtils.isEmpty(item.get("img"))){
                    hasImg = "（无图）";
                } else {
                    hasImg = "";
                }
                String number = item.get("number").toString();
//                String outfilepath = wordOutChooser.getSelectedFile().getPath() + "/" + item.get("number") + hasImg + "/" + item.get("number") + "-" + item.get("code") + filename;
                String outfilepath = String.format("%s/%s%s/%s%s", wordOutChooser.getSelectedFile().getPath(), number, hasImg, item.get("code"), filename);
                if(number.length() > 12){
                    outfilepath = String.format("%s/%s/%s/%s/%s%s/%s%s", wordOutChooser.getSelectedFile().getPath(), number.substring(0, 6),
                            number.substring(6, 9), number.substring(9, 12), item.get("number"), hasImg, item.get("code"), filename);
                }
                FreeMarkerUtil.createFile(item, templateFileName, outfilepath);
            }

            //保存配置文件
            JSONObject map = new JSONObject();
            map.put("dataPath", dataChooser.getSelectedFile().getPath());
            map.put("outPath", wordOutChooser.getSelectedFile().getPath());
            map.put("imagePath", imgPath==null?"":imgPath.getPath());
            map.put("select", selectTemplate.getSelectedIndex());
            SavePropUtil.saveProp(map);
            JOptionPane.showMessageDialog(this, "已成功生成" + list.size() + "个文件！！");
//            } catch (Exception exception) {
//                exception.printStackTrace();
//                JOptionPane.showMessageDialog(this, "生成失败！！");
//                log.error(exception.getMessage(), exception);
//            }
        });

        word2pdf.addActionListener(e -> {
            if (wordOutChooser.getSelectedFile() == null) {
                JOptionPane.showMessageDialog(this, "请选择生成路径");
                return;
            }

            saveProp();
            DosUtil.run();
            JOptionPane.showMessageDialog(this, "转换成功！！");
        });
        p4.add(register);
        p4.add(word2pdf);
        p4.setPreferredSize(new Dimension(550, 40));

        p5 = new JPanel();
        p5.setPreferredSize(new Dimension(550, 40));
        selectTemplate = new JComboBox();
        selectTemplate.addItem("农村集体土地所有权权属调查表");
        selectTemplate.addItem("土地权属界线协议书");
        p5.add(new JLabel("模板选择："));
        p5.add(selectTemplate);
        download = new JButton("下载导入模板");
        download.addActionListener(e -> {
            int i = downloadChooser.showOpenDialog(this.getContentPane());// 显示文件选择对话框

            // 判断用户单击的是否为“打开”按钮
            if (i == JFileChooser.APPROVE_OPTION) {

                File selectedFile = downloadChooser.getSelectedFile();// 获得选中的文件对象
                File origin = new File(this.getClass().getResource("/import/data.xlsx").getPath());
                try {
                    Files.copy(origin.toPath(), new File(selectedFile.getPath() + "\\data.xlsx").toPath());
                    JOptionPane.showMessageDialog(this, "文件已存放：" + selectedFile.getPath() + "\\数据导入模板.xlsx");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                    JOptionPane.showMessageDialog(this, "下载失败，数据模板不存在或已损坏！！");
                }
            }
        });
        p5.add(download);

        this.add(p1);
        this.add(p2);
        this.add(p3);
        this.add(p5);
        this.add(p4);
//        this.setBounds(500, 500, 550, 400);//设置大小
        this.setLayout(new FlowLayout());//设置流式布局

        //加载配置文件
        JSONObject prop = SavePropUtil.loadProp();
        if(prop.containsKey("dataPath")){
            dataName.setText(prop.getString("dataPath"));
            dataChooser.setSelectedFile(new File(prop.getString("dataPath")));
        }
        if(prop.containsKey("outPath")){
            wordOutName.setText(prop.getString("outPath"));
            wordOutChooser.setSelectedFile(new File(prop.getString("outPath")));
        }
        if(prop.containsKey("imagePath")){
            imgName.setText(prop.getString("imagePath"));
            imgChooser.setSelectedFile(new File(prop.getString("imagePath")));
        }
        if(prop.containsKey("select")){
           selectTemplate.setSelectedItem(prop.getInteger("select"));
        }
    }

//    public void show() {
//    }

    public void saveProp() {
        try {
            File file = new File("doc2pdf.json");
            if(file.exists()){
                file.delete();
            }
            Writer fw = new FileWriter("word2pdf/dist/doc2pdf.json");
            JSONObject map = new JSONObject();
            map.put("outPath", wordOutChooser.getSelectedFile().getPath());
            fw.write(map.toString());
            fw.flush();
            fw.close();
        } catch (Exception e1) {
            JOptionPane.showMessageDialog(null, "创建生成PDF配置文档失败");
            log.error("创建生成PDF配置文档失败");
            e1.printStackTrace();
        }
    }
}
