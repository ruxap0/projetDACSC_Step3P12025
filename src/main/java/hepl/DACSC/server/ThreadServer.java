package hepl.DACSC.server;

import hepl.DACSC.protocol.Protocole;

import java.io.IOException;
import java.net.ServerSocket;

public abstract class ThreadServer extends Thread{
    protected int port;
    protected Protocole protocole;
    protected Logger logger;

    protected ServerSocket serverSocket;

    public ThreadServer(int port, Protocole protocole, Logger logger) throws IOException {
        super("TH Server-" + port);
        this.port = port;
        this.protocole = protocole;
        this.logger = logger;

        serverSocket = new ServerSocket(port);
    }
}
