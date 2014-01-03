package pl.kemot.scrum.scrumteczki2.service;

import android.net.Uri;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
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
        List<Task> taskList = new LinkedList<>();
        Sprint sprint = new Sprint();
        try {
            Workbook workbook = Workbook.getWorkbook(workbookFile);
            sprint.setName(workbookFile.getName());
            Sheet firstSheet = workbook.getSheet(0);

            int numberOfRows = firstSheet.getRows();
            for (int i = INDEX_OF_FIRST_ROW; i < numberOfRows; i++) {
                DateCell estimate = (DateCell) firstSheet.getCell(SprintExcelCols.ESTIMATE.getColumnNumber(), i);
                long timeInSeconds = TimeUtils.visualBasicTimeToSeconds(estimate.getDate().getTime());
                Task task = new Task();
                task.setLabel(firstSheet.getCell(SprintExcelCols.ID.getColumnNumber(), i).getContents());
                task.setProduct(firstSheet.getCell(SprintExcelCols.PRODUCT.getColumnNumber(), i).getContents());
                task.setEstimatedTime(TimeUtils.timeInSecondsToEstimatedTime(timeInSeconds));
                sprint.addTask(task);
            }
        } catch (IOException | BiffException e) {
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
