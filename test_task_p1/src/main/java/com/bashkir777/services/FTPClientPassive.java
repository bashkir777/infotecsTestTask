package com.bashkir777.services;

import com.bashkir777.interfaces.FTPClient;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class FTPClientPassive extends FTPClient {

    public FTPClientPassive(String user, String pass) {
        super(user, pass);
    }

    public Socket openConnection() throws IOException {
        connect();
        sendCommand("TYPE I");
        readResponse();

        sendCommand("PASV");
        String response = readResponse();

        String[] parts = response.split("[(),]");
        int port = Integer.parseInt(parts[5].trim()) * 256 + Integer.parseInt(parts[6].trim());
        return new Socket(getServer(), port);
    }

    @Override
    public void uploadFile(String data, String remoteFileName) throws IOException {
        Socket dataSocket = null;
        OutputStream dataOut = null;
        try {
            dataSocket = openConnection();
            sendCommand("STOR " + remoteFileName);

            byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
            dataOut = dataSocket.getOutputStream();
            dataOut.write(bytes);
            dataOut.flush();

            readResponse();

        } finally {
            if (dataOut != null) {
                dataOut.close();
            }
            if (dataSocket != null) {
                dataSocket.close();
            }
            close();
        }
    }

    @Override
    public String downloadFile(String remoteFileName) throws IOException {
        Socket dataSocket = null;
        InputStream dataIn = null;
        ByteArrayOutputStream baos = null;
        try {
            dataSocket = openConnection();
            sendCommand("RETR " + remoteFileName);
            String answer = readResponse();
            if(answer.equals("550 File not found")){
                throw new IOException("No such file");
            }

            baos = new ByteArrayOutputStream();
            dataIn = dataSocket.getInputStream();
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = dataIn.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }

            readResponse();
            return baos.toString("UTF-8");
        } finally {
            if (dataIn != null) {
                dataIn.close();
            }
            if (dataSocket != null) {
                dataSocket.close();
            }
            if (baos != null) {
                baos.close();
            }
            close();
        }
    }
}
