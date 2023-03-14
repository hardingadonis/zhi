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

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * A class contains methods for generating hashes.
 * 
 * @since 0.1.0
 * @author Minh Vương
 * @version 0.1.0
 */
public class Hash {

    /**
     * Calculates the SHA-256 hash of the given message string and returns it as
     * a hex-encoded string.
     *
     * @param message the message to hash
     * @return a string representing the SHA-256 hash of the message, or null if
     * the algorithm is not available
     */
    public static String generateSHA256(String message) {
        byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(messageBytes);

            StringBuilder hexString = new StringBuilder(2 * hash.length);
            for (byte b : hash) {
                hexString.append(String.format("%02x", b & 0xff));
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException ex) {
            return null;
        }
    }

    /**
     * Mines a SHA-256 hash with a difficulty level of 1, starting with the
     * character "0", by incrementing a nonce value in the input message until
     * the resulting hash starts with "0".
     *
     * @param message message the input message to be hashed
     * @return the mined SHA-256 hash that starts with "0"
     */
    public static String mine(String message) {
        return mine(message, "0", 1);
    }

    /**
     * Mines a SHA-256 hash with a difficulty level of 1 by incrementing a nonce
     * value in the input message until the resulting hash starts with the
     * specified first character.
     *
     * @param message message the input message to be hashed
     * @param firstLetter the first character that the hash must start with
     * @return the mined SHA-256 hash that starts with the specified first
     * character
     */
    public static String mine(String message, String firstLetter) {
        return mine(message, firstLetter, 1);
    }

    /**
     * Mines a SHA-256 hash with a certain level of difficulty by incrementing a
     * nonce value in the input message until the resulting hash starts with a
     * specified number of leading characters.
     *
     * @param message message the input message to be hashed
     * @param firstLetter firstLetter the first character that the hash must
     * start with
     * @param difficult difficulty the number of times the first character must
     * be repeated in the hash
     * @return the mined SHA-256 hash that meets the specified difficulty level
     */
    public static String mine(String message, String firstLetter, int difficult) {
        int count = 1;
        String hashCode;

        do {
            hashCode = generateSHA256((count++) + "::" + message);
        } while (!hashCode.startsWith(firstLetter.repeat(difficult)));

        return hashCode;
    }
}
