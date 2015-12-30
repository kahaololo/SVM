package com.kaha.svm;

import com.kaha.svm.*;

class SVM {

	private CPU cpu;
	private RAM ram;
	private Debugger debugger;

	public SVM() {
		ram = new RAM();
		cpu = new CPU(ram);
	}

	public CPU CPU() { return cpu; }
	public RAM RAM() { return ram; }

	public void debug() {
		debugger = new Debugger(cpu, ram);
		debugger.start();
	}


	public static void main(String[] args) {

		SVM svm = new SVM();

		svm.debug();
	}
}
