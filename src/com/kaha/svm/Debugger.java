package com.kaha.svm;

import java.util.*;
import java.lang.*;
import java.io.*;

class Debugger {

    private CPU cpu;
    private RAM ram;
    private OPCODE oc;

    private Scanner in;

    public Debugger(CPU cpu, RAM ram) {
        this.cpu = cpu;
        this.ram = ram;

        System.out.println("** Welcome to Simple Virtual Machine **");
        System.out.println("** SVM is in the Debug Mode **\n");
    }

    private void dump() {
        System.out.printf("CP: 0x%08x\n", cpu.getCP());
        System.out.printf("OP: 0x%08x\tDP: 0x%08x\n", cpu.getOP(), cpu.getDP());
        System.out.printf("SP: 0x%08x\tRP: 0x%08x\n", cpu.getSP(), cpu.getRP());
        System.out.printf("IE: 0%010d\n", cpu.getIE());

        System.out.println(getRamDump());

        System.out.println();
    }

    private String getRamDump() {
        int[] data = ram.getData();
        StringBuilder output = new StringBuilder();

        for (int i = 0, j = 1; i < data.length; i++, j++) {
            
            output.append(String.format("%08x", data[i]));

            if ( j % 8 == 0 && j != data.length )
                output.append("\n");
            else
                output.append(" ");

        }

        return output.toString();
    }

    private void help() {
        System.out.println("Possible commands are:");
        System.out.println("start                   - restartx execution cycle from current CP position");
        System.out.println("restart                 - restart execution cycle from the 0x00 position");
        System.out.println("step                    - execute single instruction execution cycle");
        System.out.println("dump                    - CPU and RAM dump");
        System.out.println("load [filename]         - loads ROM from the file");
        System.out.println("store [filename]        - store RAM to the file");
        System.out.println("read ..                 - read values/mnemonics from console to memory starting from OP");
        System.out.println("erase [shift] [length]  - assigns new value to CP register");
        System.out.println("cp [xx]                 - assigns new value to CP register");
        System.out.println("op [xx]                 - assigns new value to OP register");
        System.out.println("dp [xx]                 - assigns new value to DP register");
        System.out.println("rp [xx]                 - assigns new value to RP register");
        System.out.println("sp [xx]                 - oassigns new value to SP register");
        System.out.println("help                    - display this message");
        System.out.println("exit                    - stop SVM and exit\n");
    }

    private void exit() { 
        int cp = cpu.loadCode(OPCODE.instructionCode.get("HALT"));
        cpu.setCP(cp);
        cpu.executeSingleOp();
    }

    private void restart() {
        cpu.flushRAM();
        cpu.setCP(0x00);
        cpu.execute();
    }

    private void start() {
        // cpu.flushRAM();
        cpu.execute();
    }

    private void step() {
        cpu.executeSingleOp();
    }

    private void read(String[] args) {
        cpu.flushCodeSegment();
        for (String arg : args) {
            // match each mnemonic opcode
            cpu.loadCode(OPCODE.instructionCode.get(arg));
        }
    }

    private void store(String filename) {
        String fullPath = FilePath.getTmpPath() + "\\" + filename + ".txt";
        // System.out.println(fullPath);
        try(FileOutputStream fos = new FileOutputStream(fullPath)) {
            String str = getRamDump();
            byte[] buffer = str.getBytes();
             
            fos.write(buffer, 0, buffer.length);
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    private void load(String filename) {
        String fullPath = FilePath.getTmpPath() + "\\" + filename + ".txt";
        // System.out.println(fullPath);
        StringBuilder ramDump = new StringBuilder();

        try(FileInputStream fis = new FileInputStream(fullPath)) {
            int i = -1;
            while( (i = fis.read()) !=- 1 )
                ramDump.append( (char) i );
            
            String[] words = ramDump.toString().split("\\s+");

            for (int j = 0; j < words.length; j++) {
                ram.data[j] = (int) Long.parseLong(words[j], 16);
            }

        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }



    private void eraseMemorySegment(int pos, int length) {
        // flush Code segment from 
        cpu.eraseMemorySegment(pos, length);
    }

    private void setCP(int value) { cpu.setCP(value); }
    private void setOP(int value) { cpu.setOP(value); }
    private void setDP(int value) { cpu.setDP(value); }
    private void setRP(int value) { cpu.setRP(value); }
    private void setSP(int value) { cpu.setSP(value); }


    // main method
    public void execute() {
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
            case "start": start();
                break;
            case "restart": restart();
                break;
            case "load": load(args[0]);
                break;
            case "store": store(args[0]);
                break;
            case "step": step();
                break;
            case "read": read(args);
                break;
            case "dump": dump();
                break;
            case "help": help();
                break;
            case "exit": exit();
                break;
            case "erase": eraseMemorySegment(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
                break;
            case "cp": setCP(Integer.parseInt(args[0]));
                break;
            case "op": setOP(Integer.parseInt(args[0]));
                break;
            case "dp": setDP(Integer.parseInt(args[0]));
                break;
            case "rp": setRP(Integer.parseInt(args[0]));
                break;
            case "sp": setSP(Integer.parseInt(args[0]));
                break;
            default: help();
                break;
        }

        // recursive call
        execute();
    }


    private void prompt() {
        System.out.print("\n>> ");
    }
    
}
