package com.example.madara.section3;

import android.nfc.Tag;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by madara on 3/3/18.
 */

public class Utils {
    private final String DEBUG_TAG = "Utils";
    private String mFileName;
    private String mPath;
    private String mContent;
    public Utils(){
//        this.mContent = content;
//        this.mFileName = filename;
//        this.mPath = path;
//       // writeOnFile(mFileName, mPath, mContent);

    }

    public  void writeOnFile(String filename, String path, String content){
        try {
            FileWriter fstream = new FileWriter(path+filename,true);
            Log.e(DEBUG_TAG,path+filename);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(content);
            out.newLine();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(DEBUG_TAG,"error");

        }
    }

}
