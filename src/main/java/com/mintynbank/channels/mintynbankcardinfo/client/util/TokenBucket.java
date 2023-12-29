package com.mintynbank.channels.mintynbankcardinfo.client.util;

import org.springframework.stereotype.Service;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * TokenBucket for rate limiting.
 * Usage:
 * TokenBucket tokenBucket = new TokenBucket(capacity, tokensPerInterval);
 * if (tokenBucket.tryConsume()) {
 *     // consume
 * } else {
 *     // Rate limit exceeded
 * }
 *
 * @author Emmanuel-Irabor
 * @since 29/12/2023
 */
@Service
public class TokenBucket {
    private final int capacity;
    private int tokens;
    private long lastRefillTimestamp;
    private final long refillInterval;

    private final Lock lock = new ReentrantLock();

    // Constants
    private static final int DEFAULT_CAPACITY = 2;
    private static final int DEFAULT_TOKENS_PER_INTERVAL = 1;
    private static final long DEFAULT_REFILL_INTERVAL = 60 * 1000; // 1 minute

    public TokenBucket() {
        this(DEFAULT_CAPACITY, DEFAULT_TOKENS_PER_INTERVAL);
    }

    public TokenBucket(int capacity, int tokensPerInterval) {
        this.capacity = capacity;
        this.tokens = capacity;
        this.lastRefillTimestamp = System.currentTimeMillis();
        this.refillInterval = DEFAULT_REFILL_INTERVAL / tokensPerInterval;
    }

    /**
     * Tries to consume a token.
     *
     * @return true if a token was consumed, false if rate limit exceeded.
     */
    public boolean tryConsume() {
        lock.lock();
        try {
            refill();
            if (tokens > 0) {
                tokens--;
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    private void refill() {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - lastRefillTimestamp;
        long tokensToAdd = elapsedTime / refillInterval;
        if (tokensToAdd > 0) {
            tokens = Math.min(capacity, tokens + (int) tokensToAdd);
            lastRefillTimestamp = currentTime;
        }
    }
}

