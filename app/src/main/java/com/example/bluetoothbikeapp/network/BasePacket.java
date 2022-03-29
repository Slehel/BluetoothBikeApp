package com.example.bluetoothbikeapp.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class BasePacket<T> {

    protected T data;
    public final EncoderDecoder.PacketType packetType;

    protected BasePacket(EncoderDecoder.PacketType packetType) {
        this.packetType = packetType;
    }

    abstract void writeDataIntoStream(OutputStream outputStream) throws IOException;

    protected abstract long getDataLength();

    public abstract void readFromStream(long length, InputStream inputStream) throws IOException;

    public T getData() {
        return data;
    }

}
