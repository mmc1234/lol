/*
 * Copyright 2021. mmc1234
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
    private final JTextPane text = new JTextPane();
    private final JTextField edit = new JTextField();
    private JFrame frame;
    private final List<String> lines = new LinkedList();

    private final Queue<Consumer<String[]>> listenerQueue = new ConcurrentLinkedQueue<>();

    public void send(final String[] args) {
        for (final var listener : this.listenerQueue.stream().toList()) {
            listener.accept(args);
        }
    }

    public void addListener(final Consumer<String[]> listener) {
        this.listenerQueue.offer(listener);
    }

    public boolean shouldClose() {
        return this.frame != null && this.exit;
    }

    boolean exit;

    public CommandLineWindow() {
        SwingUtilities.invokeLater(() -> {
            this.frame = new JFrame("cmd");
            this.frame.setSize(640, 480);
            this.frame.setResizable(false);
            this.frame.setLayout(null);
            final var background = new Color(60, 63, 65);
            final var background2 = new Color(53, 53, 54);
            this.frame.setBackground(background);

            this.text.setBackground(background);
            this.text.setForeground(new Color(96, 222, 177));
            this.text.setLocation(0, 0);
            this.text.setSize(640, 400);
            this.text.setEditable(false);
            this.edit.setBackground(background2);
            this.edit.setForeground(new Color(118, 206, 154, 255));
            this.edit.setLocation(0, 400);
            this.edit.setSize(640, 50);
            this.edit.setBorder(null);
            this.edit.setCaretColor(new Color(177, 255, 107));

            this.edit.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(final KeyEvent e) {
                }

                @Override
                public void keyPressed(final KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        final var inputStr = CommandLineWindow.this.edit.getText();
                        if (inputStr == null || inputStr.isEmpty() || inputStr.isBlank()) {
                            return;
                        }
                        CommandLineWindow.this.edit.setText("");
                        if (inputStr.replaceFirst(" ", "").equals("clear")) {
                            CommandLineWindow.this.lines.clear();
                            CommandLineWindow.this.text.setText("");
                            return;
                        }
                        if (CommandLineWindow.this.lines.size() > 20) {
                            CommandLineWindow.this.lines.remove(0);
                        }
                        CommandLineWindow.this.lines.add(inputStr);
                        final StringBuilder sb = new StringBuilder();
                        for (final var line : CommandLineWindow.this.lines) {
                            sb.append(line);
                            sb.append("\n");
                        }
                        CommandLineWindow.this.text.setText(sb.toString());

                        boolean begin = false;
                        final var str = inputStr;
                        final StringBuilder psb = new StringBuilder();
                        final var argList = Lists.newArrayList();
                        for (int i = 0; i < str.length(); i++) {
                            final var ch = str.charAt(i);
                            if (ch == '"') {
                                begin = !begin;
                                psb.append('"');
                            } else if (ch == '\\' && (i + 1) < str.length() && str.charAt(i + 1) == '"') {
                                i++;
                                psb.append('\\');
                            } else if (ch == ' ' && !begin) {
                                argList.add(psb.toString().replaceFirst(" ", ""));
                                psb.setLength(0);
                            } else {
                                psb.append(ch);
                            }
                        }
                        final var arg = psb.toString();
                        if (arg != null && !arg.isBlank()) {
                            argList.add(arg);
                        }
                        psb.setLength(0);
                        send(argList.toArray(new String[argList.size()]));
                    }
                }

                @Override
                public void keyReleased(final KeyEvent e) {
                }
            });
            this.frame.add(this.text);
            this.frame.add(this.edit);
            this.frame.setVisible(true);
            this.edit.requestFocus();
            this.frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            this.frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(final WindowEvent e) {
                    CommandLineWindow.this.exit = true;
                }
            });
        });
    }
}
