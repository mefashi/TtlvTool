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

        TtlvTool.TTLV ttlv3 = new TtlvTool.TTLV();
        ttlv3.tag = 0x1;
        ttlv3.type = 0x1;
        ttlv.childs.add(ttlv3);

        TtlvTool.TTLV ttlv4 = new TtlvTool.TTLV();
        ttlv4.tag = 0x1;
        ttlv4.type = 0x0;
        String msg4 = "hello3";
        ttlv4.length = msg4.getBytes().length;
        ttlv4.values = msg4.getBytes();

        ttlv3.childs.add(ttlv4);

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
                for(TtlvTool.TTLV ttlvfc22 : ttlvfc.childs) {
                    System.out.println(ttlvfc22.tag + " - " + ttlvfc22.type + " - " + ttlvfc22.length);
                    System.out.println(new String(ttlvfc22.values));
                }
            }
        }
    }
}