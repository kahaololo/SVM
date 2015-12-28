package com.kaha.svm;

class CPU {
	// Registers
	private int CP  // next opcode
	   ,OP  // end of Code Segment
	   ,DP  // end of Data Segment
	   ,RP  // top of the Return Stack
	   ,SP  // top of the Data Stack
	   ,IE; // instruction counter 


	public CPU () {
		CP = 0x01;
		OP = 0x0B;
		DP = 0x01A;
		RP = 0x00;
		SP = 0xffffffff;
		IE = 11;
	}

	public int CP() {
		return CP;
	}
	public int OP() {
		return OP;
	}
	public int DP() {
		return DP;
	}
	public int RP() {
		return RP;
	}
	public int SP() {
		return SP;
	}
	public int IE() {
		return IE;
	}


	public static void main(String[] args) {
		// CPU cpu = new CPU();
	}
}
