Simple Virtual Machine

This project takes us from the world of high-level object-oriented language programming to the low-level operations of a computer. We are going to emulate how a machine operates. And to do that, we are going to study a simple machine language and write several programs on it. That is a valuable experience that allows us to understand how a virtual machine actually works.

Virtual Machines solve a wide range of tasks from emulating the old hardware to providing a portable platform for computation. Examples of virtual machine include JVM, Dalvik, .NET CLR, Squeak (Smalltalk), CPython, DosBox, VMWare etc...

Let us create an emulator called Simple Virtual Machine (or SVM for short). As its name implies, it’s a simple machine, but also a powerful one as well. It has a CPU, memory and an external debugger with a lot of features. We can program this machine by loading bytecode images from the disk or by entering opcode mnemonics in the debugger.

Following sections give SVM specifications and explain how it works. By implementing a SVM emulator, you will actually be able to run, test and debug the SVM programs.
