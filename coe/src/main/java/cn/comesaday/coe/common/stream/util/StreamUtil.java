package cn.comesaday.coe.common.stream.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.*;

/**
 * <Descripe> io操作基础工具类
 *
 * @author: ChenWei
 * @CreateAt: 2020-08-10 16:48
 */
public class StreamUtil {

    /**
     * @Fields FIRST_ROW_WIDTH : 第一行高度
     */
    private static final short FIRST_ROW_HEIGHT = 500;

    /**
     * @Fields ROW_HEIGHT : 其他行行高
     */
    private static final short ROW_HEIGHT = 400;

    /**
     * @Fields FIRST_FONT_HEIGHT : 第一行字体高度
     */
    private static final short FIRST_FONT_HEIGHT = 500;

    /**
     * @Fields FONT_HEIGHT : 字体高度
     */
    private static final short FONT_HEIGHT = 250;

    /**
     * @Fields EVEVY_WIDTH : 每个字符宽度
     */
    private static final short EVEVY_WIDTH = 256;

    /**
     * @Fields FIRST_COLUMN_NAME : 序号
     */
    private static final String FIRST_COLUMN_NAME = "序号";

    /**
     * <说明> 获取文件文本内容
     * @param inputStream
     * @author ChenWei
     * @date 2020/8/10 17:12
     * @return java.lang.String
     */
    public static String text(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedInputStream bis = new BufferedInputStream(inputStream);
        byte[] by = new byte[1024];
        int len = 0;
        while ((len = bis.read(by)) != -1) {
            stringBuilder.append(new String(by, 0, len));
        }
        inputStream.close();
        bis.close();
        return stringBuilder.toString();
    }

    /**
     *〈简述〉生成二维码
     *〈详细描述〉
     * @author ChenWei
     * @param url String 问卷地址
     * @param response HttpServletResponse
     * @throws IOException
     */
    public static void createQRCode(String url, int width, int height,
                                    String format, HttpServletResponse response) {
        //对二维码进行必要的设定
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        //设定内容的编码格式
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        //设定图像外边距（像素）
        hints.put(EncodeHintType.MARGIN, 0);
        try {
            //进行编码并获得一个bit封装对象
            BitMatrix bitMatrix = new MultiFormatWriter()
                    .encode(url, BarcodeFormat.QR_CODE, width, height, hints);
            MatrixToImageWriter.writeToStream(bitMatrix, format, response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *〈简述〉下载二维码
     *〈详细描述〉
     * @author ChenWei
     */
    public static void downloadQrCode(String url, int width, int height,
                                      String format, HttpServletResponse response) {
        response.reset();//重置 响应头
        response.setCharacterEncoding("utf-8");
        response.setContentType("image/jpeg;charset=utf-8");
        response.addHeader("Content-Disposition" ,"attachment;filename=qr.jpeg");
        //对二维码进行必要的设定
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        //设定内容的编码格式
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        //设定图像外边距（像素）
        hints.put(EncodeHintType.MARGIN, 0);
        try {
            //进行编码并获得一个bit封装对象
            BitMatrix bitMatrix = new MultiFormatWriter()
                    .encode(url, BarcodeFormat.QR_CODE, width, height, hints);
            MatrixToImageWriter.writeToStream(bitMatrix, format, response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 输出报表
     * @param response HttpServletResponse
     * @param tableName 表名
     * @param themeName 主题名
     * @param headers 列头
     * @param cellWidths 列宽
     * @param datas 数据
     * @param <T>
     */
    public static <T> void exportToExcel(HttpServletResponse response, String tableName, String themeName, String[] headers, Integer[] cellWidths, List<Map<String, Object>> datas) {
        if (null == datas || datas.size() < 1) {
            return;
        }
        // 创建一个excel
        HSSFWorkbook workbook = new HSSFWorkbook();
        // Excel的列
        HSSFCell cell = null;
        // Excel的行
        HSSFRow row = null;
        // 表列样式
        HSSFCellStyle style = workbook.createCellStyle();
        // 表格初始化
        HSSFSheet sheet = initTable(workbook, tableName,themeName, headers, cellWidths, cell, row, style);
        // 填充数据
        fillData(sheet, datas, cell, row, style, workbook);
        // 输出表格
        outPutExcel(response, workbook, tableName);

    }

    private static HSSFSheet initTable(HSSFWorkbook workbook, String tableName, String themeName, String[] headers, Integer[] cellWidths, HSSFCell cell, HSSFRow row, HSSFCellStyle style) {
        // 表的列数
        short cellNumber = (short)headers.length;
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 设置字体
        HSSFFont font = workbook.createFont();
        // 创建一个sheet
        HSSFSheet sheet = workbook.createSheet("sheet1");
        // 设置sheet的头
        HSSFHeader header = sheet.getHeader();
        NumberFormat ddf1 = NumberFormat.getNumberInstance();
        ddf1.setMaximumFractionDigits(1);
        ddf1.setRoundingMode(RoundingMode.DOWN);
        header.setCenter(tableName);
        CellRangeAddress title = new CellRangeAddress(0, 0, 0, cellNumber);
        sheet.addMergedRegion(title);
        // 标题行
        row = sheet.createRow(0);
        row.setHeight(FIRST_ROW_HEIGHT);
        cell = row.createCell(0);
        cell.setCellValue(themeName);
        font.setColor(HSSFFont.COLOR_NORMAL);
        font.setFontHeightInPoints((short)16);
        font.setFontHeight(FIRST_FONT_HEIGHT);
        // 设置字体风格
        style.setFont(font);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        cell.setCellStyle(style);
        //  内容行
        row = sheet.createRow(1);
        row.setHeight(ROW_HEIGHT);
        //第一列
        cell = row.createCell(0);
        cell.setCellValue(FIRST_COLUMN_NAME);
        sheet.setColumnWidth(0, EVEVY_WIDTH * 8);
        font.setFontHeightInPoints((short)14);
        font.setColor(HSSFFont.COLOR_NORMAL);
        font.setFontHeight(FONT_HEIGHT);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        style.setFont(font);
        cell.setCellStyle(style);

        for (int i = 0; i < cellNumber; i++) {
            cell = row.createCell(i + 1);
            cell.setCellValue(headers[i]);
            sheet.setColumnWidth(i + 1, EVEVY_WIDTH * cellWidths[i]);
            font.setFontHeightInPoints((short)14);
            font.setColor(HSSFFont.COLOR_NORMAL);
            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            font.setFontHeight(FONT_HEIGHT);
            font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
            style.setFont(font);
            cell.setCellStyle(style);
        }

        return sheet;
    }

    private static <T> void fillData(HSSFSheet sheet, List<Map<String, Object>> datas, HSSFCell cell, HSSFRow row, HSSFCellStyle style, HSSFWorkbook workbook) {
        //  按顺序列出所有
        List<String> dataCodes = new ArrayList<String>();
        //  datas.get(0)此处的map必须为linkedHashMap，否则dataCodes会出现乱序
        Set<Map.Entry<String, Object>> set = datas.get(0).entrySet();
        for (Map.Entry<String, Object> entry : set) {
            dataCodes.add(entry.getKey());
        }

        HSSFFont font = workbook.createFont();
        //  每行
        for (int i = 0; i < datas.size(); i++) {
            Map<String, Object> data = datas.get(i);
            row = sheet.createRow((short) (i + 2));
            row.setHeight(ROW_HEIGHT);
            //  每列
            cell = row.createCell(0);
            font.setFontHeightInPoints((short)14);
            font.setColor(HSSFFont.COLOR_NORMAL);
            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            font.setFontHeight(FONT_HEIGHT);
            font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
            style.setFont(font);
            cell.setCellValue(i + 1);
            cell.setCellStyle(style);

            for (int j = 0; j < dataCodes.size(); j++ ) {
                cell = row.createCell(j + 1);
                String code = dataCodes.get(j);
                if (null != data.get(code)) {
                    font.setFontHeightInPoints((short)14);
                    font.setColor(HSSFFont.COLOR_NORMAL);
                    style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                    font.setFontHeight(FONT_HEIGHT);
                    font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
                    style.setFont(font);
                    cell.setCellValue(data.get(code).toString());
                    cell.setCellStyle(style);
                }
            }
        }
    }

    private static void outPutExcel(HttpServletResponse response, HSSFWorkbook workbook, String tableName) {
        // 创建一个输出流对象
        OutputStream out = null;
        try {
            out = response.getOutputStream();
            // headerString为中文时转码
            tableName = new String(tableName.getBytes("gb2312"), "ISO8859-1");
            // filename是下载的xls的名，建议最好用英文
            response.setHeader("Content-disposition",
                    "attachment; filename=" + tableName + ".xls");
            // 设置类型
            response.setContentType("application/msexcel;charset=UTF-8");
            // 设置头
            response.setHeader("Pragma", "No-cache");
            // 设置头
            response.setHeader("Cache-Control", "no-cache");
            // 设置日期头
            response.setDateHeader("Expires", 0);
            workbook.write(out);
            out.flush();
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
