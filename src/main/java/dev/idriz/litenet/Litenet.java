package dev.idriz.litenet;

import org.jetbrains.annotations.NotNull;

public class Litenet {

    private final String address;
    private final int port;

    private boolean preferIOUring = true;

    public Litenet(final @NotNull String address, final int port) {
        this.address = address;
        this.port = port;
    }

    /**
     * @return Whether we prefer to opt into using IOUring when available on the system. This is *true* by default.
     */
    public boolean isPreferIOUring() {
        return preferIOUring;
    }

    /**
     * Sets whether we prefer to opt into using IOUring when available on the system.
     * @param preferIOUring Whether we prefer to opt into using IOUring when available on the system.
     */
    public void setPreferIOUring(boolean preferIOUring) {
        this.preferIOUring = preferIOUring;
    }

    /**
     * @return The address we wish to bind to.
     */
    public String getAddress() {
        return address;
    }

    /**
     * @return The port we wish to bind to.
     */
    public int getPort() {
        return port;
    }
}
