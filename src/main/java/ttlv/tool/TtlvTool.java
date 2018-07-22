package ttlv.tool;

import java.util.ArrayList;
import java.util.List;

public class TtlvTool {

    static class TTLV {
        //4 byte
        int tag;

        //is construct
        byte type;

        //4 byte
        int length;

        byte[] values;

        List<TTLV> childs = new ArrayList<TTLV>();
    }

    private int TOP_TAG = 0xFFFFFFFF;
    private byte CONSTRUCT = 0x1;

    public TtlvTool() {
    }

    public byte[] pack(TTLV ttlv) {
        if(ttlv == null){
            throw new IllegalArgumentException("data cannot be empty");
        }
        byte[] bytes = null;

        if(ttlv.tag == TOP_TAG) {
            for(TTLV ttlv1 : ttlv.childs) {
                if(bytes == null) {
                    bytes = pack(ttlv1);
                } else {
                    byte[] ttlv1Bytes = pack(ttlv1);
                    bytes = mergeArray(bytes, ttlv1Bytes);
                }
            }
        } else {
            byte[] mbytes = new byte[0];
            if(ttlv.type == CONSTRUCT) {
                ttlv.length = 0;
                ttlv.values = null;
                for(TTLV ttlv1 : ttlv.childs) {
                    byte[] mbytes1 = pack(ttlv1);
                    mbytes = mergeArray(mbytes, mbytes1);
                }
                ttlv.length += mbytes.length;
            } else {
                mbytes = new byte[ttlv.length];
                System.arraycopy(ttlv.values, 0, mbytes, 0, ttlv.length);
            }

            int i = 0;

            bytes = new byte[9 + ttlv.length];
            bytes[i++] = (byte) (ttlv.tag >> 24 & 0xFF);
            bytes[i++] = (byte) (ttlv.tag >> 16 & 0xFF);
            bytes[i++] = (byte) (ttlv.tag >> 8 & 0xFF);
            bytes[i++] = (byte) (ttlv.tag & 0xFF);

            bytes[i++] = ttlv.type;

            bytes[i++] = (byte) (ttlv.length >> 24 & 0xFF);
            bytes[i++] = (byte) (ttlv.length >> 16 & 0xFF);
            bytes[i++] = (byte) (ttlv.length >> 8 & 0xFF);
            bytes[i++] = (byte) (ttlv.length & 0xFF);

            System.arraycopy(mbytes, 0, bytes, i, ttlv.length);
        }
        return bytes;
    }

    public TTLV unpack(byte[] data) {
        TTLV ttlv = new TTLV();
        ttlv.tag = TOP_TAG;
        ttlv.type = CONSTRUCT;
        ttlv.length = data.length;
        ttlv.values = data;
        ttlv.childs = unPackParse(data);
        return ttlv;
    }

    private List<TTLV> unPackParse(byte[] data) {
        if(data == null) {
            throw new IllegalArgumentException("data cannot be empty");
        }
        if(data.length < 9) {
            throw new IllegalArgumentException("data length is greater than 9");
        }

        List<TTLV> ttlvs = new ArrayList<TTLV>();

        int i = 0;
        int length = data.length;

        while(i < length) {
            TTLV ttlv = new TTLV();
            ttlv.tag = data[i++] << 8 | data[i++] << 8 | data[i++] << 8 | data[i++];
            ttlv.type = data[i++];
            ttlv.length = data[i++] << 8 | data[i++] << 8 | data[i++] << 8 | data[i++];
            ttlv.values = new byte[ttlv.length];
            System.arraycopy(data, i, ttlv.values, 0, ttlv.length);
            i += ttlv.length;
            if(ttlv.type == CONSTRUCT) {
                //construct
                ttlv.childs = unPackParse(ttlv.values);
            }
            ttlvs.add(ttlv);
        }
        return ttlvs;
    }

    private byte[] mergeArray(byte[] byte1, byte[] byte2) {
        byte[] mBytes = new byte[byte1.length + byte2.length];
        System.arraycopy(byte1, 0, mBytes, 0, byte1.length);
        System.arraycopy(byte2, 0, mBytes, byte1.length, byte2.length);
        return mBytes;
    }
}
