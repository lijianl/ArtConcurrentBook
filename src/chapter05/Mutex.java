package chapter05;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 10-2
 *
 * 独断锁实现, => 自定义锁的基本实现
 * 同步器的本质: 使用CAS维护一个锁状态和一个等待队列
 */
public class Mutex implements Lock {


    // 自定义实现的同步器，继承实现
    private static class Sync extends AbstractQueuedSynchronizer {

        private static final long serialVersionUID = -4387327721959839431L;

        // 判断是否被占用
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        // 状态为0获得锁
        public boolean tryAcquire(int acquires) {
            assert acquires == 1; // Otherwise unused
            // 使用CAS判断状态
            if (compareAndSetState(0, 1)) {
                // 记录当前线程
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        // 释放锁
        protected boolean tryRelease(int releases) {
            assert releases == 1; // Otherwise unused
            if (getState() == 0)
                throw new IllegalMonitorStateException();
            // 释放线程
            setExclusiveOwnerThread(null);
            // 恢复状态
            setState(0);
            return true;
        }

        // 返回条件
        Condition newCondition() {
            return new ConditionObject();
        }
    }

    // final
    private final Sync sync = new Sync();

    public void lock() {
        sync.acquire(1);
    }

    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    public void unlock() {
        sync.release(1);
    }

    public Condition newCondition() {
        return sync.newCondition();
    }

    public boolean isLocked() {
        return sync.isHeldExclusively();
    }

    public boolean hasQueuedThreads() {
        return sync.hasQueuedThreads();
    }

    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(timeout));
    }
}
