package com.fengtoos.mls.template.gui;

import com.alibaba.fastjson.JSONObject;
import com.fengtoos.mls.template.util.ExcelTest;
import com.fengtoos.mls.template.util.FreeMarkerUtil;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.List;
import java.util.Map;

import static javax.swing.JFileChooser.DIRECTORIES_ONLY;


public class BuildGui {//实现监听器的接口
    private JFrame frame;
    private JPanel p0, p1, p2, p3, p4;

    private JTextField dataName;
    private JTextField wordOutName;
    private JTextField imgName;
    private JButton dataChoose;
    private JButton wordChoose;
    private JButton imgChoose;
    private JButton register;
    private JButton word2pdf;
    private Reader fw;
    private JFileChooser dataChooser; //数据导入选择器
    private JFileChooser wordOutChooser; //word路径选择器
    private JFileChooser imgChooser; //图片路径选择器

    {
        dataChooser = new JFileChooser();
        FileFilter filter = new FileNameExtensionFilter("Excel表格（xlsx）", "XLSX");
        dataChooser.setFileFilter(filter);

        wordOutChooser = new JFileChooser();
        wordOutChooser.setFileSelectionMode(DIRECTORIES_ONLY);

        imgChooser = new JFileChooser();
        imgChooser.setFileSelectionMode(DIRECTORIES_ONLY);
    }

    public BuildGui() {
        frame = new JFrame("宗图生成器");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//设置窗口的点击右上角的x的处理方式，这里设置的是退出程序
        p0 = new JPanel();
        p0.setPreferredSize(new Dimension(550, 40));
        p0.add(new JLabel("宗图生成器"));
        frame.add(p0);

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

            int i = dataChooser.showOpenDialog(frame.getContentPane());// 显示文件选择对话框

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
        wordOutName.setPreferredSize(new Dimension(wordOutName.getWidth(), wordOutName.getHeight() + 28));
        p2.add(wordOutName);
        wordChoose = new JButton("选择路径");
        wordChoose.addActionListener(e -> {

            int i = wordOutChooser.showOpenDialog(frame.getContentPane());// 显示文件选择对话框

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
        imgName.setPreferredSize(new Dimension(imgName.getWidth(), imgName.getHeight() + 28));
        p3.add(imgName);
        imgChoose = new JButton("选择路径");
        imgChoose.addActionListener(e -> {

            int i = imgChooser.showOpenDialog(frame.getContentPane());// 显示文件选择对话框

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
            try {
                List<Map<String, Object>> list = ExcelTest.readTable(dataChooser.getSelectedFile(), imgChooser.getSelectedFile().getPath());
                for (Map<String, Object> item : list) {
                    String outfilepath = wordOutChooser.getSelectedFile().getPath() + "/" + item.get("number") + "/" + item.get("name") + "-调查表.doc";
                    FreeMarkerUtil.crateFile(item, "template2020060202.xml.ftl", outfilepath);
                }
                JOptionPane.showMessageDialog(frame, "已成功生成" + list.size() + "个文件！！");
            } catch (Exception exception) {
                exception.printStackTrace();
                JOptionPane.showMessageDialog(frame, "生成失败！！");
            }
        });

        word2pdf.addActionListener(e -> {
            if (wordOutChooser.getSelectedFile() == null) {
                JOptionPane.showMessageDialog(frame, "请选择生成路径");
                return;
            }

            saveProp();
        });
        p4.add(register);
        p4.add(word2pdf);
        p4.setPreferredSize(new Dimension(550, 40));

        frame.add(p1);
        frame.add(p2);
        frame.add(p3);
        frame.add(p4);

        frame.pack();
        frame.setVisible(true);
        show();
    }

    public void show() {
        frame.setBounds(500, 500, 550, 400);//设置大小
        frame.setLayout(new FlowLayout());//设置流式布局
    }

    public void saveProp() {
        try {
            File file = new File("doc2pdf.json");
            if(file.exists()){
                file.delete();
            }
            Writer fw = new FileWriter("doc2pdf.json");
            JSONObject map = new JSONObject();
            map.put("outPath", wordOutChooser.getSelectedFile().getPath());
            fw.write(map.toString());
            fw.flush();
            fw.close();
        } catch (Exception e1) {
            JOptionPane.showMessageDialog(null, "创建生成PDF配置文档失败");

        }
    }
}
