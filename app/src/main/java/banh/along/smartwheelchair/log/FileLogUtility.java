package banh.along.smartwheelchair.log;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileLogUtility {
    private Context mContext;
    private FileOutputStream mFileOutputStream;

    private String mFilePath = "logs";
    private String mFileName = "log.txt";

    public FileLogUtility(Context context) {
        this.mContext = context;
    }

    public FileLogUtility(Context context, String filePath, String fileName) {
        mContext = context;
        mFilePath = filePath;
        mFileName = fileName;
    }

    public void writeLog(String msg) {
        if (msg != null) {
            //Calendar calendar = Calendar.getInstance();
            //SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
            //String dateSt = dateFormat.format(calendar.getTime());
            long time = System.currentTimeMillis();
            String outputString = time + " " + msg + "\n";

            try {
                mFileOutputStream.write(outputString.getBytes());
            } catch (IOException e) {
                Log.e("iii", e.toString());
            }
        }
    }

    public void openFile() {
        File file  = new File(mContext.getExternalFilesDir(mFilePath), mFileName);
        try {
            mFileOutputStream = new FileOutputStream(file, true);
        } catch (FileNotFoundException e) {
            Log.e("iii", e.toString());
        }
    }

    public void closeFile() {
        if (mFileOutputStream != null) {
            try {
                mFileOutputStream.close();
            } catch (IOException e) {
                Log.e("iii", e.toString());
            }
        }
    }
}


