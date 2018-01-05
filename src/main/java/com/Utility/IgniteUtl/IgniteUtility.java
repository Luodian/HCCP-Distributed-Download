package com.Utility.IgniteUtl;

import com.Utility.DownloadUtility.SiteFileFetch;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteMessaging;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class IgniteUtility {
	public static void broadcast (Ignite ignite, Collection<UUID> sons_id, String url, long start, long end, int seriesID) {
		try {
			// Messaging instance over given cluster group (in this case, remote nodes).
			if (sons_id.size () == 0) {
				return;
			}
			
			IgniteMessaging rmtMsg = ignite.message (ignite.cluster ().forRemotes ().forNodeIds (sons_id));
			ArrayList<String> config = new ArrayList<> ();
			
			config.add (url);
			config.add (String.valueOf (start));
			config.add (String.valueOf (end));
			config.add (String.valueOf (seriesID));
			rmtMsg.sendOrdered ("DownloadTaskComing", config.toString (), 1);
		} catch (Exception e) {
			e.printStackTrace ();
		}
	}
	
	public static Ignite startDefaultIgnite () {
//		CacheConfiguration cacheCfg = new CacheConfiguration ("Cache");
//		cacheCfg.setCacheMode (CacheMode.PARTITIONED);
		IgniteConfiguration cfg = new IgniteConfiguration ();
//		cfg.setCacheConfiguration (cacheCfg);
		cfg.setPeerClassLoadingEnabled (true);
		Ignite ignite = Ignition.start (cfg);
		IgniteMessaging igniteMessaging = ignite.message (ignite.cluster ().forLocal ());
		//监听的消息是接受方而不是发送方，监听的是接受这个动作而不是发送这个东作，所以其实这里填写local就可以了
		igniteMessaging.remoteListen ("DownloadTaskComing", (nodeID, msg) -> {
			msg = msg.toString ().substring (1, msg.toString ().length () - 1);
			String tmp[] = msg.toString ().split (",");
			if (tmp.length != 3) {
				System.err.println ("WRONG DOWNLOAD TASK COMING MESSAGE");
				return true;
			}
			String url = tmp[0];
			long start = Long.valueOf (tmp[1]);
			long end = Long.valueOf (tmp[2]);
			String seriesID = tmp[3];
			SiteFileFetch siteFileFetch = new SiteFileFetch (url, "Tmp", nodeID + "_" + seriesID, start, end, 5);
			try {
				siteFileFetch.join ();
				//当前版本一台机子只允许一个节点
			} catch (InterruptedException e) {
				e.printStackTrace ();
			}
			return true;
		});
		return ignite;
	}
	
	public static void ConcateByteArray (ArrayList<byte[]> multi_file) {
	
	}
}
