package com.github.mmc1234.lol.test;

import com.github.mmc1234.lol.cmd.*;
import org.junit.*;
import org.lwjgl.system.*;

public class CmdTest {
    @Test
    public void test() {
        var clw = new CommandLineWindow();
        clw.addListener((args)-> {
            for(var arg:args) {
                System.out.println(arg+" ");
            }
        });
        while (!clw.shouldClose()) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
