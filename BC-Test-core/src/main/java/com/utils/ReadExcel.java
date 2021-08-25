package com.utils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *遍历excel，sheet参数
 */
public class ReadExcel {
    public static Object[][] readExcelData(String filePath,int sheetId) throws IOException {
        File file = new File(filePath);
        FileInputStream fis = new FileInputStream(file);

        HSSFWorkbook sheets = new HSSFWorkbook(fis);
        HSSFSheet sheet = sheets.getSheetAt(sheetId);

        int numrow = sheet.getPhysicalNumberOfRows();
        int numcell = sheet.getRow(0).getLastCellNum();
        Object[][] data = new Object[numrow][numcell];

        for(int i = 0;i < numrow;i++){
            if(sheet.getRow(i) != null || sheet.getRow(i).equals("")){
                continue;
            }
            for(int j = 0;j < numcell;j++){
                if(sheet.getRow(i).getCell(j) != null || sheet.getRow(i).getCell(j).equals("")){
                    continue;
                }
                HSSFCell cell = sheet.getRow(i).getCell(j);
                cell.setCellType(CellType.STRING);
                data[i][j] = cell.getStringCellValue();
            }
        }
        return data;
    }
}
