package com.kaha.svm;

import com.kaha.svm.RAM;

class CPU {
	// Registers
	private int cp; // next opcode
	private int op;  // end of Code Segment
	private int dp;  // end of Data Segment
	private int rp;  // top of the Return Stack
	private int sp;  // top of the Data Stack
	private int ie; // instruction counter

	private RAM ram;

	public CPU (RAM ram) {
		this.ram = ram;
	}

	public int getCP() { return cp; }
	public void setCP(int value) { cp = value; }
	
	public int getOP() { return op; }
	public void setOP (int value) { op = value; }
	public void loadCode (int opcode) {
		ram.data[op] = opcode;
		++op;
		++ie;
	}

	public int getDP() { return dp; }
	public void setDP(int value) { dp = value; }

	public int getRP() { return rp; }
	public void setRP(int value) { rp = value; }

	public int getSP() { return sp; }
	public void setSP(int value) { sp = value; }

	public int getIE() { return ie; }

	public static void main(String[] args) {
		// CPU cpu = new CPU();
	}
}
