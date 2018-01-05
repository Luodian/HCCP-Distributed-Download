package com.company;

import org.apache.ignite.*;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cluster.ClusterGroup;
import org.apache.ignite.cluster.ClusterMetrics;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.compute.ComputeTaskFuture;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.lang.IgniteCallable;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.multicast.TcpDiscoveryMulticastIpFinder;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;
import org.apache.ignite.transactions.Transaction;

import java.io.*;
import java.util.*;

class Main {
	public static void main (String[] args) {
		
		try {
//            Ignite ignite = Ignition.start ();
////			IgniteCluster igniteCluster = ignite.cluster();
////			igniteCluster.localNode();
//			// Limit broadcast to remote nodes only and
//			// enable asynchronous mode.
//			IgniteCompute compute = ignite.compute (ignite.cluster ().forRemotes ()).withAsync ();
//			// Print out hello message on remote nodes in the cluster group.
//			compute.broadcast (() -> System.out.println ("Hello Node: " + ignite.cluster ().localNode ().id ()));
//			ComputeTaskFuture<?> fut = compute.future ();
//			fut.listen (f -> System.out.println ("Finished sending broadcast job."));
//
//            IgniteConfiguration cfg = new IgniteConfiguration();
//
//            cfg.setPeerClassLoadingEnabled(true);
//
//            Map<String, String> attrs = Collections.singletonMap("ROLE", "worker");
//
//            cfg.setUserAttributes(attrs);
//
//// Start Ignite node.
//
//            Ignite ignite = Ignition.start(cfg);
//
//            IgniteCluster igniteC = ignite.cluster();
//
//            ClusterGroup clusterGroup = igniteC.forAttribute("ROLE", "worker");
//
//            IgniteCompute compute = ignite.compute(clusterGroup).withAsync ();
//
//            compute.broadcast(() -> System.out.println("Hello"));
//
//            ComputeTaskFuture<?> fut = compute.future();
//
//            fut.listen( f -> System.out.println("haha"));
//            CacheConfiguration cacheCfg = new CacheConfiguration("myCache");
//
//            cacheCfg.setCacheMode(CacheMode.PARTITIONED);
//
//            IgniteConfiguration cfg = new IgniteConfiguration();
//
//            cfg.setCacheConfiguration(cacheCfg);
//
//            cfg.setPeerClassLoadingEnabled (true);
//
//            Ignite ignite = Ignition.start(cfg);
//
//
//            IgniteTransactions transactions = ignite.transactions ();
//
//            Transaction tx = transactions.txStart();
//
//            IgniteCache<String,String> cache = ignite.cache("myCache");
//
//            String str = cache.get("Hello");
//            // Add listener for unordered messages on all remote nodes.
//            tx.commit();
//
//            System.out.println(str);

//            TcpDiscoverySpi spi = new TcpDiscoverySpi();
//            TcpDiscoveryMulticastIpFinder ipFinder = new TcpDiscoveryMulticastIpFinder();
//            ipFinder.setMulticastGroup("219.217.246.37");
//            ipFinder.setAddresses(Arrays.asList("172.20.88.65:45031..52032"));
//            spi.setIpFinder(ipFinder);
//            IgniteConfiguration cfg = new IgniteConfiguration();
//            cfg.setDiscoverySpi(spi);
//            Ignition.start(cfg);

            CacheConfiguration cacheCfg = new CacheConfiguration("myCache");
            cacheCfg.setCacheMode(CacheMode.PARTITIONED);

            IgniteConfiguration cfg = new IgniteConfiguration();
            cfg.setCacheConfiguration(cacheCfg);
            cfg.setPeerClassLoadingEnabled (true);
            Ignite ignite = Ignition.start(cfg);
            IgniteCluster igniteCluster = ignite.cluster();
            Collection<ClusterNode> collections = igniteCluster.nodes();
            for (ClusterNode c:collections
                 ) {
                System.out.println(c.metrics().getAverageCpuLoad());
            }
//            CacheConfiguration cacheCfg = new CacheConfiguration("myCache");
//            cacheCfg.setCacheMode(CacheMode.PARTITIONED);
//
//            IgniteConfiguration cfg = new IgniteConfiguration();
//            cfg.setCacheConfiguration(cacheCfg);
//            cfg.setPeerClassLoadingEnabled (true);
//
//            Ignite ignite = Ignition.start(cfg);
//
//            IgniteTransactions transactions = ignite.transactions ();
//
//            Transaction tx = transactions.txStart();
//
//            IgniteCache<String,byte[]> cache = ignite.cache("myCache");
//            File file = new File("D:/sample.jpg");
//            FileOutputStream outputStream = new FileOutputStream(file);
//            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
//            try {
//                byte[] buf = new byte[35000000];
//                int bytesRead = 0;
//                // 2.read(byte b)
//                // bytesRead = input.read(buf);
//                // 3.read(byte b[], int off, int len)
//                byte[] get_buf = cache.get("sample");
//                bufferedOutputStream.write(get_buf);
//                bufferedOutputStream.flush();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }  finally {
//                bufferedOutputStream.close();
//            }
//
//            // Add listener for unordered messages on all remote nodes.
//            tx.commit();
        } catch (Exception e) {
			e.printStackTrace ();
		}
//		try (Ignite ignite = Ignition.start()) {
//			Collection<IgniteCallable<Integer>> calls = new ArrayList<>();
//			// Iterate through all the words in the sentence and create Callable jobs.
//			for (final String word : "Count characters using callable".split(" "))
//				calls.add(word::length);
//			// Execute collection of Callables on the grid.
//			Collection<Integer> res = ignite.compute().call(calls);
//			// Add up all the results.
//			int sum = res.stream().mapToInt(Integer::intValue).sum();
//			System.out.println("Total number of characters is '" + sum + "'.");
//		}
	}
}

