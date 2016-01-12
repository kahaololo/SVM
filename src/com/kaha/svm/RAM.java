package com.kaha.svm;

import java.util.Arrays;

class RAM {
	protected int[] data;
	private static final int SIZE = 256;

	public RAM () { data = new int[SIZE]; }

	public int[] getData() {
		return data;
	}

	// public static void main(String[] args) {
	// 	RAM ram = new RAM();
	// }
}
