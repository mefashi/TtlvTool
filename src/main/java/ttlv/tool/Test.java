package ttlv.tool;

public class Test {

    public static void main(String[] args) {
        TtlvTool.TTLV ttlv = new TtlvTool.TTLV();
        ttlv.tag = 0x1;
        ttlv.type = 0x1;

        TtlvTool.TTLV ttlv1 = new TtlvTool.TTLV();
        ttlv1.tag = 0x1;
        ttlv1.type = 0x0;
        String msg1 = "hello1";
        ttlv1.length = msg1.getBytes().length;
        ttlv1.values = msg1.getBytes();
        ttlv.childs.add(ttlv1);

        TtlvTool.TTLV ttlv2 = new TtlvTool.TTLV();
        ttlv2.tag = 0x1;
        ttlv2.type = 0x0;
        String msg2 = "hello2";
        ttlv2.length = msg2.getBytes().length;
        ttlv2.values = msg2.getBytes();
        ttlv.childs.add(ttlv2);

        TtlvTool ttlvTool = new TtlvTool();
        byte[] b = ttlvTool.pack(ttlv);
        for(byte b1 : b) {
            System.out.print(b1);
        }
        System.out.println();

        TtlvTool.TTLV mTtlv = ttlvTool.unpack(b);
        System.out.println(mTtlv.tag + " - " + mTtlv.type + " - " + mTtlv.length);
        System.out.println("-------------------------");
        for(TtlvTool.TTLV ttlvf : mTtlv.childs) {
            System.out.println(ttlvf.tag + " - " + ttlvf.type + " - " + ttlvf.length);
            System.out.println("-------------------------");
            for(TtlvTool.TTLV ttlvfc : ttlvf.childs) {
                System.out.println(ttlvfc.tag + " - " + ttlvfc.type + " - " + ttlvfc.length);
                System.out.println(new String(ttlvfc.values));
            }
        }

        System.out.println("-------------------------");
        System.out.println("-------------------------");
        System.out.println("-------------------------");

        byte[] bpack = ttlvTool.pack(ttlv);
        for(byte b1 : b) {
            System.out.print(b1);
        }
        System.out.println();

        TtlvTool.TTLV mTtlvUnpack = ttlvTool.unpack(b);
        System.out.println(mTtlvUnpack.tag + " - " + mTtlvUnpack.type + " - " + mTtlvUnpack.length);
        System.out.println("-------------------------");
        for(TtlvTool.TTLV ttlvf : mTtlv.childs) {
            System.out.println(ttlvf.tag + " - " + ttlvf.type + " - " + ttlvf.length);
            System.out.println("-------------------------");
            for(TtlvTool.TTLV ttlvfc : ttlvf.childs) {
                System.out.println(ttlvfc.tag + " - " + ttlvfc.type + " - " + ttlvfc.length);
                System.out.println(new String(ttlvfc.values));
            }
        }
    }
}
