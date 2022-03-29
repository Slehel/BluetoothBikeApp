package com.example.bluetoothbikeapp.network;

import com.example.bluetoothbikeapp.BluetoothBikeApplication;

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
    public byte[] encode(BasePacket packet) {
        byte typeId = (byte) packet.packetType.ordinal();
        ArrayList<Byte> bytes = new ArrayList<>();
        bytes.add(typeId);

        // add length
        addDataLengthToBytes(bytes, packet.getDataLength());
        packet.addDataAsBytes(bytes);

        byte[] bytesToSend = new byte[bytes.size()];
        for (int i = 0; i < bytes.size(); i++) {
            bytesToSend[i] = bytes.get(i);
        }

        return bytesToSend;
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
