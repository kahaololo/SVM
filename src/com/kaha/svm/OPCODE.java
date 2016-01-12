package com.kaha.svm;

import java.util.*;
// import java.util.HashMap;


class OPCODE {
    static final Map<String, Integer> instructionCode;

    static {
        Map<String, Integer> tmpMapIC = new HashMap<>();
        tmpMapIC.put("NOP"   , 0x00);
        tmpMapIC.put("READ"  , 0xA3);
        tmpMapIC.put("WRITE" , 0xA4);
        tmpMapIC.put("READI" , 0xA5);
        tmpMapIC.put("WRITEI", 0xA6);
        tmpMapIC.put("ADD"   , 0xC0);
        tmpMapIC.put("SUB"   , 0xC1);
        tmpMapIC.put("MUL"   , 0xC2);
        tmpMapIC.put("DIV"   , 0xC3);
        tmpMapIC.put("RND"   , 0xC7);
        tmpMapIC.put("DEBUG" , 0xEEEEEEEE);
        tmpMapIC.put("HALT"  , 0xFFFFFFFF);

        instructionCode = Collections.unmodifiableMap(tmpMapIC);
    }
}
