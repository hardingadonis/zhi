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
 * A class representing a wallet, include CASH and BANK.
 *
 * @since 0.1.0
 * @author Minh Vương
 * @version 0.1.0
 */
public class Wallet {

    /**
     * An enum representing the type of a wallet, either CASH or BANK.
     */
    public enum Type {
        CASH,
        BANK
    }

    private final int id;
    private final String name;
    private final Type type;
    private long balance;

    private final String hashCode;

    /**
     * Constructs a new wallet object with the specified ID, name, type, and
     * balance.
     *
     * @param id the unique identifier for the wallet
     * @param name the name of the wallet
     * @param type the type of the wallet (CASH or BANK)
     * @param balance the balance of the wallet
     */
    public Wallet(int id, String name, Type type, long balance) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.balance = balance > 0 ? balance : 0;
        this.hashCode = this.generateHashCode();
    }

    /**
     * Constructs a new wallet object with the specified ID, name, type,
     * balance, and hash code.
     *
     * @param id the unique identifier for the wallet
     * @param name the name of the wallet
     * @param type the type of the wallet (CASH or BANK)
     * @param balance the balance of the wallet
     * @param hashCode the hash code for the wallet
     * @throws ErrorVerificationException if the hash code is invalid
     */
    public Wallet(int id, String name, Type type, long balance, String hashCode) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.balance = balance > 0 ? balance : 0;

        if (this.verify(hashCode)) {
            this.hashCode = hashCode;
        } else {
            throw new ErrorVerificationException("The hash code must be: " + this.generateHashCode());
        }
    }

    /**
     * Returns the ID of the wallet.
     *
     * @return the ID of the wallet
     */
    public int getID() {
        return id;
    }

    /**
     * Returns the name of the wallet.
     *
     * @return the name of the wallet
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the type of the wallet.
     *
     * @return the type of the wallet (CASH or BANK)
     */
    public Type getType() {
        return type;
    }

    /**
     * Returns the balance of the wallet.
     *
     * @return the balance of the wallet
     */
    public long getBalance() {
        return balance;
    }

    /**
     * Returns the hash code for the wallet.
     *
     * @return the hash code for the wallet
     */
    public String getHashCode() {
        return hashCode;
    }

    /**
     * Transfers the specified amount of money from the wallet.
     *
     * @param amount the amount of money to transfer
     * @return true if the transfer is successful, false otherwise
     */
    public boolean transfer(long amount) {
        if (this.balance < amount) {
            return false;
        }

        this.balance -= amount;
        return true;
    }

    /**
     * Receives the specified amount of money to the wallet.
     *
     * @param amount the amount of money to receive
     * @return true if the receive is successful, false otherwise
     */
    public boolean receive(long amount) {
        if (amount > Long.MAX_VALUE - this.balance) {
            return false;
        }

        this.balance += amount;
        return true;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Wallet{" + "id=" + id + ", name=" + name + ", type=" + type + ", balance=" + balance + ", hashCode=" + hashCode + '}';
    }

    private String generateHashCode() {
        return Hash.generateSHA256(this.id + "::" + this.type + "::" + this.name);
    }

    private boolean verify(String hashCodeNeedToVerify) {
        return this.generateHashCode().equalsIgnoreCase(hashCodeNeedToVerify);
    }
}
