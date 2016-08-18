package dueum_rule;

/**
 * Created by user on 2016-08-11.
 */

public class DueumRule {
    public static boolean dueumRuleApplied = false;

    // ������Ģ�� ����Ǵ� ��� ����ż� ������ ù���� ���
    public static String getLastDueum(String last) {
        dueumRuleApplied = false;                       // �̰� ����Ʈ��
        int uniCode = last.hashCode();                 // last�� �����ڵ带 ����

        // �� ���� �� ���� �ٲ�� ����
        if('��'<=uniCode && uniCode<='��') { uniCode += ('��'-'��'); }
        if('��'<=uniCode && uniCode<='��') { uniCode += ('��'-'��'); }
        if('��'<=uniCode && uniCode<='�a') { uniCode += ('��'-'��'); }
        if('��'<=uniCode && uniCode<='��') { uniCode += ('��'-'��'); }

        // �� ���� �� ���� �ٲ�� ����
        if('��'<=uniCode && uniCode<='�l') { uniCode += ('��'-'��'); }
        if('��'<=uniCode && uniCode<='��') { uniCode += ('��'-'��'); }
        if('��'<=uniCode && uniCode<='��') { uniCode += ('��'-'��'); }
        if('��'<=uniCode && uniCode<='��') { uniCode += ('��'-'��'); }
        if('��'<=uniCode && uniCode<='�T') { uniCode += ('��'-'��'); }
        if('��'<=uniCode && uniCode<='��') { uniCode += ('��'-'��'); }

        // �� ���� �� ���� �ٲ�� ����
        if('��'<=uniCode && uniCode<='��') { uniCode += ('��'-'��'); }
        if('��'<=uniCode && uniCode<='�O') { uniCode += ('��'-'��'); }
        if('��'<=uniCode && uniCode<='��') { uniCode += ('��'-'��'); }
        if('��'<=uniCode && uniCode<='��') { uniCode += ('��'-'��'); }
        if('��'<=uniCode && uniCode<='��') { uniCode += ('��'-'��'); }
        if('��'<=uniCode && uniCode<='�k') { uniCode += ('��'-'��'); }

        if(uniCode!=last.hashCode()) { dueumRuleApplied = true; }    // �����ڵ尪�� �ٲ���ٸ� ������Ģ�� ����Ȱ�
        String lastDueum = String.valueOf((char) uniCode);           // char to Integer ��
        return lastDueum;
    }
}
