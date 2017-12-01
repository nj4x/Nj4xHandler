/**
 * @Title: TestServer.java
 * @Package com.test.server
 * @author Ryan 13684587@qq.com
 * @date 2017年11月28日 下午7:20:03
 * @version V1.0
 */
package com.test.server;

import java.util.Iterator;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import com.jfx.net.JFXServer;

/**
 * @ClassName: TestServer
 * @author Ryan 13684587@qq.com
 * @date 2017年11月28日 下午7:20:03
 *
 */
public class TestServer {

    static {
        System.setProperty("jfx_server_host", Config.ApplicationHost);
    }

    public static LinkedBlockingQueue<String> cache = new LinkedBlockingQueue<String>();

    public static ConcurrentMap<String, String> online = Maps.newConcurrentMap();

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    private ExecutorService pool = Executors.newCachedThreadPool();

    private boolean isWhile = true;

    public TestServer(){
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    shutdown();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }));
    }

    public void run(){
        String account = null;
        while(isWhile){
            try{
                account = cache.poll(5000, TimeUnit.MILLISECONDS);
                if(StringUtils.isEmpty(account)) {
                    Thread.sleep(5000L);
                    continue;
                }
                //Determine if the account is duplicated
                String mt4User = getMt4User(account);
                if(online.containsKey(mt4User)) continue;
                online.put(mt4User, account);
                System.out.println("Online: " + TestServer.online.size());
                Nj4xHandler handler = new Nj4xHandler(account);
                pool.execute(handler);
            }catch(Exception e){
                //TODO
            }
        }
        pool.shutdown();
        countDownLatch.countDown();
    }

    public void shutdown(){
        isWhile = false;
        try {
            countDownLatch.await();
            JFXServer.stop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String getMt4User(String account){
        Preconditions.checkNotNull(account);
        Iterator<String> str = Splitter.on(",").omitEmptyStrings().trimResults().split(account).iterator();
        if(str.hasNext()) str.next();
        if(str.hasNext()) return str.next();
        return null;
    }

    /**
     * @throws InterruptedException
     * @Title: main
     * @param @param args
     * @return void
     * @throws
     */
    public static void main(String[] args) throws InterruptedException {
        cache.put("ThinkForexAU-Demo,44096736,wlb8xxb");
        cache.put("ThinkForexAU-Demo,44096734,0ljzdlc");
        cache.put("ThinkForexAU-Demo,44096735,pd2pvuz");
        cache.put("ThinkForexAU-Demo,44096741,xl6igiq");
        cache.put("ThinkForexAU-Demo,44096725,zsxm4yu");
        cache.put("ThinkForexAU-Demo,44096726,t5jcuiq");
        cache.put("ThinkForexAU-Demo,44096715,fya6brw");//password error
        cache.put("ThinkForexAU-Demo,44096722,pq8ombn");
        cache.put("ThinkForexAU-Demo,44096727,go0zrbd");
        cache.put("ThinkForexAU-Demo,44096728,qqq6nmk");
        cache.put("ThinkForexAU-Demo,44096716,su1ssxi");
        cache.put("ThinkForexAU-Demo,44096717,z8lohzy");
        cache.put("ThinkForexAU-Demo,44096719,1jxwaiv");
        cache.put("ThinkForexAU-Demo,44096720,w8nqhqw");
        cache.put("ThinkForexAU-Demo,44096729,as1zqxk");
        cache.put("ThinkForexAU-Demo,44096730,nox5nqx");
        cache.put("ThinkForexAU-Demo,44096731,xqos2be");
        cache.put("ThinkForexAU-Demo,44096732,o5pqhcw");
        cache.put("ThinkForexAU-Demo,44096733,olmj4ck");
        cache.put("ThinkForexAU-Demo,44096737,0golrpd");
        cache.put("ThinkForexAU-Demo,44096738,jh2spdf");
        cache.put("ThinkForexAU-Demo,44096739,xlt6mwr");
        cache.put("ThinkForexAU-Demo,44096740,nju0cxj");
        cache.put("ThinkForexAU-Demo,44096742,3lydill");
        cache.put("ThinkForexAU-Demo,44096743,xl5taup");

        cache.put("USGFX-Demo,1100278564,ml0zaze");
        cache.put("USGFX-Demo,1100278566,bhk1mkd");
        cache.put("USGFX-Demo,1100278567,pd3hdpp");//password error
        cache.put("USGFX-Demo,1100278569,cwz7nip");
        cache.put("USGFX-Demo,1100278570,mj3cyga");
        cache.put("USGFX-Demo,1100278572,3ypdlfo");
        cache.put("USGFX-Demo,1100278574,1pepsnp");
        cache.put("USGFX-Demo,1100278579,z5crbzk");
        cache.put("USGFX-Demo,1100278580,qqy7ynm");
        cache.put("USGFX-Demo,1100278581,jq0prxr");
        cache.put("USGFX-Demo,1100278583,rfn5aly");
        cache.put("USGFX-Demo,1100278584,o6swudi");
        cache.put("USGFX-Demo,1100278585,0hdlyix");
        cache.put("USGFX-Demo,1100278610,v5canox");
        cache.put("USGFX-Demo,1100278611,w4cliaw");
        cache.put("USGFX-Demo,1100278612,1gcpqqu");
        cache.put("USGFX-Demo,1100278614,8huacct");
        cache.put("USGFX-Demo,1100278615,h7klrju");
        cache.put("USGFX-Demo,1100278616,2utrodg");
        cache.put("USGFX-Demo,1100278617,8nbvxse");

        cache.put("VantageAU-Demo,100142194,u7jnhgv");
        cache.put("VantageAU-Demo,100142183,uxa2zhl");
        cache.put("VantageAU-Demo,100142193,5ytwcxo");
        cache.put("VantageAU-Demo,100142190,zdo6wto");
        cache.put("VantageAU-Demo,100131218,gwn0mfu");
        cache.put("VantageAU-Demo,100142187,ydfe8jr");
        cache.put("VantageAU-Demo,100142233,fy3mous");
        cache.put("VantageAU-Demo,100142200,rckh3lm");//password error
        cache.put("VantageAU-Demo,100142201,rucs4xl");
        cache.put("VantageAU-Demo,100142203,yav1roo");
        cache.put("VantageAU-Demo,100142202,z8uwuog");
        cache.put("VantageAU-Demo,100142204,l4rcbns");
        cache.put("VantageAU-Demo,100142180,m7gevge");
        cache.put("VantageAU-Demo,100142181,jsig7xc");
        cache.put("VantageAU-Demo,100142207,mj2awzt");
        cache.put("VantageAU-Demo,100142209,hnj1vfp");
        cache.put("VantageAU-Demo,100142182,aij0nls");
        cache.put("VantageAU-Demo,100142188,fzi5dli");
        cache.put("VantageAU-Demo,100142210,uj6dsyc");
        cache.put("VantageAU-Demo,100142189,mzgf1ac");
        cache.put("VantageAU-Demo,100142185,u1viagn");
        cache.put("VantageAU-Demo,100142192,w6ymvkl");
        cache.put("VantageAU-Demo,100142211,l8tbrat");
        cache.put("VantageAU-Demo,100142212,vk4onlb");
        cache.put("VantageAU-Demo,100143411,uwlq2xo");
        cache.put("VantageAU-Demo,100143412,k5pxjlq");
        cache.put("VantageAU-Demo,100143413,6ybobun");

        cache.put("ACYFX-Demo,835754,6yxsniu");
        cache.put("ACYFX-Demo,835755,hi5sxrm");
        cache.put("ACYFX-Demo,835757,q6ekyyv");
        cache.put("ACYFX-Demo,835759,t3wylvs");
        cache.put("ACYFX-Demo,835758,jzvn5dp");
        cache.put("ACYFX-Demo,835765,2nstlmv");
        cache.put("ACYFX-Demo,835767,bno6mfg");
        cache.put("ACYFX-Demo,835761,rmq2kmz");
        cache.put("ACYFX-Demo,835762,ft7mivf");
        cache.put("ACYFX-Demo,835763,2jbjzsc");
        cache.put("ACYFX-Demo,835769,5ghawxc");
        cache.put("ACYFX-Demo,835771,g8vcwqu");
        cache.put("ACYFX-Demo,835773,uepa5zt");
        cache.put("ACYFX-Demo,835776,idj3lir");
        cache.put("ACYFX-Demo,835779,4actiot");
        cache.put("ACYFX-Demo,835780,e7zfwag");
        cache.put("ACYFX-Demo,835782,fxuy1lz");
        cache.put("ACYFX-Demo,835783,tdp7uux");
        cache.put("ACYFX-Demo,835786,rtee8pf");
        cache.put("ACYFX-Demo,835787,gs5cjzd");
        cache.put("ACYFX-Demo,835764,o2xyuba");
        cache.put("ACYFX-Demo,835788,gju0jsw");
        cache.put("ACYFX-Demo,835789,7lortjs");
        cache.put("ACYFX-Demo,835790,m7huiie");
        cache.put("ACYFX-Demo,835791,xwdy1tz");
        cache.put("ACYFX-Demo,835792,8xxmgku");
        cache.put("ACYFX-Demo,835793,xoni3hr");
        cache.put("ACYFX-Demo,835766,0gtpvwi");
        cache.put("ACYFX-Demo,835770,j7yxvuy");
        cache.put("ACYFX-Demo,835774,ekm2jmc");
        cache.put("ACYFX-Demo,835777,neo4uen");
        cache.put("ACYFX-Demo,835778,zmjq4pa");
        cache.put("ACYFX-Demo,835781,okzq5kr");
        cache.put("ACYFX-Demo,835784,q4jlkwu");
        cache.put("ACYFX-Demo,835785,vshz7tr");
        cache.put("ACYFX-Demo,835819,extj2bn");
        cache.put("ACYFX-Demo,835820,dxp0qwv");
        cache.put("ACYFX-Demo,835824,hd8pfps");
        cache.put("ACYFX-Demo,835825,ypd5rqb");
        cache.put("ACYFX-Demo,835827,rlku0pg");
        cache.put("ACYFX-Demo,835828,0hxkczn");
        cache.put("ACYFX-Demo,835831,0lgzpdb");
        cache.put("ACYFX-Demo,835832,ydr1yru");//password error
        cache.put("ACYFX-Demo,835833,o0njasc");
        cache.put("ACYFX-Demo,835834,m1drpok");
        cache.put("ACYFX-Demo,835835,dc2jyvt");
        cache.put("ACYFX-Demo,835836,0kpzplb");
        cache.put("ACYFX-Demo,835837,nb2jyvt");
        cache.put("ACYFX-Demo,835838,k8pkjgt");
        cache.put("ACYFX-Demo,835849,junf1cn");
        //……
        TestServer server = new TestServer();
        server.run();
    }

}

