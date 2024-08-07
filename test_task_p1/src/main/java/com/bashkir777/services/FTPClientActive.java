package com.bashkir777.services;

import com.bashkir777.interfaces.FTPClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class FTPClientActive extends FTPClient {

    public FTPClientActive(String user, String pass) {
        super(user, pass);
    }

    @Override
    public String downloadFile(String remoteFileName) throws IOException {
        ByteArrayOutputStream baos = null;
        ServerSocket dataServerSocket = null;
        Socket dataSocket = null;
        InputStream dataIn = null;
        try {
            connect();

            sendCommand("TYPE I");
            readResponse();

            dataServerSocket = new ServerSocket(0);

            int dataPort = dataServerSocket.getLocalPort();

            String ip = getIPFromHostname(getSelf()).replace('.', ',');

            String portCommand = "PORT " + ip + "," + (dataPort / 256) + "," + (dataPort % 256);

            sendCommand(portCommand);
            readResponse();

            sendCommand("RETR " + remoteFileName);
            readResponse();

            dataSocket = dataServerSocket.accept();

            baos = new ByteArrayOutputStream();
            dataIn = dataSocket.getInputStream();
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = dataIn.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
            readResponse();

        } finally {
            if (dataIn != null) {
                dataIn.close();
            }
            if (dataSocket != null) {
                dataSocket.close();
            }
            if (dataServerSocket != null) {
                dataServerSocket.close();
            }
            if (baos != null) {
                baos.close();
            }
            close();
        }

        return baos.toString("UTF-8");
    }

    @Override
    public void uploadFile(String data, String remoteFileName) throws IOException {
        ServerSocket dataServerSocket = null;
        Socket dataSocket = null;
        OutputStream dataOut = null;
        try {
            connect();

            sendCommand("TYPE I");
            readResponse();

            dataServerSocket = new ServerSocket(0);

            int dataPort = dataServerSocket.getLocalPort();

            String ip = getIPFromHostname(getSelf()).replace('.', ',');

            String portCommand = "PORT " + ip + "," + (dataPort / 256) + "," + (dataPort % 256);

            sendCommand(portCommand);
            readResponse();

            sendCommand("STOR " + remoteFileName);
            readResponse();

            dataSocket = dataServerSocket.accept();

            byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
            dataOut = dataSocket.getOutputStream();
            dataOut.write(bytes);
            dataOut.flush();

            dataSocket.shutdownOutput();

            readResponse();

        }finally {
            if (dataOut != null) {
                dataOut.close();
            }
            if (dataSocket != null) {
                dataSocket.close();
            }
            if (dataServerSocket != null) {
                dataServerSocket.close();
            }
            close();
        }
    }
}
