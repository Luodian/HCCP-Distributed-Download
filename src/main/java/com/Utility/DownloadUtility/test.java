package com.Utility.DownloadUtility;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteMessaging;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cluster.ClusterGroup;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import static com.Utility.IgniteUtl.IgniteUtility.ConcateByteArray;
import static com.Utility.IgniteUtl.IgniteUtility.multicast;

public class test {
	public static void main (String[] args) {
		try {
			CacheConfiguration cacheCfg = new CacheConfiguration ("myCache");
			cacheCfg.setCacheMode (CacheMode.PARTITIONED);
			//  CacheConfiguration cacheCfg = new CacheConfiguration ("Cache");
			//	cacheCfg.setCacheMode (CacheMode.PARTITIONED);
			IgniteConfiguration cfg = new IgniteConfiguration ();
			cfg.setCacheConfiguration (cacheCfg);
			//	cfg.setCacheConfiguration (cacheCfg);
			cfg.setPeerClassLoadingEnabled (true);
			
			Ignite ignite = Ignition.start (cfg);
			
			Collection<UUID> node_ids = new ArrayList<> ();
			
			ClusterGroup cg = ignite.cluster ().forRemotes ();
			Collection<ClusterNode> clusterNodes = cg.nodes ();
			for (ClusterNode e : clusterNodes) {
				node_ids.add (e.id ());
			}
			
			String url = "https://ws1.sinaimg.cn/large/006tNc79ly1fn4o49dqcaj30sg0sgmzo.jpg";
			
			multicast (ignite, node_ids, url, 1);
			
			ArrayList<byte[]> buffer = new ArrayList<> ();
			
			IgniteMessaging igniteMessaging = ignite.message (ignite.cluster ().forLocal ());
			
			igniteMessaging.localListen (String.valueOf (1), (nodeID, msg) ->
			{
				System.out.println (msg);
				System.out.println ("LLLLLLLLLLl");
				if (msg.equals ("SUCCESS")) {
					System.out.println ("LLLLLLLLLLl");
					IgniteCache<String, ArrayList<byte[]>> tmp_cache = ignite.cache ("myCache");
					ArrayList<byte[]> tmp_byte = tmp_cache.get (String.valueOf (1));
					buffer.addAll (tmp_byte);
				}
				ConcateByteArray (buffer, "/Users/luodian/Desktop/a.jpg");
				return true;
			});


//			int [] flag = new int[1];
//			for (int item : flag)
//			{
//				item = 0;
//			}
//			ArrayList<byte []> buffer = new ArrayList<> ();
//			AtomicInteger flag2 = new AtomicInteger ();
//			//监听的消息是接受方而不是发送方，监听的是接受这个动作而不是发送这个东作，所以其实这里填写local就可以了
//			for (int i = 1; i <= 1; ++i)
//			{
//				int finalI = i;
//				igniteMessaging.remoteListen (String.valueOf (i), (nodeID, msg) ->
//				{
//					flag2.set (1);
//					if (msg.toString ().equals ("SUCCESS"))
//					{
//						flag[finalI] = 1;
//						for (int item : flag)
//						{
//							if (item == 0)
//							{
//								flag2.set (0);
//								break;
//							}
//						}
//						if (flag2.equals (1))
//						{
//							for (int j = 1; j <= 1; ++j)
//							{
//								IgniteCache<String, ArrayList<byte[]>> tmp_cache = ignite.cache ("myCache");
//								ArrayList<byte[]> tmp_byte = tmp_cache.get (String.valueOf (j));
//								buffer.addAll (tmp_byte);
//							}
//							ConcateByteArray(buffer,"/Users/luodian/Desktop/HCCP-Distributed-Download/Downloads/a.jpg");
//						}
//					}
//					return true;
//				});
//			}
			
		} catch (Exception e) {
            e.printStackTrace();
        }
    }
}