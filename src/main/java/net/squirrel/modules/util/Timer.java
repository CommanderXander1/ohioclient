package net.squirrel.modules.util;

public class Timer {
    public long lastMs = System.currentTimeMillis();

    public void reset() {
        lastMs = System.currentTimeMillis();
    }
    public boolean hasTimeElapsed(long time, boolean reset) {
        if(System.currentTimeMillis() - lastMs > time) {
            if(reset) {
                this.reset();
            }
            return true;
        } else {
            return false;
        }
    }
}
