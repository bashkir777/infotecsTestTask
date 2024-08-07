package com.bashkir777.interfaces;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public abstract class FTPClient {

    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private final String server =  System.getenv("FTP_SERVER");
    private final String self = System.getenv("FTP_ACTIVE_HOST");
    private final String user;
    private final String pass;

    private static final int PORT = 21;

    public FTPClient(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }

    public void sendCommand(String command) throws IOException {
        writer.write(command + "\r\n");
        writer.flush();
    }

    public void connect() throws IOException {
        socket = new Socket(server, PORT);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        readResponse();

        sendCommand("USER " + user);
        readResponse();

        sendCommand("PASS " + pass);
        readResponse();
    }

    public String readResponse() throws IOException {
        String response = reader.readLine();
        System.out.println(response);
        return response;
    }

    public void close() throws IOException {
        sendCommand("QUIT");
        readResponse();
        socket.close();
    }

    public final String getIPFromHostname(String hostname) throws IOException {
        InetAddress inetAddress = InetAddress.getByName(hostname);
        return inetAddress.getHostAddress();
    }

    public Socket getSocket() {
        return socket;
    }

    public String getServer() {
        return server;
    }

    public BufferedReader getReader() {
        return reader;
    }

    public BufferedWriter getWriter() {
        return writer;
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }

    public String getSelf() {
        return self;
    }

    public abstract String downloadFile(String remoteFileName) throws IOException;
    public abstract void uploadFile(String data, String remoteFileName) throws IOException;
}
