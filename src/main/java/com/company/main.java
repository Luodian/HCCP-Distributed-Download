package com.company;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteTransactions;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.transactions.Transaction;

import java.io.FileInputStream;
import java.io.InputStream;

class Main {
	public static void main (String[] args) {
		try {
			CacheConfiguration cacheCfg = new CacheConfiguration ("myCache");
			cacheCfg.setCacheMode (CacheMode.PARTITIONED);
			
			IgniteConfiguration cfg = new IgniteConfiguration ();
			cfg.setCacheConfiguration (cacheCfg);
			cfg.setPeerClassLoadingEnabled (true);
			
			Ignite ignite = Ignition.start (cfg);
			
			IgniteTransactions transactions = ignite.transactions ();
			
			Transaction tx = transactions.txStart ();
			
			IgniteCache<String, byte[]> cache = ignite.cache ("myCache");
			
			
			try (InputStream input = new FileInputStream ("/Users/luodian/Desktop/HCCP-Distributed-Download/src/main/resources/sample.jpg")) {
				byte[] buf = new byte[35000000];
				int bytesRead = 0;
				// 2.read(byte b)
				// bytesRead = input.read(buf);
				// 3.read(byte b[], int off, int len)
				bytesRead = input.read (buf, 0, 35000000);
				
				cache.put ("sample", buf);
			} catch (Exception e) {
				e.printStackTrace ();
			}
			
			// Add listener for unordered messages on all remote nodes.
			tx.commit ();
		} catch (Exception e) {
			e.printStackTrace ();
		}
	}
}

