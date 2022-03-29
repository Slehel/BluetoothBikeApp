package com.example.bluetoothbikeapp.network;

import android.util.Log;

import com.example.bluetoothbikeapp.BluetoothBikeApplication;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.ArrayList;

public class EncoderDecoder {

    public static final int PACKET_LENGTH_BYTE_SIZE = 8;
    public static final int HEADER_BYTE_SIZE = 1 + PACKET_LENGTH_BYTE_SIZE;

    private final BluetoothBikeApplication application;

    public EncoderDecoder(BluetoothBikeApplication application) {
        this.application = application;
    }

    enum PacketType {

        TYPED_MESSAGE,
        VOICE_FILE,

    }

    @SuppressWarnings("unchecked")
    public void encodeInto(BasePacket packet, OutputStream outputStream) throws IOException {
        ArrayList<Byte> headerBytes = new ArrayList<>();
        // add header
        byte typeId = (byte) packet.packetType.ordinal();
        headerBytes.add(typeId);

        // add length
        addDataLengthToBytes(headerBytes, packet.getDataLength());

        // send header
        byte[] headerBytesToSend = new byte[headerBytes.size()];
        for (int i = 0; i < headerBytes.size(); i++) {
            headerBytesToSend[i] = headerBytes.get(i);
        }
        outputStream.write(headerBytesToSend);

        // send body
        packet.writeDataIntoStream(outputStream);
    }

    public BasePacket getPacketClassFromFirstByte(byte typeByte) {
        PacketType type = PacketType.values()[(int) typeByte];

        switch (type) {
            case TYPED_MESSAGE:
                return new TypedMessagePacket();
            case VOICE_FILE:
                return new VoiceFilePacket(application.getNewRecordingFile());
        }
        return null;
    }

    private void addDataLengthToBytes(ArrayList<Byte> bytes, long length) {
        Log.d(getClass().getSimpleName(), "Message length: " + length);
        BigInteger bigInt = BigInteger.valueOf(length);
        byte[] byteArray = bigInt.toByteArray();
        int zeros = PACKET_LENGTH_BYTE_SIZE - byteArray.length;
        for (int i = 0; i < zeros; i++) {
            bytes.add((byte) 0);
        }
        for (byte item : byteArray) {
            bytes.add(item);
        }
    }

}
