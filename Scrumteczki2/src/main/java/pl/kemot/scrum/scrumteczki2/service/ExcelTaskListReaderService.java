package pl.kemot.scrum.scrumteczki2.service;

import android.net.Uri;
import android.widget.Toast;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import pl.kemot.scrum.scrumteczki2.model.Sprint;
import pl.kemot.scrum.scrumteczki2.model.Task;
import pl.kemot.scrum.scrumteczki2.utils.TimeUtils;

/**
 * Created by Tomek on 22.11.13.
 */
public class ExcelTaskListReaderService {
    private enum SprintExcelCols {
        ID(0),
        PRODUCT(1),
        ESTIMATE(3);

        private final int columnNumber;

        SprintExcelCols(final int columnNumber) {
            this.columnNumber = columnNumber;
        }

        public int getColumnNumber() {
            return columnNumber;
        }
    }
    private static final int INDEX_OF_FIRST_ROW = 1;

    public static Sprint loadTasksFromExcelWorkbook(Uri workbookUri) {
        return loadTasksFromExcelWorkbook(getFileFromUriIfExcel(workbookUri));
    }
    public static Sprint loadTasksFromExcelWorkbook(File workbookFile) {
        Sprint sprint = new Sprint();
        try {
            FileInputStream workbookStream = new FileInputStream(workbookFile);
            POIFSFileSystem poiFs = new POIFSFileSystem(workbookStream);
            HSSFWorkbook workbook = new HSSFWorkbook(poiFs);
            sprint.setName(workbookFile.getName());
            HSSFSheet firstSheet = workbook.getSheetAt(0);

            int numberOfRows = firstSheet.getPhysicalNumberOfRows();
            for (int i = INDEX_OF_FIRST_ROW; i < numberOfRows; i++) {
                HSSFCell estimate = firstSheet.getRow(i).getCell(SprintExcelCols.ESTIMATE.getColumnNumber());
                Task task = new Task();
                String taskLabel = firstSheet.getRow(i).getCell(SprintExcelCols.ID.getColumnNumber()).getStringCellValue();
                task.setLabel(taskLabel);
                String taskProduct = firstSheet.getRow(i).getCell(SprintExcelCols.PRODUCT.getColumnNumber()).getStringCellValue();
                task.setProduct(taskProduct);
                long estimatedTimeInSeconds = TimeUtils.changeFractionOfDayToSeconds(estimate.getNumericCellValue());
                String representationOfEstimatedTime = TimeUtils.timeInSecondsToEstimatedTime(estimatedTimeInSeconds);
                task.setEstimatedTime(representationOfEstimatedTime);
                sprint.addTask(task);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sprint;
    }

    private static File getFileFromUriIfExcel(Uri uri) {
        String path = uri.getPath();
        String extension = path.substring(path.lastIndexOf(".") + 1, path.length());
        if (extension.matches("xls[x]?")) {
            return new File(path);
        } else {
            return null;
        }
    }

}
