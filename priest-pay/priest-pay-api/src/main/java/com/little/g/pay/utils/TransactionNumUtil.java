package com.little.g.pay.utils;


import com.little.g.common.encrypt.MD5Utils;
import com.little.g.common.exception.ServiceDataException;
import com.little.g.pay.PayErrorCodes;
import org.apache.commons.lang3.StringUtils;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lengligang on 16/1/19.
 * trade number generate util
 */
public class TransactionNumUtil {

    private static int min=10000;
    private static int max=99999;

    private static Integer minsequence=100000;
    private static Integer cursequence=100000;
    private static Integer maxSequence=999999;

    private static String pid=getPid();


    private static final DateFormat format=new SimpleDateFormat("yyyyMMddHHmmddssSSS");

    public static final String  PREFIX_TX_CHA="CHA";

    public static final String  PREFIX_TX_NUM="KIN";

    public static final String  PREFIX_WITHDRAW_NUM="DRW";
    public static final String  PREFIX_QIJI_WITHDRAW_NUM="QWD";

    public static final String  PREFIX_LUCKY_MONEY_SEND="LMS";

    public static final String  PREFIX_LUCKY_MONEY_GRAB="LMG";

    public static final String PREFIX_LUCK_REFUND = "LMR";

    public static final String  PREFIX_STRIKE_A_BALANCE="SAB";

    public static final String  PREFIX_DEDUCT="DCT";

    public static final String  PREFIX_PREORDER="PRE";

    public static final String  PREFIX_PRODUCTION_NUM = "PDT";

    public  static String generatePreorderNum(){return generate(PREFIX_PREORDER);}
    public  static String generateChageNum(){return generate(PREFIX_TX_CHA);}
    public  static String generateGrabNum(){return generate(PREFIX_LUCKY_MONEY_GRAB);}
    public  static String generateGrabNum(Long luckyMoneyId, Long grabUid){return PREFIX_LUCKY_MONEY_GRAB + "_" + luckyMoneyId + "_" + grabUid;}
    public  static String generateLukyMoneyNum(){return generate(PREFIX_LUCKY_MONEY_SEND);}
    public  static String generateDrawNum(){return generate(PREFIX_WITHDRAW_NUM);}
    public  static String generateTranNum(){return generate(PREFIX_TX_NUM);}
    public  static String generateProductionTranNum(){return generate(PREFIX_PRODUCTION_NUM);}

    /**
     * 由于transNo只能在唯一的交易流水中使用，而实际业务使用中，如果涉及多次转账时，通常有使用固定流水的需求出现.
     * 为了达到这个目的，可以约定: 不同的业务场景各自定义业务前缀，业务流程中涉及多次转账的需求，使用相同的后缀:
     * 比如, 奇技提现
     * 1. 提现申请时生成流水后缀(比如: 54412-201701), 使用 QWD54412-201701 作为初次冻结流水
     * 2. 审批通过后,
     * 2.1 使用 QSF54412-201701 作为服务费流水
     * 2.2 使用 QII54412-201701 作为个人所得税流水
     * 2.3 使用 QDR54412-201701 作为最终提现流水
     * <p>
     * 这样各个步骤的流水都是固定的，整个处理流程均可重试
     *
     * @param transNo
     * @param newPrefix
     * @param originPrefix
     * @return
     */
    public static String withNewPrefix(String transNo, String newPrefix, String originPrefix) {
        if (!transNo.startsWith(originPrefix)) {
            throw new IllegalArgumentException("invalid originPrefix: " + originPrefix + ", transNo is: " + transNo);
        }
        return newPrefix + transNo.substring(originPrefix.length());
    }

    public static String generate(String prefix){
        if(StringUtils.length(prefix) != 3){
            throw new ServiceDataException(PayErrorCodes.PAY_ERROR);
        }
        StringBuilder sb=new StringBuilder();
        sb.append(prefix);
        sb.append(format.format(new Date()));
        sb.append(pid);
        int s = new Double(Math.random()*(max-min+1)).intValue()+min;
        sb.append(s);
        int seq;
        synchronized(TransactionNumUtil.class){
            cursequence++;
            if(cursequence>maxSequence){
                cursequence=minsequence;
            }
            seq=cursequence;
        }
        sb.append(seq);
        return sb.toString();
    }


    private static String getPid() {
        //获取进程的PID
        RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
        String name = runtime.getName(); // format: "pid@hostname"
        String md5 = MD5Utils.encode(name);
        int length = md5.length();
        List<Integer> history=new ArrayList<>();
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<5;) {
           int pos= new Double(Math.random() * length).intValue();
            if(history.contains(pos)){
                continue;
            }
            history.add(pos);
            sb.append(md5.charAt(pos));
            i++;
        }
        return sb.toString().toUpperCase();
    }

    public  static void main(String args[]){
        System.out.print(generate("ADD"));
    }
}
