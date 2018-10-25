package banh.along.smartwheelchair.log;

import android.content.Context;
import banh.along.smartwheelchair.log.FileLogUtility;

public class LoggerManager {
    private Context mContext;

    private FileLogUtility mAccLogger;
    private FileLogUtility mVGPSLogger;
    private FileLogUtility mVACCLogger;
    private FileLogUtility mRecognitionApiLogger;
    private FileLogUtility mRecognitionAccLogger;
    private  FileLogUtility mAccFilter;

    private FileLogUtility mAccRawBeforeFilter;
    private FileLogUtility mAccAfterKFilter;
    private FileLogUtility mAccAfterMatLabFilter;

    private static LoggerManager instances;

    public static LoggerManager getInstances(Context context) {
        if (instances == null) {
            instances = new LoggerManager(context);
        }

        return instances;
    }

    public LoggerManager(Context context) {
        this.mContext = context;

        mAccLogger = new FileLogUtility(mContext, "logs", "accRaw.txt");

        mAccRawBeforeFilter = new FileLogUtility(mContext, "logs", "accRawBeforeFilter.txt");
        mAccAfterKFilter = new FileLogUtility(mContext, "logs", "accKFilter.txt");
        mAccAfterMatLabFilter = new FileLogUtility(mContext, "logs", "accMatlabFilter.txt");

        mVGPSLogger = new FileLogUtility(mContext, "logs", "v_gps.txt");
        mVACCLogger = new FileLogUtility(mContext, "logs", "v_acc.txt");
        mRecognitionApiLogger = new FileLogUtility(mContext, "logs", "recog_api.txt");
        mRecognitionAccLogger = new FileLogUtility(mContext, "logs", "recog_acc.txt");
        mAccFilter = new FileLogUtility(mContext, "logs", "accFilter.txt");

    }

    public void open() {
        mAccLogger.openFile();
        mVGPSLogger.openFile();
        mVACCLogger.openFile();
        mRecognitionApiLogger.openFile();
        mRecognitionAccLogger.openFile();

        mAccFilter.openFile();
        mAccRawBeforeFilter.openFile();
        mAccAfterKFilter.openFile();
        mAccAfterMatLabFilter.openFile();
    }

    public void close() {
        mAccLogger.closeFile();
        mVGPSLogger.closeFile();
        mVACCLogger.closeFile();
        mRecognitionApiLogger.closeFile();
        mRecognitionAccLogger.closeFile();

        mAccFilter.closeFile();
        mAccRawBeforeFilter.closeFile();
        mAccAfterKFilter.closeFile();
        mAccAfterMatLabFilter.closeFile();
    }

    // Log accelerometer sensor data
    public void writeACCLogger(String message) {
        mAccLogger.writeLog(message);
    }


    public void writeAccRawBeforeFilterLogger(String message) {
        mAccRawBeforeFilter.writeLog(message);
    }

    public void writeAccAfterKFilterLogger(String message) {
        mAccAfterKFilter.writeLog(message);
    }

    public void writeAccAfterMatLabFilterLogger(String message) {
        mAccAfterMatLabFilter.writeLog(message);
    }


    // Log speed base on GPS
    public void writeVGPSLogger(String message) {
        mVGPSLogger.writeLog(message);
    }

    // Log speed base on accelerometer
    public void writeVACCLogger(String message) {
        mVACCLogger.writeLog(message);
    }

    // Log recognition base on google api
    public void writeRecognitionApiLogger(String message) {
        mRecognitionApiLogger.writeLog(message);
    }

    // Log recognition base on accelerometer
    public void writeRecognitionACCLogger(String message) {
        mRecognitionAccLogger.writeLog(message);
    }

    //log accFilter
    public void writeAccFilterLogger(String message){
        mAccFilter.writeLog(message);
    }

}

