package com.Utility.DownloadUtility;
public class Utility {
    public static void log(String message) {
        System.out.println(message);
    }

    public static void log(int message) {
        System.out.println(message);
    }

    public static void processErrorCode(int nErrorCode) {
        System.err.println("Error Code : " + nErrorCode);
    }
}