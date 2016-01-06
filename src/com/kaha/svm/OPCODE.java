package com.kaha.svm;

import java.util.*;
// import java.util.HashMap;

class OPCODE {
	static final Map<String, Integer> instructionCode;
    
    static {
        Map<String, Integer> tmpMap = new HashMap<String, Integer>();
        tmpMap.put("READ"  , 0xA3);
        tmpMap.put("WRITE" , 0xA4);
        tmpMap.put("READI" , 0xA5);
        tmpMap.put("WRITEI", 0xA6);
        tmpMap.put("ADD"   , 0xC0);

        instructionCode = Collections.unmodifiableMap(tmpMap);
    }
}
