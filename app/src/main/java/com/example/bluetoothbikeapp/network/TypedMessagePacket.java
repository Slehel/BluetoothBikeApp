package com.example.bluetoothbikeapp.network;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class TypedMessagePacket extends BasePacket<String> {

    // "asd" -> 00000000 0000000000000000000000000000000000000000000000000000000000000011  asd

    public TypedMessagePacket() {
        super(EncoderDecoder.PacketType.TYPED_MESSAGE);
    }

    public TypedMessagePacket(String message) {
        super(EncoderDecoder.PacketType.TYPED_MESSAGE);
        data = message;
    }

    @Override
    void addDataAsBytes(ArrayList<Byte> bytes) {
        byte[] array = data.getBytes(StandardCharsets.UTF_8);
        for (byte item : array) {
            bytes.add(item);
        }
    }

    @Override
    protected long getDataLength() {
        return data.length();
    }

    @Override
    public void readFromStream(long length, InputStream inputStream) throws IOException {
        long readBytes = 0;
        StringBuilder builder = new StringBuilder();

        byte[] buffer = new byte[1024];
        int bytes;

        while (readBytes < length) {
            bytes = inputStream.read(buffer);
            readBytes += bytes;
            builder.append(new String(buffer, StandardCharsets.UTF_8));
        }
        data = builder.toString();
    }
}
