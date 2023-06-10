package dev.idriz.litenet.network;

import dev.idriz.litenet.Litenet;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.incubator.channel.uring.IOUring;
import io.netty.incubator.channel.uring.IOUringEventLoopGroup;
import io.netty.incubator.channel.uring.IOUringSocketChannel;
import org.jetbrains.annotations.NotNull;

public class NetworkManager {

    private static final boolean hasIOUring = IOUring.isAvailable();

    private final Litenet litenet;

    private final EventLoopGroup bossGroup;
    private final EventLoopGroup workerGroup;

    private final Class<? extends ServerChannel> serverChannelClass = (Class<? extends ServerChannel>) getSocketChannelClass();

    public NetworkManager(final @NotNull Litenet litenet) {
        this.litenet = litenet;

        this.bossGroup = createEventLoopGroup();
        this.workerGroup = createEventLoopGroup();
    }

    public void startServer() {
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            ChannelFuture future = serverBootstrap.group(bossGroup, workerGroup)
                    .channel((Class<? extends ServerChannel>) getSocketChannelClass())
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .bind(litenet.getAddress(), litenet.getPort());
            future.sync();

            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    /**
     * @return The socket channel class we wish to use.
     */
    private Class<? extends SocketChannel> getSocketChannelClass() {
        if (litenet.isPreferIOUring() && hasIOUring) {
            return IOUringSocketChannel.class;
        } else {
            return NioSocketChannel.class;
        }
    }

    /**
     * @return The event loop group we wish to use.
     */
    private EventLoopGroup createEventLoopGroup() {
        // This is enough options. We will most likely run this on Linux.
        if (litenet.isPreferIOUring() && hasIOUring) {
            return new IOUringEventLoopGroup();
        } else {
            return new NioEventLoopGroup();
        }
    }

}
