//package idv.woody.jgroup;
//
//import org.jgroups.JChannel;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//
///**
// * Created by chun-chiao on 2016/5/8.
// */
//@Component
//@Scope("singleton")
//public class SenderChannel {
//    @Autowired
//    private JChannel channel;
//    private String cluster;
//
//    public void start() throws Exception {
//        if (channel.isConnected()) {
//            System.out.println("connected already");
//        } else {
//            channel.connect("cluster");
//        }
//    }
//}
