/**
 * @Title: Nj4xHandler.java
 * @Package com.test.server
 * @author Ryan 13684587@qq.com
 * @date 2017年11月28日 下午8:03:13
 * @version V1.0
 */
package com.test.server;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.jfx.Broker;
import com.jfx.SelectionPool;
import com.jfx.strategy.NJ4XInvalidUserNameOrPasswordException;
import com.jfx.strategy.OrderInfo;
import com.jfx.strategy.PositionChangeInfo;
import com.jfx.strategy.PositionInfo;
import com.jfx.strategy.PositionListener;
import com.jfx.strategy.Strategy;

/**
 * @ClassName: Nj4xHandler
 * @author Ryan 13684587@qq.com
 * @date 2017年11月28日 下午8:03:13
 *
 */
public class Nj4xHandler implements Runnable{

    private final Strategy strategy;

    private boolean isWhile = true;

    private String broker = null;

    private String mt4Num = null;

    private String passwd = null;

    public Nj4xHandler(String account){
        strategy = new Strategy();
        mt4AccountHandler(account);
    }

    @Override
    public void run() {
        strategy.setPositionListener(new Mt4Order(), 500, 500);
        strategy.setReconnect(false);
        boolean pwdError = false;
        try{
//            String termServerHost = "127.0.0.1";
            String termServerHost = "192.168.1.171";//my VM
            strategy.connect(termServerHost, 7788, new Broker(broker), mt4Num, passwd);
            for (String s: strategy.getSymbols()) strategy.symbolSelect(s, false);//just to make mt4 use less CPU
            while(isWhile){
                try{
                    //analog business processing
                    String s = mt4Num + "connected : " + strategy.isConnected();
                    String s1 = mt4Num + "accountBalance : " + strategy.accountBalance();
                    String s2 = mt4Num + "accountEquity : " + strategy.accountEquity();
                    String s3 = mt4Num + "accountProfit : " + strategy.accountProfit();
                    Map<Long, OrderInfo> orders = strategy.orderGetAll(SelectionPool.MODE_TRADES);
                    String s4 = mt4Num + "orders : " + orders;
//                    System.out.println(s);
//                    System.out.println(s1);
//                    System.out.println(s2);
//                    System.out.println(s3);
//                    System.out.println(s4);
                    Thread.sleep(10000L);
                }catch(Exception e){
                    e.printStackTrace();
                    isWhile = false;
                }
            }
        }catch(NJ4XInvalidUserNameOrPasswordException e){
//            e.printStackTrace();
//            pwdError = true;
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
//        strategy.disconnect();
                strategy.close(true);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        try {
            //Delete the offline account in the online list
            TestServer.online.remove(mt4Num);
            System.out.println("Online: " + TestServer.online.size());
            Thread.sleep(10000L);
            if (!pwdError) {
                TestServer.cache.put(accountToStr());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void mt4AccountHandler(String account){
        Preconditions.checkNotNull(account);
        Iterator<String> str = Splitter.on(",").omitEmptyStrings().trimResults().split(account).iterator();
        if(str.hasNext()) broker = str.next();
        if(str.hasNext()) mt4Num = str.next();
        if(str.hasNext()) passwd = str.next();
    }

    private String accountToStr(){
        return broker.concat(",").concat(mt4Num).concat(",").concat(passwd);
    }

}

class Mt4Order implements PositionListener{

    @Override
    public void onInit(PositionInfo initialPositionInfo) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onChange(PositionInfo currentPositionInfo, PositionChangeInfo changes) {
        for(OrderInfo o : changes.getNewOrders()){
            System.out.println("open = " + ToStringBuilder.reflectionToString(o));
            //Thread dormancy, analog business processing
            try {
                Thread.sleep(50L);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        for(OrderInfo o : changes.getClosedOrders()){
            System.out.println("close = " + ToStringBuilder.reflectionToString(o));
            //Thread dormancy, analog business processing
            try {
                Thread.sleep(50L);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
