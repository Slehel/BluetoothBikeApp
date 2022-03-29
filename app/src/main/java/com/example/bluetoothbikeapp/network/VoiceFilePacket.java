package com.example.bluetoothbikeapp.network;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class VoiceFilePacket extends BasePacket<File> {

    public VoiceFilePacket(File file) {
        super(EncoderDecoder.PacketType.VOICE_FILE);
        data = file;
    }

    @Override
    void addDataAsBytes(ArrayList<Byte> arrayList) {
        FileInputStream inputStream;
        try {
            inputStream = new FileInputStream(data);
            byte[] b = new byte[1024];
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            int c;
            while ((c = inputStream.read(b)) != -1) {
                os.write(b, 0, c);
            }
            byte[] byteArray = os.toByteArray();
            for (byte item : byteArray) {
                arrayList.add(item);
            }
        } catch (IOException e) {
            e.printStackTrace();
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
            outputStream.write(bytes);
        }
    }
}
