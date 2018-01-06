package com.Utility.DownloadUtility;

import org.apache.ignite.Ignite;

import java.io.Serializable;

import static com.Utility.IgniteUtl.IgniteUtility.startDownloadIgnite;

public class test implements Serializable {
	public static void main (String[] args) {
		try {
			///Users/luodian/Desktop/a.jpg
			Ignite ignite = startDownloadIgnite ("https://ws1.sinaimg.cn/large/006tNc79ly1fn4o49dqcaj30sg0sgmzo.jpg", "/Users/luodian/Desktop/", "1.jpg", "A", 2);
//			mod_test (16132, 5);
		} catch (Exception e) {
            e.printStackTrace();
        }
    }
}