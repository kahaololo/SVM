package com.kaha.svm;

import java.util.Arrays;

class RAM {
	private int[] ram;
	private static final int SIZE = 32;

	public RAM () {
		ram = new int[SIZE];
		ram[0] = 0x01;
		ram[1] = 0xA1;
		ram[3] = 0xAB;
	}

	public int Length() {
		return ram.length;
	}

	public int[] Data() {
		return ram;
	}

	// public static void main(String[] args) {
	// 	RAM ram = new RAM();
	// }
}
