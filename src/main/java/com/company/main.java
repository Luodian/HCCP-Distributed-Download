package com.company;

import com.Utility.IgniteUtl.IgniteUtility;
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
            IgniteUtility.startDefaultIgnite();
	}
}

