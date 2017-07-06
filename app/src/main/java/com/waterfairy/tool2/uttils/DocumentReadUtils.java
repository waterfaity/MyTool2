package com.waterfairy.tool2.uttils;

import android.util.Xml;

//import org.textmining.text.extraction.WordExtractor;
//import org.xmlpull.v1.XmlPullParser;
//import org.xmlpull.v1.XmlPullParserException;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.zip.ZipEntry;
//import java.util.zip.ZipException;
//import java.util.zip.ZipFile;
//
//import jxl.Cell;
//import jxl.CellType;
//import jxl.DateCell;
//import jxl.NumberCell;
//import jxl.Sheet;
//import jxl.Workbook;

/**
 * Created by water_fairy on 2017/7/5.
 * 995637517@qq.com
 */

public class DocumentReadUtils {
//    public static String readDOC(String path) {
//        // 创建输入流读取doc文件
//        FileInputStream in;
//        String text = null;
////                Environment.getExternalStorageDirectory().getAbsolutePath()+ "/aa.doc")
//        try {
//            in = new FileInputStream(new File(path));
//            int a= in.available();
//            WordExtractor extractor = null;
//            // 创建WordExtractor
//            extractor = new WordExtractor();
//            // 对doc文件进行提取
//            text = extractor.extractText(in);
//            System.out.println("解析得到的东西"+text);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (text == null) {
//            text = "解析文件出现问题";
//        }
//        return text;
//    }
//
//    public static String readDOCX(String path) {
//        String river = "";
//        try {
//            ZipFile xlsxFile = new ZipFile(new File(path));
//            ZipEntry sharedStringXML = xlsxFile.getEntry("word/document.xml");
//            InputStream inputStream = xlsxFile.getInputStream(sharedStringXML);
//            XmlPullParser xmlParser = Xml.newPullParser();
//            xmlParser.setInput(inputStream, "utf-8");
//            int evtType = xmlParser.getEventType();
//            while (evtType != XmlPullParser.END_DOCUMENT) {
//                switch (evtType) {
//                    case XmlPullParser.START_TAG:
//                        String tag = xmlParser.getName();
//                        System.out.println(tag);
//                        if (tag.equalsIgnoreCase("t")) {
//                            river += xmlParser.nextText() + "\n";
//                        }
//                        break;
//                    case XmlPullParser.END_TAG:
//                        break;
//                    default:
//                        break;
//                }
//                evtType = xmlParser.next();
//            }
//        } catch (ZipException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (XmlPullParserException e) {
//            e.printStackTrace();
//        }
//        if (river == null) {
//            river = "解析文件出现问题";
//        }
//
//        return river;
//    }
//    public static String readXLS(String path) {
//        String str = "";
//        try {
//            Workbook workbook = null;
//            workbook = Workbook.getWorkbook(new File(path));
//            Sheet sheet = workbook.getSheet(0);
//            Cell cell = null;
//            int columnCount = sheet.getColumns();
//            int rowCount = sheet.getRows();
//            for (int i = 0; i < rowCount; i++) {
//                for (int j = 0; j < columnCount; j++) {
//                    cell = sheet.getCell(j, i);
//                    String temp2 = "";
//                    if (cell.getType() == CellType.NUMBER) {
//                        temp2 = ((NumberCell) cell).getValue() + "";
//                    } else if (cell.getType() == CellType.DATE) {
//                        temp2 = "" + ((DateCell) cell).getDate();
//                    } else {
//                        temp2 = "" + cell.getContents();
//                    }
//                    str = str + "  " + temp2;
//                }
//                str += "\n";
//            }
//            workbook.close();
//        } catch (Exception e) {
//        }
//        if (str == null) {
//            str = "解析文件出现问题";
//        }
//        return str;
//    }
//    public static String readXLSX(String path) {
//        String str = "";
//        String v = null;
//        boolean flat = false;
//        List<String> ls = new ArrayList<>();
//        try {
//            ZipFile xlsxFile = new ZipFile(new File(path));
//            ZipEntry sharedStringXML = xlsxFile
//                    .getEntry("xl/sharedStrings.xml");
//            InputStream inputStream = xlsxFile.getInputStream(sharedStringXML);
//            XmlPullParser xmlParser = Xml.newPullParser();
//            xmlParser.setInput(inputStream, "utf-8");
//            int evtType = xmlParser.getEventType();
//            while (evtType != XmlPullParser.END_DOCUMENT) {
//                switch (evtType) {
//                    case XmlPullParser.START_TAG:
//                        String tag = xmlParser.getName();
//                        if (tag.equalsIgnoreCase("t")) {
//                            ls.add(xmlParser.nextText());
//                        }
//                        break;
//                    case XmlPullParser.END_TAG:
//                        break;
//                    default:
//                        break;
//                }
//                evtType = xmlParser.next();
//            }
//            ZipEntry sheetXML = xlsxFile.getEntry("xl/worksheets/sheet1.xml");
//            InputStream inputStreamsheet = xlsxFile.getInputStream(sheetXML);
//            XmlPullParser xmlParsersheet = Xml.newPullParser();
//            xmlParsersheet.setInput(inputStreamsheet, "utf-8");
//            int evtTypesheet = xmlParsersheet.getEventType();
//            while (evtTypesheet != XmlPullParser.END_DOCUMENT) {
//                switch (evtTypesheet) {
//                    case XmlPullParser.START_TAG:
//                        String tag = xmlParsersheet.getName();
//                        if (tag.equalsIgnoreCase("row")) {
//                        } else if (tag.equalsIgnoreCase("c")) {
//                            String t = xmlParsersheet.getAttributeValue(null, "t");
//                            if (t != null) {
//                                flat = true;
//                                System.out.println(flat + "有");
//                            } else {
//                                System.out.println(flat + "没有");
//                                flat = false;
//                            }
//                        } else if (tag.equalsIgnoreCase("v")) {
//                            v = xmlParsersheet.nextText();
//                            if (v != null) {
//                                if (flat) {
//                                    str += ls.get(Integer.parseInt(v)) + "  ";
//                                } else {
//                                    str += v + "  ";
//                                }
//                            }
//                        }
//                        break;
//                    case XmlPullParser.END_TAG:
//                        if (xmlParsersheet.getName().equalsIgnoreCase("row")
//                                && v != null) {
//                            str += "\n";
//                        }
//                        break;
//                }
//                evtTypesheet = xmlParsersheet.next();
//            }
//            System.out.println(str);
//        } catch (ZipException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (XmlPullParserException e) {
//            e.printStackTrace();
//        }
//        if (str == null) {
//            str = "解析文件出现问题";
//        }
//        return str;
//    }
//    public static String readPPTX(String path) {
//        List<String> ls = new ArrayList<>();
//        String river = "";
//        ZipFile xlsxFile = null;
//        try {
//            xlsxFile = new ZipFile(new File(path));// pptx按照读取zip格式读取
//        } catch (ZipException e1) {
//            e1.printStackTrace();
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        }
//        try {
//            ZipEntry sharedStringXML = xlsxFile.getEntry("[Content_Types].xml");// 找到里面存放内容的文件
//            InputStream inputStream = xlsxFile.getInputStream(sharedStringXML);// 将得到文件流
//            XmlPullParser xmlParser = Xml.newPullParser();// 实例化pull
//            xmlParser.setInput(inputStream, "utf-8");// 将流放进pull中
//            int evtType = xmlParser.getEventType();// 得到标签类型的状态
//            while (evtType != XmlPullParser.END_DOCUMENT) {// 循环读取流
//                switch (evtType) {
//                    case XmlPullParser.START_TAG: // 判断标签开始读取
//                        String tag = xmlParser.getName();// 得到标签
//                        if (tag.equalsIgnoreCase("Override")) {
//                            String s = xmlParser
//                                    .getAttributeValue(null, "PartName");
//                            if (s.lastIndexOf("/ppt/slides/slide") == 0) {
//                                ls.add(s);
//                            }
//                        }
//                        break;
//                    case XmlPullParser.END_TAG:// 标签读取结束
//                        break;
//                    default:
//                        break;
//                }
//                evtType = xmlParser.next();// 读取下一个标签
//            }
//        } catch (ZipException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (XmlPullParserException e) {
//            e.printStackTrace();
//        }
//        for (int i = 1; i < (ls.size() + 1); i++) {// 假设有6张幻灯片
//            river += "第" + i + "张················" + "\n";
//            try {
//                ZipEntry sharedStringXML = xlsxFile.getEntry("ppt/slides/slide"
//                        + i + ".xml");// 找到里面存放内容的文件
//                InputStream inputStream = xlsxFile
//                        .getInputStream(sharedStringXML);// 将得到文件流
//                XmlPullParser xmlParser = Xml.newPullParser();// 实例化pull
//                xmlParser.setInput(inputStream, "utf-8");// 将流放进pull中
//                int evtType = xmlParser.getEventType();// 得到标签类型的状态
//                while (evtType != XmlPullParser.END_DOCUMENT) {// 循环读取流
//                    switch (evtType) {
//                        case XmlPullParser.START_TAG: // 判断标签开始读取
//                            String tag = xmlParser.getName();// 得到标签
//                            if (tag.equalsIgnoreCase("t")) {
//                                river += xmlParser.nextText() + "\n";
//                            }
//                            break;
//                        case XmlPullParser.END_TAG:// 标签读取结束
//                            break;
//                        default:
//                            break;
//                    }
//                    evtType = xmlParser.next();// 读取下一个标签
//                }
//            } catch (ZipException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (XmlPullParserException e) {
//                e.printStackTrace();
//            }
//        }
//        if (river == null) {
//            river = "解析文件出现问题";
//        }
//        return river;
//    }

}
