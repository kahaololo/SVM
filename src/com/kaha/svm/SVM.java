package com.kaha.svm;

import com.kaha.svm.*;

class SVM {

	private CPU cpu;
	private RAM ram;

	public SVM() {
		cpu = new CPU();
		ram = new RAM();
	}

	public CPU CPU() { return cpu; }
	public RAM RAM() { return ram; }


	public static void main(String[] args) {

		SVM svm = new SVM();

		Debugger d = new Debugger(svm.CPU(), svm.RAM());
		// d.dump();
	}
}
