package com.kaha.svm;

import java.util.*;
import com.kaha.svm.RAM;
import com.kaha.svm.Debugger;
import com.kaha.svm.ExecuteOpCode;
import java.lang.reflect.*;
import java.lang.Math;

public class CPU {
    // Default values for registers
    private static final int CP = 0x00; 
    private static final int OP = 0x00; 
    private static final int DP = 0x10; 
    private static final int SP = 0xe0; 
    private static final int RP = 0xf0;
    // Registers
    private int cp = CP;  // next opcode
    private int op = OP;  // end of Code Segment
    private int dp = DP;  // end of Data Segment
    private int sp = SP;  // top of the Data Stack
    private int rp = RP;  // top of the Return Stack
    private int ie = 0;     // instruction counter
     

    private final Map<Integer, ExecuteOpCode> codeToInstructionTable = new HashMap<>();

    private RAM ram;

    private Scanner in;
    private Stack dataStack,returnStack;
    
    // fill OPCODE to INSTRUCTION table
    {   
        // READ
        codeToInstructionTable.put(0xA3, new ExecuteOpCode() {
            public void callCode() {
                read();
            }
        });
        // WRITE
        codeToInstructionTable.put(0xA4, new ExecuteOpCode() {
            public void callCode() {
                write();
            }
        });
        // READI
        codeToInstructionTable.put(0xA5, new ExecuteOpCode() {
            public void callCode() {
                readi();
            }
        });
        // WRITEI
        codeToInstructionTable.put(0xA6, new ExecuteOpCode() {
            public void callCode() {
                writei();
            }
        });
        // ADD
        codeToInstructionTable.put(0xC0, new ExecuteOpCode() {
            public void callCode() {
                add();
            }
        });
        // SUB
        codeToInstructionTable.put(0xC1, new ExecuteOpCode() {
            public void callCode() {
                subtracts();
            }
        });
        // MUL
        codeToInstructionTable.put(0xC2, new ExecuteOpCode() {
            public void callCode() {
                multiply();
            }
        });
        // DIV
        codeToInstructionTable.put(0xC3, new ExecuteOpCode() {
            public void callCode() {
                divide();
            }
        });
        // RND
        codeToInstructionTable.put(0xC7, new ExecuteOpCode() {
            public void callCode() {
                random();
            }
        });
        // NOP
        codeToInstructionTable.put(0x00, new ExecuteOpCode() {
            public void callCode() {
                nop();
            }
        });
        // CALL
        codeToInstructionTable.put(0xE4, new ExecuteOpCode() {
            public void callCode() {
                call();
            }
        });
        // RET
        codeToInstructionTable.put(0xE5, new ExecuteOpCode() {
            public void callCode() {
                ret();
            }
        });
        // DEBUG
        codeToInstructionTable.put(0xEEEEEEEE, new ExecuteOpCode() {
            public void callCode() {
                debug();
            }
        });
        // HALT
        codeToInstructionTable.put(0xFFFFFFFF, new ExecuteOpCode() {
            public void callCode() {
                halt();
            }
        });
    }

    public CPU (RAM ram) {
        this.ram = ram;

        in = new Scanner(System.in);
        in.useDelimiter("\r\n");

        dataStack = new Stack(sp);
        returnStack = new Stack(rp);
    }

    public int getCP() { return cp; }
    public void setCP(int value) { cp = value; }

    public int getOP() { return op; }
    public void setOP (int value) { op = value; }

    public int getDP() { return dp; }
    public void setDP(int value) { dp = value; }

    public int getRP() { return returnStack.getPointer(); }
    public void setRP(int value) { returnStack.setPointer(value); }

    public int getSP() { return dataStack.getPointer(); }
    public void setSP(int value) { dataStack.setPointer(value); }

    public int getIE() { return ie; }

    public int loadCode (int opcode) {
        ram.data[op] = opcode;
        
        ++ie;
        return op++;
    }


    public void flushCodeSegment() {
        for (int i = 0; i < op ; i++) {
            ram.data[op] = 0x00;
        }
        op = OP;
    }
    public void flushRAM() {
        for (int i = op; i < ram.data.length ; i++) {
            ram.data[i] = 0x00;
        }
        setCP(CP);
        setDP(DP);
        setSP(SP);
        setRP(RP);
    }
    public void eraseMemorySegment(int pos, int length) {
        for (int i = pos; i < pos + length ; i++) {
            ram.data[i] = 0x00;
        }
    }

    

    // Main method - recursive call instruction which pointed by cp register
    public void execute() {
        codeToInstructionTable.get(ram.data[cp]).callCode();
        cp++;

        if (cp < op)
            execute();
    }
    public void executeSingleOp() {
        codeToInstructionTable.get(ram.data[cp]).callCode();
        ++cp;
    }

    // Instructions
    private void debug() {
        Debugger debugger = new Debugger(this, ram);
        debugger.execute();
    }

    private void halt() {
        System.exit(0);
    }

    private void nop() {}

    private void call() {
        // get subroutine address from the top of Data Stack
        int subPoint = dataStack.pop();
        // store current cp+1 to return stack
        returnStack.push(cp);
        // shift cp to the begining of subroutine
        cp = subPoint - 1;

    }
    private void ret() {
        // get the address from return stack
        int returnPoint = returnStack.pop();
        // shift cp to this address
        cp = returnPoint;
    }


    private void read() {
        char[] string = in.next().toCharArray();

        int stringPointer = dp;
        // store string char by char to data segment
        ram.data[dp] = string.length;
        ++dp;
        for (int i = 0; i < string.length; i++) {
            ram.data[dp] = (int) string[i];
            ++dp;
        }
        // store string pointer to Data Stack
        dataStack.push(stringPointer);
    }
    private void readi() {
        int n = in.nextInt();
        // System.out.println("you enter " + n);
        dataStack.push(n);
    }
    private void write() {
        int stringPointer = dataStack.pop();

        int stringLength = ram.data[stringPointer];

        for (int i = stringPointer + 1; i <= stringPointer + stringLength; i++) {
            System.out.print( (char) ram.data[i] );
        }
        System.out.println();
    }
    private void writei() {
        System.out.println(dataStack.pop());
    }

    // Math methods
    private void add() {
        int n2 = dataStack.pop();
        int n1 = dataStack.pop();
        dataStack.push(n1+n2);
    }
    private void subtracts() {
        int n2 = dataStack.pop();
        int n1 = dataStack.pop();
        dataStack.push(n1-n2);
    }
    private void multiply() {
        int n2 = dataStack.pop();
        int n1 = dataStack.pop();
        dataStack.push(n1*n2);
    }
    private void divide() {
        int n2 = dataStack.pop();
        int n1 = dataStack.pop();
        dataStack.push(n1/n2);
    }
    private void random() {
        int n = dataStack.pop();
        dataStack.push((int)(Math.random() * n));
    }


    // Memory control
    // private void extendRam() {
    //     ram.data = Arrays.copyOf(ram.data, ram.data.length*2);
    // }


    public class Stack {
        private int pointer;

        public Stack(int pointer) {
            this.pointer = pointer;
        }

        public void push(int value) {
            ram.data[pointer] = value;
            ++pointer;
        }

        public int pop() {
            // unnececary operation, just for clear debug
            ram.data[pointer] = 0x00;

            --pointer;
            return ram.data[pointer];
        }

        public int getPointer() {
            return pointer;
        }
        public void setPointer(int i) {
            pointer = i;
        }
    }




    public static void main(String[] args) {
        // CPU cpu = new CPU();
    }
}
