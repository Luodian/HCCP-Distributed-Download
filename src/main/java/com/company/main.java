package com.company;

import com.Utility.DownloadUtility.SiteFileFetch;
import org.apache.ignite.Ignite;

import java.io.Serializable;

import static com.Utility.IgniteUtl.IgniteUtility.startDefaultIgnite;

class Main implements Serializable {
	public static void main (String[] args) {
		try {
			Ignite ignite = startDefaultIgnite ("A");
//			SiteFileFetch siteFileFetch = new SiteFileFetch("https://ws1.sinaimg.cn/large/006tNc79ly1fn4o49dqcaj30sg0sgmzo.jpg", "D:/", "c", 0 , 111580 , 3);
//		    siteFileFetch.start();
		} catch (Exception e) {
			e.printStackTrace ();
		}
	}
}

