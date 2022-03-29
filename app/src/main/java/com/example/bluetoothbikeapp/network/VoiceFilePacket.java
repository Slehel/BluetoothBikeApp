package com.example.bluetoothbikeapp.network;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class VoiceFilePacket extends BasePacket<File> {

    public VoiceFilePacket(File file) {
        super(EncoderDecoder.PacketType.VOICE_FILE);
        data = file;
    }

    @Override
    void writeDataIntoStream(OutputStream outputStream) throws IOException {
        FileInputStream inputStream;
        inputStream = new FileInputStream(data);
        byte[] b = new byte[1024];
        int c;
        while ((c = inputStream.read(b)) != -1) {
            outputStream.write(b, 0, c);
        }
    }

    @Override
    protected long getDataLength() {
        return data.length();
    }

    @Override
    public void readFromStream(long length, InputStream inputStream) throws IOException {
        long readBytes = 0;

        byte[] buffer = new byte[1024];
        int bytes;

        FileOutputStream outputStream = new FileOutputStream(data);
        while (readBytes < length) {
            bytes = inputStream.read(buffer);
            readBytes += bytes;
            outputStream.write(buffer, 0, bytes);
            Log.d(getClass().getSimpleName(), "Read bytes: " + readBytes);
        }
        Log.d(getClass().getSimpleName(), "Read bytes: " + readBytes);
        outputStream.close();
    }
}
