package com.kaha.svm;

class Debugger {

	private CPU cpu;
	private RAM ram;
	// private Scanner reader;

	public Debugger(CPU cpu, RAM ram) {
		this.cpu = cpu;
		this.ram = ram;
		// reader = new Scanner(System.in);

		System.out.println("** Welcome to Simple Virtual Machine **");
		System.out.println("** SVM is in the Debug Mode **\n");

		prompt();
	}

	void prompt() {
		System.out.println(">> ");
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
	}

	public static void main(String[] args) {
		// Debugger d = new Debugger();
		// d.dump();
	}
}
