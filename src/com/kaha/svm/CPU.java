package com.kaha.svm;

import java.util.*;
import com.kaha.svm.RAM;
import com.kaha.svm.Debugger;
import com.kaha.svm.ExecuteOpCode;
import java.lang.reflect.*;
import java.util.Arrays;


public class CPU {
	// Registers
	private int cp = 0x00; // next opcode
	private int op = 0x00;  // end of Code Segment
	private int dp = 0x10;  // end of Data Segment
	private int sp = 0x20;  // top of the Data Stack
	private int rp = 0x30;  // top of the Return Stack
	private int ie = 0; // instruction counter

	private final Map<Integer, ExecuteOpCode> codeToInstructionTable = new HashMap<>();

	private RAM ram;

	private Scanner in;
	private DataStack ds = new DataStack();
	
	// fill OPCODE to INSTRUCTION table
	{	
		// READI
		codeToInstructionTable.put(0xA5, new ExecuteOpCode() {
			public void execute() {
				readi();
			}
		});
		// NOP
		codeToInstructionTable.put(0x00, new ExecuteOpCode() {
			public void execute() {
				nop();
			}
		});
		// DEBUG
		codeToInstructionTable.put(0xEEEEEEEE, new ExecuteOpCode() {
			public void execute() {
				debug();
			}
		});
		// HALT
		codeToInstructionTable.put(0xFFFFFFFF, new ExecuteOpCode() {
			public void execute() {
				halt();
			}
		});
	}

	public CPU (RAM ram) {
		this.ram = ram;

		in = new Scanner(System.in);
        in.useDelimiter("\r\n");
	}

	public int getCP() { return cp; }
	public void setCP(int value) { cp = value; }

	public int getOP() { return op; }
	public void setOP (int value) { op = value; }

	public int getDP() { return dp; }
	public void setDP(int value) { dp = value; }
	public void growDataSegment() {
		if (op >= dp)
			dp = op;
		++dp;
	}

	public int getRP() { return rp; }
	public void setRP(int value) { rp = value; }

	public int getSP() { return sp; }
	public void setSP(int value) { sp = value; }

	public int getIE() { return ie; }

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

	public void halt() {
		System.exit(0);
	}

	public void nop() {
		++cp;
	}

	private void readi() {
		int n = in.nextInt();
		ram.data[dp] = n;
		growDataSegment();
	}



	class DataStack {
		void push(int value) {
			ram.data[sp] = value;
			++sp;
		}

		Object pop() {
			--sp;
			return ram.data[sp];
		}
	}





	public static void main(String[] args) {
		// CPU cpu = new CPU();
	}
}
