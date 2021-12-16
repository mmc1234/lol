package com.github.mmc1234.lol.cmd;

public class Main {
    public static void main(String[] args0) {
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
