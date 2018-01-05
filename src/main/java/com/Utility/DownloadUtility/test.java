package com.Utility.DownloadUtility;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;

public class test {
	public static void main (String[] args) {
		try {
			CacheConfiguration cacheCfg = new CacheConfiguration ("myCache");
			cacheCfg.setCacheMode (CacheMode.PARTITIONED);
			
			IgniteConfiguration cfg = new IgniteConfiguration ();
			cfg.setCacheConfiguration (cacheCfg);
			cfg.setPeerClassLoadingEnabled (true);
			
			Ignite ignite = Ignition.start (cfg);
			
			
		} catch (Exception e) {
            e.printStackTrace();
        }
    }
}