package com.Utility.DownloadUtility;
public class test {
	public static void main (String[] args) {
        try {
            String url = "http://samples.leanpub.com/ignite-sample.pdf";
	        String path = "D:\\temp";
	        String name = "ignite-sample.pdf";
            long fileLength = FileInfo.getFileSize(url);
	        SiteFileFetch fileFetch = new SiteFileFetch (url, path, name, 0, fileLength, 5);
            fileFetch.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}