package com.meng.zookeeper.client;

import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ZkClientMain {
    ZkClient zkClient;
    ZooKeeper zooKeeper;
    ZooKeeper zooKeeper2;
    private static Logger logger = LoggerFactory.getLogger(ZkClientMain.class);

    public static void main(String[] args) {

        try {
            new ZkClientMain().operateTest();
        } catch (KeeperException e) {
            logger.error("[main] KeeperException:{}", e.getMessage(), e);
        } catch (InterruptedException e) {
            logger.error("[main] InterruptedException:{}", e.getMessage(), e);
        }
    }

    /**
     * 初始化连接
     *
     * @throws IOException
     */
    public void init() throws IOException {

//        zkClient = new ZkClient("169.254.97.213:2181", 500000, 500000);
        zooKeeper = new ZooKeeper("169.254.97.213:2181", 500000, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                logger.info(watchedEvent.getPath());
            }
        });
        /*zooKeeper2 = new ZooKeeper("169.254.97.213:2181", 500000, new Watcher(){
            @Override
            public void process(WatchedEvent watchedEvent) {
                logger.info(watchedEvent.getPath());
            }
        });*/
    }

    /**
     * 操作测试
     *
     * @throws KeeperException
     * @throws InterruptedException
     */
    public void operateTest() throws KeeperException, InterruptedException {
        // 初始化连接
        try {
            init();
        } catch (IOException e) {
            logger.error("[operateTest] init error:{}", e.getMessage(), e);
        }

        // 创建一个永久节点
        createPersistent();

        // 打印节点状态
        Stat exists = zooKeeper.exists("/simple", false);
        logger.info(exists.toString());
        System.out.println(exists);

        // 给永久节点添加一个监听器
        Watcher watcher = watchedEvent -> {
            System.out.println(watchedEvent.getPath());
            System.out.println("aaa");
        };
        zooKeeper.exists("/simple", watcher);

        // 在永久节点下面，创建一个临时节点
        Stat existsEphemeral = zooKeeper.exists("/simple/ephemeral", false);
        if (existsEphemeral == null) {
            List<ACL> acls = new ArrayList<>();
            acls.add(new ACL(ZooDefs.Perms.ALL, new Id("world", "anyone")));
            zooKeeper.create("/simple/ephemeral", null, acls, CreateMode.EPHEMERAL);
        }

        // 给临时节点设置别的ip访问权限
        int perm = ZooDefs.Perms.ADMIN | ZooDefs.Perms.READ | ZooDefs.Perms.DELETE;
        List<ACL> acls = new ArrayList<>();
//        acls.add(new ACL(perm, new Id("ip", "169.254.97.213")));
//        Stat statAclW = zooKeeper.setACL("/simple/ephemeral", acls, 0);

        // 使用当前ip访问临时节点
//        try {
//            List<ACL> aclW = zooKeeper.getACL("/simple/ephemeral", statAclW);
//        }catch (KeeperException e) {
//            logger.error("[operateTest] KeeperException:{}", e.getMessage(), e);
//        } catch (InterruptedException e) {
//            logger.error("[operateTest] InterruptedException:{}", e.getMessage(), e);
//        }

        // 给临时节点设置当前ip访问权限
        acls.clear();
        acls.add(new ACL(perm, new Id("ip", "169.254.97.211")));
        Stat statAclR = zooKeeper.setACL("/simple/ephemeral", acls, 0);

        // 再次使用当前ip访问
        List<ACL> aclR = zooKeeper.getACL("/simple/ephemeral", statAclR);

        // 设置临时节点的访问权限问指定的用户
        // meng WdhJJ9lLRh7rt3T1Ji2cIzzUFI0=
        zooKeeper.addAuthInfo("digest", "meng:mengpp".getBytes());
        acls.clear();
        acls.add(new ACL(perm, new Id("auth", "meng")));
        Stat statAclA = zooKeeper.setACL("/simple/ephemeral", acls, 1);

        // 不使用用户名密码访问临时节点
//        List<ACL> aclA2N = zooKeeper2.getACL("/simple/ephemeral", new Stat());

        // 给当前用户添加认证信息
//        zooKeeper2.addAuthInfo("digest","meng:mengpp".getBytes());

        // 再次访问临时节点
//        List<ACL> aclA2Y = zooKeeper2.getACL("/simple/ephemeral", new Stat());

        // 删除临时节点
        zooKeeper.delete("/simple/ephemeral", 0);
    }

    public void createPersistent() throws KeeperException, InterruptedException {
        Stat exists = zooKeeper.exists("/simple", false);
        if (exists == null) {
            List<ACL> acls = new ArrayList<>();
            acls.add(new ACL(ZooDefs.Perms.ALL, new Id("world", "anyone")));
            zooKeeper.create("/simple", null, acls, CreateMode.PERSISTENT);
        }
    }


    public void initTest() {
        // centos.desk
        // 169.254.97.213

        logger.debug("start debug zk......");
        logger.info("start info zk......");
        logger.error("start error zk......");

        zkClient = new ZkClient("127.0.0.1:2181", 5000, 10000);
        zkClient = new ZkClient("169.254.97.211:2181", 5000, 10000);
        zkClient = new ZkClient("169.254.97.213", 5000, 10000);
    }

    public void initZkClient(String addr, int sessionTimeOut, int connectionTimeOut) {
        zkClient = new ZkClient(addr, sessionTimeOut, connectionTimeOut);
    }
}
