package com.company;

import org.apache.ignite.Ignite;

import static com.Utility.IgniteUtl.IgniteUtility.startDefaultIgnite;

class Main {
	public static void main (String[] args) {
		try {
			Ignite ignite = startDefaultIgnite ("A");
		} catch (Exception e) {
			e.printStackTrace ();
		}
	}
}

