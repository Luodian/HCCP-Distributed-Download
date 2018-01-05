package com.Utility.DownloadUtility;
public class test {
    public static void _main(String[] args) {
        try {
            String url = "http://samples.leanpub.com/ignite-sample.pdf";
            String path = "C:\\Users\\YuhongZhong\\Desktop";
            String name = "ignite-sample.pdf.2";
            long fileLength = FileInfo.getFileSize(url);
            SiteFileFetch fileFetch = new SiteFileFetch(url, path, name, 10,fileLength, 5);
            fileFetch.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            String[] filePathArray = {"C:\\Users\\YuhongZhong\\Desktop\\ignite-sample.pdf.1",
                    "C:\\Users\\YuhongZhong\\Desktop\\ignite-sample.pdf.2"};
            String url = "http://samples.leanpub.com/ignite-sample.pdf";
            long fileLength = FileInfo.getFileSize(url);
            long[] startIndexArray = {0, 10};
            long[] endIndexArray = {10, (int)fileLength};
            String targetPath = "C:\\Users\\YuhongZhong\\Desktop\\ignite-sample.pdf";
            FileMerge merger = new FileMerge(filePathArray, startIndexArray, endIndexArray, targetPath);
            merger.merge();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}