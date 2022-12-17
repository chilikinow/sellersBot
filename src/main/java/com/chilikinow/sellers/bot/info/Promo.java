package com.chilikinow.sellers.bot.info;

import com.chilikinow.sellers.bot.settings.BotData;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.nio.file.Path;
import java.util.*;
import java.util.List;

@Slf4j
public class Promo {

    private static Map <String, String> promoMobileTVMap;
//    private static Map <String, String> promoAppliancesMap;
    private static XSSFWorkbook workBook;

    private Promo(){
    }

    public static void updateWorkbook(){
        addWorkbook();
        promoMobileTVMap = new HashMap<>(addSpecialMap(workBook,"Календарь"));
//        promoAppliancesMap = new HashMap<>(addSpecialMap(workBook,"Акции_БТ"));
    }

    public static Map<String, String> getInstancePromoMobileTV(){
        updateWorkbook();
        return promoMobileTVMap;
    }

//    public static Map<String, String> getInstancePromoAppliances(){
//        updateWorkbook();
//        return promoAppliancesMap;
//    }

    private static void addWorkbook(){

        promoMobileTVMap = new HashMap<>();
//        promoAppliancesMap = new HashMap<>();

        Path promoFilePath = BotData.outResources.resolve(BotData.promoFileName);

        workBook = null;
        try (FileInputStream fIS = new FileInputStream(promoFilePath.toFile())) {
            workBook = new XSSFWorkbook(fIS);//получили книгу excel
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден.");
        } catch (IOException e) {
            System.out.println("Файл не читается.");
        }
    }

    private static Map<String, String> addSpecialMap(XSSFWorkbook workBook, String sheetName) {

        //создание map, key 0я ячейка строки (За исключением 0й строки), value конкатинация содержимого всех
        //не пустых ячеек тойже строки

        int sheetNumber = workBook.getSheetIndex(sheetName);

        //получили страницу книги
        XSSFSheet sheet = workBook.getSheetAt(sheetNumber);

        //получаем лист строк
        ArrayList<Row> rowList = new ArrayList<>();

        Iterator<Row> rowIterator = sheet.rowIterator(); //формируем список строк из страницы
        while (rowIterator.hasNext()){
            Row bufferRow = rowIterator.next();
            if(!bufferRow.getCell(0).getStringCellValue().equals("")) {  //за исключением пустых
                rowList.add(bufferRow);
            }
        }

        //перебираем лист строк без первой строки, где хронятся названия столбцов
        ArrayList<Cell> cellList = new ArrayList<>();
        StringBuilder valueForMap = new StringBuilder();

        Map<String, String> map = new HashMap<>();

        for (int i = 1; i < rowList.size(); i++) {
            cellList = new ArrayList<>();
            Iterator<Cell> cellIterator = rowList.get(i).iterator();
            Cell bufferCell;
            while (cellIterator.hasNext()){  //формируем лист ячеек из строки
                bufferCell = cellIterator.next();
                try {
                    if(!bufferCell.getStringCellValue().equals("")) {  //за исключением пустых
                        cellList.add(bufferCell);
                    }
                } catch (Exception exception){

                }
            }

            //переменная для формирования информации со всех ячеек (подробности акции) кроме названия акции
            valueForMap = new StringBuilder();
            for (int j = 1; j < cellList.size(); j++) {
                String cellString = cellList.get(j).getStringCellValue();
                    valueForMap.append(cellString + "\n");
            }
            //создаем элемент Map, где key- название акции, value- подробности акции
            //key очищается от лишних пробелов
            map.put(cellList.get(0).getStringCellValue().trim(), valueForMap.toString());
        }

//            блок для проверки вывода Map в консоль
//            for (Map.Entry<String, String> entry: map.entrySet()){
//                StringBuilder mapToString = new StringBuilder();
//                mapToString.append(entry.getKey())
//                        .append("\n\n")
//                        .append(entry.getValue());
//                System.out.println(mapToString.toString());
//                System.out.println("------------------------------");
//            }
//            System.out.println("_________End Map_________\n\n\n");

        return map;
    }

    private static List<List<String>> addTableStringsFromPromoFile(XSSFWorkbook workBook, int sheetNumber){

        //получили страницу книги
        XSSFSheet sheet = workBook.getSheetAt(sheetNumber);

        //получаем лист строк
        ArrayList<Row> rowList = new ArrayList<>();
        Iterator<Row> rowIterator = sheet.rowIterator(); //формируем список строк из страницы
        while (rowIterator.hasNext()) {
            Row bufferRow = rowIterator.next();
            if (!bufferRow.getCell(0).getStringCellValue().equals("")) {  //за исключением пустых
                rowList.add(bufferRow);
            }
        }

        List<String> cellList;
        List<List<String>> tableStrings = new ArrayList<>();

        //перебираем лист строк
        for (int i = 0; i < rowList.size(); i++) {
            cellList = new ArrayList<>();
            Iterator<Cell> cellIterator = rowList.get(i).iterator();
            while (cellIterator.hasNext()) {  //формируем лист ячеек из строки
                Cell bufferCell = cellIterator.next();
                if (bufferCell.getCellType().equals(CellType.STRING))
                    if (!bufferCell.getStringCellValue().equals("")) {  //за исключением пустых
                        cellList.add(bufferCell.getStringCellValue().trim());
                    }
                if (bufferCell.getCellType().equals(CellType.NUMERIC))
                    if (bufferCell.getNumericCellValue() != 0) {  //за исключением пустых
                        cellList.add(String.valueOf(bufferCell.getNumericCellValue()));
                    }
            }
            tableStrings.add(new ArrayList<>(cellList));
        }

        //test
//        for (int i = 0; i < tableStrings.size(); i++) {
//            System.out.println(tableStrings.get(i));
//        }

        return tableStrings;
    }


}