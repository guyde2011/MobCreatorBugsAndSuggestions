package com.guyde.mobs.components;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by CH on 05/07/2015.
 */
public class Converters {
    public static final YMLDataConverter RAW = new BasicDataConverter() {

        @Override
        public String getData(AbstractField field) {
            return field.getRawText();
        }

    };

    public static final YMLDataConverter STRING = new BasicDataConverter() {

        @Override
        public String getData(AbstractField field) {
            return "'" + field.getRawText() + "'";
        }

    };

    public static final YMLDataConverter INT = new YMLDataConverter() {

        @Override
        public String getData(AbstractField field) {
            return field.getRawText();
        }

        @Override
        public void applyData(AbstractField field){
            ((BasicField)field).setLength(50);
            field.setKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                    char c = e.getKeyChar();
                    if ( ( (c < '0') || (c > '9') ) && (c != '\b') ) {
                        e.consume();
                    }
                }

                @Override
                public void keyPressed(KeyEvent e) {}

                @Override
                public void keyReleased(KeyEvent e) {}

            });
        }

    };

    public static final YMLDataConverter NUMBER = new YMLDataConverter() {

        @Override
        public String getData(AbstractField field) {
            return field.getRawText();
        }

        @Override
        public void applyData(AbstractField field){
            ((BasicField)field).setLength(50);
            field.setKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                    char c = e.getKeyChar();
                    if ( ( ( c != '-') || (((JTextField)e.getComponent()).getText().split("-").length>1) ) && ( (c < '0') || (c > '9') ) && (c != '\b') ) {
                        e.consume();
                    }
                }

                @Override
                public void keyPressed(KeyEvent e) {}

                @Override
                public void keyReleased(KeyEvent e) {}

            });
        }

    };

    public static final YMLDataConverter COORDS = new YMLDataConverter() {

        @Override
        public String getData(AbstractField field) {
            return field.getRawText();
        }

        @Override
        public void applyData(AbstractField field){
            field.setKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                    char c = e.getKeyChar();

                    if (( ( c != '.') || (((JTextField)e.getComponent()).getText().split(".").length>1) ) && ( ( c != ',') || (((JTextField)e.getComponent()).getText().split(",").length>2) ) && ( (c < '0') || (c > '9') ) && (c != '\b') ) {
                        e.consume();
                    }
                }

                @Override
                public void keyPressed(KeyEvent e) {}

                @Override
                public void keyReleased(KeyEvent e) {}

            });
        }

    };

    public static final YMLDataConverter SKILLS = new BasicDataConverter() {

        @Override
        public String getData(AbstractField field) {
            return field.getRawText().replaceAll(" ","_").toUpperCase();
        }

    };

    public static final YMLDataConverter SOUND = new BasicDataConverter() {

        @Override
        public String getData(AbstractField field) {

            return field.getRawText().replaceAll(" ","_").toUpperCase();
        }

    };

    public static final YMLDataConverter MOB = new BasicDataConverter() {

        @Override
        public String getData(AbstractField field) {

            return field.getRawText().replaceAll(" ","").toLowerCase();
        }

    };

    public static final YMLDataConverter ELEMENT_DMG = new YMLDataConverter() {

        @Override
        public boolean shouldConvert(AbstractField field){
            return !field.getRawText().equals("0-0") && Pattern.compile("[0-9]+.{0,1}[0-9]*\\-[0-9]+.{0,1}[0-9]*").matcher(field.getRawText()).matches();
        }
        @Override
        public String getData(AbstractField field) {
            return field.getRawText();
        }

        @Override
        public void applyData(AbstractField field){
            ((BasicField)field).setLength(100);
            field.setKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                    char c = e.getKeyChar();

                    if ( ( ( c != '-') || (((JTextField)e.getComponent()).getText().split("-").length>1) ) && ( (c < '0') || (c > '9') ) && (c != '\b') ) {
                        e.consume();
                    }
                }

                @Override
                public void keyPressed(KeyEvent e) {}

                @Override
                public void keyReleased(KeyEvent e) {}

            });
        }

    };

    private static final String[] elements = new String[]{"Thunder","Fire","Water","Air","Earth"};
    private static final String[] weak = new String[]{"Earth","Water","Thunder","Fire","Air"};
    private static final String[] strong = new String[]{"Water","Air","Fire","Earth","Thunder"};
    private static final Map<String,JTextField> map = new HashMap<String,JTextField>();

    public static void addElement(String element, OrderedPanel panel){
        map.put(element,(JTextField)panel.fieldsPanel.getComponent(panel.fieldsPanel.getComponentCount()-1));
    }

    public static final YMLDataConverter ELEMENT_DEF(final String el, final TexturedToggleButton button){
        return new YMLDataConverter() {

            @Override
            public boolean shouldConvert(AbstractField field){
                return !field.getRawText().equals("0") && !field.getRawText().equals("-0") && Pattern.compile("\\-{0,1}[0-9]+.{0,1}[0-9]*").matcher(field.getRawText()).matches();
            }
            @Override
            public String getData(AbstractField field) {
                return field.getRawText();
            }

            @Override
            public void applyData(final AbstractField field){
                ((BasicField)field).setLength(50);
                field.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {

                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        if (button.getState() && !field.getRawText().startsWith("-") && !field.getRawText().equals("0")) {
                                String str = weak[Arrays.asList(elements).indexOf(el)];
                                map.get(str).setText("-" + field.getRawText());
                            }
                        }

                });
                field.setKeyListener(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        char c = e.getKeyChar();

                        if ( ( c != '-' ) && ( (c < '0') || (c > '9') ) && (c != '\b') ) {
                            e.consume();
                        }
                    }

                    @Override
                    public void keyPressed(KeyEvent e) {}

                    @Override
                    public void keyReleased(KeyEvent e) {}

                });
            }

        };
    }







}
