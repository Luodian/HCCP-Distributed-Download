package com.company;

import org.apache.ignite.Ignite;

import java.io.Serializable;

import static com.Utility.IgniteUtl.IgniteUtility.startDefaultIgnite;

class Main implements Serializable {
	public static void main (String[] args) {
		try {
			Ignite ignite = startDefaultIgnite ("A");
		} catch (Exception e) {
			e.printStackTrace ();
		}
	}
}

