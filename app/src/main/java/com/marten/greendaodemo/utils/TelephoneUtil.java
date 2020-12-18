package com.marten.greendaodemo.utils;

public class TelephoneUtil {
    /*
    *
    * 验证手机号码
    * 移动：134 ，135 ，136 ，137 ，138， 139 ，147 ，148， 150， 151， 152， 157 158， 159， 172， 178， 182， 183， 184， 187 ，188， 198
      联通：130， 131， 132 ，145 ，146 ，155 ，156 ，166 ，171 ，175 ，176 ，185 186
      电信：133 ，149 ，153 ，173 ，174 ，177 ，180 ，181 ，189 ，199
      *
    * */

    public static boolean checkCellPhone(String cellphone) {
        String regex = "^1(3([0-35-9]\\d|4[1-8])|4[14-9]\\d|5([0-35689]\\d|7[1-79])|66\\d|7[2-35-8]\\d|8\\d{2}|9[13589]\\d)\\d{7}$";
        return cellphone.matches(regex);
    }

}
