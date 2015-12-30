package com.kaha.svm;

import java.util.*;
import java.lang.*;
// import org.apache.commons.cli;

class Debugger {

	private CPU cpu;
	private RAM ram;
	private Scanner in;

	private boolean exit = false;
	// private Scanner reader;

	public Debugger(CPU cpu, RAM ram) {
		this.cpu = cpu;
		this.ram = ram;
		// reader = new Scanner(System.in);

		System.out.println("** Welcome to Simple Virtual Machine **");
		System.out.println("** SVM is in the Debug Mode **\n");
	}

	private void dump() {
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

	private void help() {
		System.out.println("Possible commands are:");
		// System.out.println("start    - restartx execution cycle from current CP position");
		// System.out.println("restart  - restartx execution cycle from current 0x00 position");
		System.out.println("dump     - CPU and RAM dump");
		System.out.println("read ..  - read values/mnemonics from console to memory starting from OP");
		System.out.println("cp [xx]  - assigns new value to CP register");
		System.out.println("op [xx]  - assigns new value to OP register");
		System.out.println("dp [xx]  - assigns new value to DP register");
		System.out.println("rp [xx]  - assigns new value to RP register");
		System.out.println("sp [xx]  - assigns new value to SP register");
		System.out.println("help     - display this message");
		System.out.println("exit     - stop SVM and exit\n");
	}

	private void exit() { exit = true; }

	private void read(String[] args) {
		for (String arg : args) {
			// match each mnemonic opcode
			cpu.loadCode(Integer.parseInt(arg));
		}
	}

	private void cp(int value) { cpu.CP(value); }
	private void op(int value) { cpu.OP(value); }
	private void dp(int value) { cpu.DP(value); }
	private void rp(int value) { cpu.RP(value); }
	private void sp(int value) { cpu.SP(value); }


	// main method
	public void start() {
		in = new Scanner(System.in);
		in.useDelimiter("\r\n");
		// chow prompt
		prompt();
		// wait for command
		String str = in.next();
		// try to split cmd (get command and possible args)
		String[] cmd = str.split("\\s+");

		// get command and args (Syntax sugar :)
		String command = cmd[0];
		String[] args = Arrays.copyOfRange(cmd, 1, cmd.length);

		// parse and execute command with args (if they exists)
		switch (command) {
			case "read": read(args);
				break;
			case "dump": dump();
				break;
			case "help": help();
				break;
			case "exit": exit();
				break;
			case "cp": cp(Integer.parseInt(args[0]));
				break;
			case "op": op(Integer.parseInt(args[0]));
				break;
			case "dp": dp(Integer.parseInt(args[0]));
				break;
			case "rp": rp(Integer.parseInt(args[0]));
				break;
			case "sp": sp(Integer.parseInt(args[0]));
				break;
			default: help();
				break;
		}

		// exit if got exit command
		if (exit) { return; }
		
		// recursive call
		start();
	}

	


	private void prompt() {
		System.out.print("\n>> ");
	}
	

	public static void main(String[] args) {

	}
}
