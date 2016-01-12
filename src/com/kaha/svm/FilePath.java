package com.kaha.svm;

// import java.util.*;
import java.io.*;
// import java.util.HashMap;


class FilePath {
    static String getPath() {
        File f = new File(System.getProperty("java.class.path"));
        File dir = f.getAbsoluteFile().getParentFile();
        return dir.toString();
    }

    static String getTmpPath() {
        return getPath() + "\\..\\..\\tmp";
    }
}
