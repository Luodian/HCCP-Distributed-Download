package com.Utility.IgniteUtl;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteMessaging;

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

//	public static void
	
}
