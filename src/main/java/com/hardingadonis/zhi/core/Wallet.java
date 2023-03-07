/*
 * The MIT License
 *
 * Copyright (c) 2023 Minh Vương.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.hardingadonis.zhi.core;

import com.hardingadonis.zhi.exception.ErrorVerificationException;

/**
 *
 * @author Minh Vương
 */
public class Wallet {

    public enum Type {
        CASH,
        BANK
    }

    private final int id;
    private final String name;
    private final Type type;
    private long balance;

    private final String hashCode;

    public Wallet(int id, String name, Type type, long balance) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.balance = balance >= 0 ? balance : 0;

        this.hashCode = this.generateHashCode();

        if (!this.verify()) {
            throw new ErrorVerificationException("The hash code must be: " + this.generateHashCode());
        }
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public long getBalance() {
        return balance;
    }

    public String getHashCode() {
        return hashCode;
    }

    public boolean transfer(long amount) {
        if (this.balance < amount) {
            return false;
        }

        this.balance -= amount;
        return true;
    }

    public boolean receive(long amount) {
        if (amount > Long.MAX_VALUE - this.balance) {
            return false;
        }

        this.balance += amount;
        return true;
    }

    @Override
    public String toString() {
        return "Wallet{" + "id=" + id + ", name=" + name + ", type=" + type + ", balance=" + balance + ", hashCode=" + hashCode + '}';
    }

    private String generateHashCode() {
        return Hash.generateSHA256(this.id + "." + this.type + "." + this.name);
    }

    private boolean verify() {
        return this.hashCode.equalsIgnoreCase(this.generateHashCode());
    }
}
