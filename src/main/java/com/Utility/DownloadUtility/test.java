package com.Utility.DownloadUtility;

import org.apache.ignite.Ignite;

import java.io.Serializable;

import static com.Utility.IgniteUtl.IgniteUtility.startDownloadIgnite;

public class test implements Serializable {
	public static void main (String[] args) {
		try {
			///Users/luodian/Desktop/a.jpg
			Ignite ignite = startDownloadIgnite ("https://ws1.sinaimg.cn/large/006tNc79ly1fn4o49dqcaj30sg0sgmzo.jpg","D:/","a.jpg","A");
//			mod_test (16132, 5);
		} catch (Exception e) {
            e.printStackTrace();
        }
    }
}