package com.bashkir777.services;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class FTPClient {
    private final Socket socket;
    private final BufferedReader reader;
    private final BufferedWriter writer;
    private static final int PORT = 21;

    public FTPClient(String server, String user, String pass) throws IOException {
        socket = new Socket(server, PORT);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        readResponse();

        sendCommand("USER " + user);
        readResponse();

        sendCommand("PASS " + pass);
        readResponse();

    }

    private void sendCommand(String command) throws IOException {
        writer.write(command + "\r\n");
        writer.flush();
    }

    private String readResponse() throws IOException {
        String response = reader.readLine();
        System.out.println(response);
        return response;
    }

    public void close() throws IOException {
        sendCommand("QUIT");
        readResponse();
        socket.close();
    }

    public void uploadFile(String data, String remoteFileName) throws IOException {
        try {
            sendCommand("TYPE I");
            readResponse();

            sendCommand("PASV");
            String response = readResponse();

            String[] parts = response.split("[(),]");
            String ip = parts[1] + "." + parts[2] + "." + parts[3] + "." + parts[4];
            int port = Integer.parseInt(parts[5]) * 256 + Integer.parseInt(parts[6]);

            Socket dataSocket = new Socket(ip, port);

            sendCommand("STOR " + remoteFileName);
            readResponse();

            byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
            OutputStream dataOut = dataSocket.getOutputStream();
            dataOut.write(bytes);
            dataOut.flush();
            dataOut.close();
            dataSocket.close();
            readResponse();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String downloadFile(String remoteFileName) throws IOException {
        ByteArrayOutputStream baos = null;
        try {
            sendCommand("TYPE I");
            readResponse();

            sendCommand("PASV");
            String response = readResponse();

            String[] parts = response.split("[(),]");
            String ip = parts[1] + "." + parts[2] + "." + parts[3] + "." + parts[4];
            int port = Integer.parseInt(parts[5]) * 256 + Integer.parseInt(parts[6]);

            Socket dataSocket = new Socket(ip, port);

            sendCommand("RETR " + remoteFileName);
            readResponse();

            baos = new ByteArrayOutputStream();
            InputStream dataIn = dataSocket.getInputStream();
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = dataIn.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
            dataIn.close();
            dataSocket.close();

            readResponse();
        } finally {
            if (baos != null) {
                baos.close();
            }
        }

        return baos.toString("UTF-8");
    }
}

