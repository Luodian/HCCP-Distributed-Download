package com.Utility.IgniteUtl;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCluster;
import org.apache.ignite.cluster.ClusterGroup;
import org.apache.ignite.cluster.ClusterNode;

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
}
