package com.kaha.svm;

import java.util.*;
import java.lang.*;
// import org.apache.commons.cli;

class Debugger {

	private CPU cpu;
	private RAM ram;

	private boolean exit = false;
	// private Scanner reader;

	public Debugger(CPU cpu, RAM ram) {
		this.cpu = cpu;
		this.ram = ram;
		// reader = new Scanner(System.in);

		System.out.println("** Welcome to Simple Virtual Machine **");
		System.out.println("** SVM is in the Debug Mode **\n");
	}

	public void dump() {
		System.out.printf("CP: 0x%08x\n", cpu.CP());
		System.out.printf("OP: 0x%08x\tDP: 0x%08x\n", cpu.OP(), cpu.DP());
		System.out.printf("RP: 0x%08x\tSP: 0x%08x\n", cpu.RP(), cpu.SP());
		System.out.printf("IE: 0%010d\n", cpu.IE());

		int[] data = ram.Data();

		for (int i = 0; i < data.length; i++) {
			if (i % 8 == 0)
				System.out.println();
			else
				System.out.print(" ");

			System.out.printf("%08x", data[i]);
		}

		System.out.println();
	}

	public void help() {
		System.out.println("Possible commands are:");
		System.out.println("start    - restartx execution cycle from current CP position");
		System.out.println("restart  - restartx execution cycle from current 0x00 position");
		System.out.println("dump     - CPU and RAM dump");
		System.out.println("help     - display this message");
		System.out.println("exit     - stop SVM and exit\n");
	}


	// main method
	public void start() {
		Scanner in = new Scanner(System.in);
		// chow prompt
		prompt();
		// wait for command
		String cmd = in.next();
		// parse and execute command
		switch (cmd) {
			case "start": start();
				break;
			case "dump": dump();
				break;
			case "help": help();
				break;
			case "exit": exit();
				break;
			default: help();
				break;
		}

		// exit if got exit command
		if (exit) { return; }
		
		// recursive call
		start();
	}

	public void exit() {
		exit = true;
	}


	private void prompt() {
		System.out.print("\n>> ");
	}
	

	public static void main(String[] args) {

	}
}
