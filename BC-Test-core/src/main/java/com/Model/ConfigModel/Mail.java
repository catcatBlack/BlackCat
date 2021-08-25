package com.Model.ConfigModel;

import lombok.Data;

@Data
public class Mail {
    public static String hostname;
    public static String senderemail;
    public static String senderpassword;
    public static String receiver;
    public static String subject;
    public static String message;
    public static String reportpath;
}
