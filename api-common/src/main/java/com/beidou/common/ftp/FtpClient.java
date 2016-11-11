package com.beidou.common.ftp;


import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by Administrator on 2015/7/10.
 */
public class FtpClient {

    private static Logger logger = Logger.getLogger("FtpClient.class");
    public FTPClient ftp;
    public ArrayList<String> arFiles;
    public List<Map<String,Object>> Lifilse;
    /**
     * 重载构造函数
     * @param isPrintCommmand 是否打印与FTPServer的交互命令
     */
    public FtpClient(boolean isPrintCommmand){
        ftp = new FTPClient();
        Lifilse =new ArrayList<Map<String,Object>>();
        arFiles = new ArrayList<String>();
        if(isPrintCommmand){
            ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
        }
    }

    /**
     * 登陆FTP服务器
     * @param host FTPServer IP地址
     * @param port FTPServer 端口
     * @param username FTPServer 登陆用户名
     * @param password FTPServer 登陆密码
     * @return 是否登录成功
     * @throws IOException
     */
    public boolean login(String host,int port,String username,String password) throws IOException {
        this.ftp.connect(host,port);
        if(FTPReply.isPositiveCompletion(this.ftp.getReplyCode())){
            if(this.ftp.login(username, password)){
                this.ftp.setControlEncoding("UTF-8");
                return true;
            }
        }
        if(this.ftp.isConnected()){
            this.ftp.disconnect();
        }
        return false;
    }

    /**
     * 关闭数据链接
     * @throws IOException
     */
    public void disConnection() throws IOException{
        if(this.ftp.isConnected()){
            this.ftp.disconnect();
        }
    }


    /**
     * 递归遍历目录下面指定的文件名
     * @param pathName 需要遍历的目录，必须以"/"开始和结束
     * @throws IOException
     */
    public void ListFiles(String pathName) throws IOException{
        if(pathName.startsWith("/")&&pathName.endsWith("/")){
            String directory = pathName;
            //更换目录到当前目录
            this.ftp.changeWorkingDirectory(directory);
            FTPFile[] files = this.ftp.listFiles();
            for(FTPFile file:files){
                if(file.isFile()){
                      arFiles.add(directory+file.getName());
                }else if(file.isDirectory()){
                    if(!file.getName().equals(".") && !file.getName().equals("..") )
                    {
                        ListFiles(directory+file.getName()+"/");
                    }

                }
            }
        }
    }

    public List<Map<String,Object>> List(String pathName) throws IOException{
        if(pathName.startsWith("/")&&pathName.endsWith("/")){
            String directory = new String(pathName.getBytes("UTF-8"),"ISO-8859-1");

           if(this.ftp.changeWorkingDirectory(directory))
           {
               directory = new String(directory.getBytes("ISO-8859-1"),"UTF-8");

               FTPFile[] files = this.ftp.listFiles();

               for(int i=0;i<files.length;i++){
                   Map<String,Object> fileMap =new HashMap<>();
                   fileMap.put("directory",directory);
                   //根据路径判断层级
                   String[] level = directory.split("/");
                   fileMap.put("level",level.length-1);

                   if(files[i].isFile()){
                       Date date=files[i].getTimestamp().getTime();
                       fileMap.put("fileTime",date);
                       fileMap.put("fileName",files[i].getName());
                       fileMap.put("isFile",0);
                       Lifilse.add(fileMap);
                   }else
                   if(files[i].isDirectory()){

                       if(!files[i].getName().equals(".") && !files[i].getName().equals("..") )
                       {
                           Date date=files[i].getTimestamp().getTime();
                           fileMap.put("fileTime",date);
                           fileMap.put("fileName",files[i].getName());
                           fileMap.put("isFile",1);
                           Lifilse.add(fileMap);
                           List(directory+files[i].getName()+"/");
                       }

                   }
               }
           }

        }
        return this.Lifilse;
    }


    public String downLoad(String remotePath,String fileName) throws IOException
    {
        remotePath = new String(remotePath.getBytes("UTF-8"),"ISO-8859-1");
        fileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");

        this.ftp.changeWorkingDirectory(remotePath);//转移到FTP服务器目录

        FTPFile[] fs = this.ftp.listFiles(remotePath);
        for(FTPFile ff:fs){

            String ffName = new String(ff.getName().getBytes("UTF-8"),"ISO-8859-1");

            if(ffName.equals(fileName)){
                File localFile = new File("D:"+"/"+ff.getName());
                OutputStream is = new FileOutputStream(localFile);
                this.ftp.retrieveFile(ff.getName(), is);
                is.close();
            }
        }

        return null;
    }
    public static void main(String[] args) throws IOException {

//        FtpClient f = new FtpClient(true);
//
//        if(f.login("192.168.1.229", 21, "track", "125y9V5F"))
//        {
//            f.List("/普丽普莱/");
//        }
//        f.disConnection();


//        Calendar calendar = Calendar.getInstance();
//        calendar.setFirstDayOfWeek(Calendar.MONDAY);
//        calendar.setTime(new Date());
//
//        String year = String.valueOf(calendar.get(Calendar.YEAR));
//        String month =String.valueOf(calendar.get(Calendar.MONTH)+1);
//        String week =String.valueOf(calendar.get(Calendar.WEEK_OF_YEAR));
//        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
//
//        System.out.print(year+"-");
//        System.out.print(month+"-");
//        System.out.print(week+"-");
//        System.out.print(day+"-");
//
//        //当前星期几
//        int w = calendar.get(Calendar.DAY_OF_WEEK);
//        System.out.println("week is = " + w);
//
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        //当月最后一天
//        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
//        Date monthLast = calendar.getTime();
//        System.out.print(format.format(monthLast)+"-");
//
//
//        //当周最后一天 单月每个周的截止时间
//        Calendar calendarW = Calendar.getInstance();
//        calendarW.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
//        calendarW.add(Calendar.DAY_OF_WEEK, 6);
//        Date weekLast = calendarW.getTime();
//        System.out.print(format.format(weekLast)+"-");


        //近60天任务完成情况
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        for(int i=0;i<=60;i++)
//        {
//            Calendar calFor = Calendar.getInstance();
//
//            calFor.set(calFor.HOUR_OF_DAY, 23);
//            calFor.set(calFor.MINUTE, 59);
//            calFor.set(calFor.SECOND, 59);
//            calFor.add(calFor.MINUTE, -24*60*i);
//            calFor.setFirstDayOfWeek(Calendar.SUNDAY);
//            //获取第几周
//            int weekNum = calFor.get(Calendar.DAY_OF_WEEK);
//            String weekOfYear =String.valueOf(calFor.get(Calendar.WEEK_OF_YEAR));
//            if(weekNum==7)
//            {
//                System.out.print("第"+weekOfYear+"周,周末日期"+format.format(calFor.getTime())+"#");
//            }
//            String day = String.valueOf(calFor.get(Calendar.DAY_OF_MONTH));
//            System.out.print("当天日期"+day+"#");
//
//        }

//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//        Date date= null;
//        try {
//            date = format.parse("2016-07-14 22:22:22");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        Calendar calFor = Calendar.getInstance();
//        calFor.setTime(date);
//        System.out.println("特定时间的日期"+format.format(calFor.getTime())+"#");
//        System.out.println("特定时间的日期"+calFor.getTime().getTime()+"#");
//        for(int i=1;i<=5;i++) {
//            Calendar calFor = Calendar.getInstance();
//            calFor.set(calFor.HOUR_OF_DAY, 23);
//            calFor.set(calFor.MINUTE, 59);
//            calFor.set(calFor.SECOND, 59);
//            calFor.setFirstDayOfWeek(Calendar.SUNDAY);
//            calFor.add(calFor.MINUTE, -24 * 60 * i);
//
//
//            String day = String.valueOf(calFor.get(Calendar.DAY_OF_MONTH));
//            System.out.println("当天日期"+day+"#");
//            int weekNum = calFor.get(Calendar.DAY_OF_WEEK);
//            System.out.println("当天星期"+weekNum+"#");
//            String weekOfYear = String.valueOf(calFor.get(Calendar.WEEK_OF_YEAR));
//            System.out.println("周次"+weekOfYear+"#");
//            Date TheoreticalTime = calFor.getTime();
//
//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            System.out.println("日期"+format.format(TheoreticalTime)+"#");
//
//            calFor.set(calFor.DAY_OF_WEEK,2);
//            System.out.println("本周二的日期"+format.format(calFor.getTime())+"#");
//
//            calFor.set(calFor.DAY_OF_MONTH,25);
//            System.out.println("本月25号的日期"+format.format(calFor.getTime())+"#");
//
//            calFor.set(calFor.MONTH,9-1);
//            calFor.set(calFor.DAY_OF_MONTH,25);
//            System.out.println("特定时间的日期"+format.format(calFor.getTime())+"#");
//        }
    }
}
