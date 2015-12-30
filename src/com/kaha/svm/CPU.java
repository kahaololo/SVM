package com.kaha.svm;

import com.kaha.svm.RAM;

class CPU {
	// Registers
	private int CP  // next opcode
	   ,OP  // end of Code Segment
	   ,DP  // end of Data Segment
	   ,RP  // top of the Return Stack
	   ,SP  // top of the Data Stack
	   ,IE; // instruction counter

	private RAM ram;

	public CPU (RAM ram) {
		this.ram = ram;
	}

	public int CP() { return CP; }
	public void CP(int value) { CP = value; }
	
	public int OP() { return OP; }
	public void OP (int value) { OP = value; }
	public void loadCode (int opcode) {
		ram.data[OP] = opcode;
		OP = ++OP;
		IE = ++IE;
	}

	public int DP() { return DP; }
	public void DP(int value) { DP = value; }

	public int RP() { return RP; }
	public void RP(int value) { RP = value; }

	public int SP() { return SP; }
	public void SP(int value) { SP = value; }

	public int IE() { return IE; }

	public static void main(String[] args) {
		// CPU cpu = new CPU();
	}
}
