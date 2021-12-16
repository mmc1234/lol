package com.github.mmc1234.lol.cmd;

import com.google.common.base.*;
import com.google.common.collect.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.*;

public class CommandLineWindow {
    private JTextPane text = new JTextPane();
    private JTextField edit = new JTextField();
    private JFrame frame;
    private List<String> lines = new LinkedList();

    private volatile Queue<Consumer<String[]>> listenerQueue = new ConcurrentLinkedQueue<>();

    public void send(String[] args) {
        for(var listener : listenerQueue.stream().toList()) {
            listener.accept(args);
        }
    }

    public void addListener(Consumer<String[]> listener) {
        listenerQueue.offer(listener);
    }

    public boolean shouldClose() {
        return frame != null ?exit: false;
    }
    boolean exit = false;

    public CommandLineWindow() {
        SwingUtilities.invokeLater(()->{
            frame = new JFrame("cmd");
            frame.setSize(640, 480);
            frame.setResizable(false);
            frame.setLayout(null);
            var background = new Color(60, 63, 65);
            var background2 = new Color(53, 53, 54);
            frame.setBackground(background);

            text.setBackground(background);
            text.setForeground(new Color(96, 222, 177));
            text.setLocation(0, 0);
            text.setSize(640, 400);
            text.setEditable(false);
            edit.setBackground(background2);
            edit.setForeground(new Color(118, 206, 154, 255));
            edit.setLocation(0, 400);
            edit.setSize(640, 50);
            edit.setBorder(null);
            edit.setCaretColor(new Color(177, 255, 107));

            edit.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                }

                @Override
                public void keyPressed(KeyEvent e) {
                    if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                        var inputStr = edit.getText();
                        if(inputStr == null || inputStr.isEmpty() || inputStr.isBlank()) {
                            return;
                        }
                        edit.setText("");
                        if(inputStr.replaceFirst(" ", "").equals("clear")) {
                            lines.clear();
                            text.setText("");
                            return;
                        }
                        if(lines.size()>20) {
                            lines.remove(0);
                        }
                        lines.add(inputStr);
                        StringBuilder sb = new StringBuilder();
                        for(var line : lines) {
                            sb.append(line);
                            sb.append("\n");
                        }
                        text.setText(sb.toString());

                        boolean begin = false;
                        var str = inputStr;
                        StringBuilder psb = new StringBuilder();
                        var argList = Lists.newArrayList();
                        for(int i = 0;i<str.length(); i++) {
                            var ch = str.charAt(i);
                            if(ch == '"') {
                                begin = !begin;
                                psb.append(ch);
                            } else if(ch == '\\' && (i+1)<str.length() && str.charAt(i+1) == '"') {
                                i++;
                                psb.append(ch);
                            } else if(ch == ' ' && !begin){
                                argList.add(psb.toString().replaceFirst(" ", ""));
                                psb.setLength(0);
                            } else {
                                psb.append(ch);
                            }
                        }
                        var arg = psb.toString();
                        if(arg != null && !arg.isBlank()) {
                            argList.add(arg);
                        }
                        psb.setLength(0);
                        System.out.println(listenerQueue.size());
                        CommandLineWindow.this.send(argList.toArray(new String[argList.size()]));
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {
                }
            });
            frame.add(text);
            frame.add(edit);
            frame.setVisible(true);
            edit.requestFocus();
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    exit = true;
                }
            });
        });
    }
}
