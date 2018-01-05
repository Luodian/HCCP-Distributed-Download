package com.Utility.IgniteUtl;

import com.Utility.DownloadUtility.FileInfo;
import com.Utility.DownloadUtility.SiteFileFetch;
import org.apache.ignite.*;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cluster.ClusterGroup;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.transactions.Transaction;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

public class IgniteUtility {
	public static void broadcast (Ignite ignite, UUID ignite_id, String url, long start, long end) {
		IgniteCluster cluster = ignite.cluster ();
		ClusterGroup remoteGroup = cluster.forRemotes ();
		// All cluster nodes in the group.
		Collection<ClusterNode> grpNodes = remoteGroup.nodes ();
		// First node in the group (useful for groups with one node).
		ClusterNode node = remoteGroup.node ();
		// And if you know a node ID, get node by ID.
		UUID myID = ignite_id;
		node = remoteGroup.node (myID);
//		IgniteMessaging rmtMsg = ignite.message(node);
	}

	public static Ignite startDefaultIgnite(){
		CacheConfiguration cacheCfg = new CacheConfiguration ("myCache");
		cacheCfg.setCacheMode (CacheMode.PARTITIONED);

		IgniteConfiguration cfg = new IgniteConfiguration ();
		cfg.setCacheConfiguration (cacheCfg);
		cfg.setPeerClassLoadingEnabled (true);
		Ignite ignite = Ignition.start (cfg);
		IgniteTransactions transactions = ignite.transactions ();

		Transaction tx = transactions.txStart ();

		IgniteCache<String, ArrayList<byte[]>> cache = ignite.cache ("myCache");

		IgniteMessaging igniteMessaging = ignite.message(ignite.cluster().forLocal());
		//监听的消息是接受方而不是发送方，监听的是接受这个动作而不是发送这个东作，所以其实这里填写local就可以了
		igniteMessaging.remoteListen("DownloadTaskComing",(nodeID, msg) -> {
			msg = msg.toString().substring(1, msg.toString().length() - 1);
			String tmp[] = msg.toString().split(",");
			if (tmp.length != 3)
			{
				System.err.println("WRONG DOWNLOAD TASK COMING MESSAGE");
				return true;
			}
			String url = tmp[0];
			long start = Long.valueOf(tmp[1]);
			long end = Long.valueOf(tmp[2]);
			String seriesID = tmp[3];
			SiteFileFetch siteFileFetch = new SiteFileFetch(url, "Tmp", nodeID + "_" + seriesID, start, end , 5);
			try {
				siteFileFetch.join();
				//当前版本一台机子只允许一个节点
				File file = new File("Tmp/"+nodeID+"_"+seriesID);
				ArrayList<byte[]> returnResult = new ArrayList<byte[]>();
				try (FileInputStream input = new FileInputStream(file);
					BufferedInputStream inputStream = new BufferedInputStream(input)) {
					byte[] tmpBytes = new byte[1024 * 1024];
					int bytesRead = input.read (tmpBytes);
					while (bytesRead != -1){
							returnResult.add(tmpBytes);
							tmpBytes = new byte[1024 * 1024];
							bytesRead = input.read(tmpBytes);
					}
					cache.put (seriesID, returnResult);
					igniteMessaging.send(seriesID, "SUCCESS");
				} catch (Exception e) {
					e.printStackTrace ();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return true;
		});
		return ignite;
	}
}
