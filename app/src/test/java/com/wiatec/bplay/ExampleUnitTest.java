package com.wiatec.bplay;

import com.wiatec.bplay.utils.AESUtil;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testDecrypt(){
        String s1 = AESUtil.encrypt("www.baidu.com" , AESUtil.key);
        System.out.println(s1);
        String result = AESUtil.decrypt(s1,AESUtil.key);
        System.out.println(result);
    }

}