package com.collegedekho.app.filelogger;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

/**
 * Created by Bashir on 19/2/16.
 */
public class FileLogger {
    private static File SD_CARD = Environment.getExternalStorageDirectory();
    private static String logDirectoryName = ".CollegeDekho";
    private static Calendar calendar = Calendar.getInstance();
    private static String logFileName = "ApiLog.txt";
    private static File logFile;

    private static void createLog() {
        logFileName = "ApiLog(" + calendar.get(Calendar.DAY_OF_MONTH) + "_" + (calendar.get(Calendar.MONTH) + 1) + "_" + calendar.get(Calendar.YEAR) + ").txt";
        if (SD_CARD != null) {
            File logDir = new File(SD_CARD.getAbsolutePath() + File.separator + logDirectoryName);
            if (!logDir.exists()) {
                logDir.mkdirs();
            }
            File logFile = new File(logDir.getAbsolutePath() + File.separator + logFileName);
            if (!logFile.exists()) {
                try {
                    logFile.createNewFile();
                    FileLogger.logFile = logFile;
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                FileLogger.logFile = logFile;
            }
        }
    }

    public static void writeLog(String content) {
        if (content == null || content.trim().matches("")) {
            return;
        }
        createLog();
        if (logFile != null && logFile.exists()) {
            appendLog(content);
        }
    }

    private static void appendLog(String fileContent) {
        try {
            FileWriter out = new FileWriter(logFile, true);
            out.write(fileContent + "\n\n");
            out.flush();
            out.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
