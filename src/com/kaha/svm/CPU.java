package com.kaha.svm;

import java.util.*;
import com.kaha.svm.RAM;
import com.kaha.svm.Debugger;
import com.kaha.svm.ExecuteOpCode;
import java.lang.reflect.*;
import java.util.Arrays;


public class CPU {
	// Registers
	private int cp; // next opcode
	private int op;  // end of Code Segment
	private int dp;  // end of Data Segment
	private int rp;  // top of the Return Stack
	private int sp;  // top of the Data Stack
	private int ie; // instruction counter

	private final Map<Integer, ExecuteOpCode> codeToInstructionTable = new HashMap<>();

	private RAM ram;
	
	// fill OPCODE to INSTRUCTION table
	{
		// Debug
		codeToInstructionTable.put(0xEEEEEEEE, new ExecuteOpCode() {
			public void execute() {
				debug();
			}
		});
		// Halt
		codeToInstructionTable.put(0xFFFFFFFF, new ExecuteOpCode() {
			public void execute() {
				System.exit(0);
			}
		});
	}

	public CPU (RAM ram) {
		this.ram = ram;
	}

	public int getCP() { return cp; }
	public void setCP(int value) { cp = value; }
	
	public int getOP() { return op; }
	public void setOP (int value) { op = value; }
	public void loadCode (int opcode) {
		// flush memory
		ram.data[op] = opcode;
		++op;
		++ie;
	}

	public void flushCodeSegment() {
		for (int i = 0; i < op ; i++) {
			ram.data[op] = 0x00;
		}
		op = 0;
	}

	public void eraseMemorySegment(int pos, int length) {
		for (int i = pos; i < pos + length ; i++) {
			ram.data[i] = 0x00;
		}
	}

	public int getDP() { return dp; }
	public void setDP(int value) { dp = value; }

	public int getRP() { return rp; }
	public void setRP(int value) { rp = value; }

	public int getSP() { return sp; }
	public void setSP(int value) { sp = value; }

	public int getIE() { return ie; }

	// Main method - loop through Code Segment in memeory and execute every insstruction
	public void execute() {
		for (int i = 0; i < op; i++) {
			codeToInstructionTable.get(ram.data[i]).execute();
		}
	}

	// Instructions
	private void debug() {
		Debugger debugger = new Debugger(this, ram);
		debugger.start();
	}




	public static void main(String[] args) {
		// CPU cpu = new CPU();
	}
}
