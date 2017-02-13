package com.wiatec.bplay.utils;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Created by patrick on 2016/12/29.
 */

public class FileUtils {

    /**
     * 通过file path 和 file name 判断文件是否存在
     * @param filePath 文件绝对路径
     * @param fileName 文件名称
     * @return 是否存在
     */
    public static boolean isExists(String filePath ,String fileName ) {
        try {
            File file = new File(filePath + fileName);
            if (file.exists()) {
                //Logger.d("----file is exists");
                return true;
            } else {
                //Logger.d("----file is not exists");
                return false;
            }
        } catch (Exception e) {
            //Logger.d("----file is not exists");
            return false;
        }
    }

    /**
     * 通过file path 和 file name ，md5判断文件是否完整
     * @param filePath 文件绝对路径
     * @param fileName 文件名称
     * @param md5 参照的正确md5值
     * @return 文件是否完整
     */
    public static boolean isIntact (String filePath,String fileName,String md5){
        try {
            String localMD5 = getMD5(filePath ,fileName);
            //Logger.d(localMD5);
            if(localMD5.equalsIgnoreCase(md5)){
                // Logger.d("----file is intact");
                return true;
            }else{
                //Logger.d("----file is not intact");
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获得文件的MD5
     * @param filePath 文件绝对路径
     * @param fileName 文件名称
     * @return 文件的md5值
     */
    public static String getMD5(String filePath,String fileName) {
        try {
            File file = new File(filePath,fileName);
            if (!file.isFile()) {
                return "1";
            }
            MessageDigest digest = null;
            FileInputStream in = null;
            byte buffer[] = new byte[1024];
            int len;
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
            BigInteger bigInt = new BigInteger(1, digest.digest());
            // Log.d("----px----" ,"---->"+bigInt.toString(16));
            return bigInt.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
            return "1";
        }
    }
}
