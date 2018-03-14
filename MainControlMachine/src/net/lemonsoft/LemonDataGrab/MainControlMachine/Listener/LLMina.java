package net.lemonsoft.LemonDataGrab.MainControlMachine.Listener;

import net.lemonsoft.LemonDataGrab.MainControlMachine.Handler.LHMina;
import net.lemonsoft.LemonDataGrab.MainControlMachine.Util.LULog;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.HashMap;

/**
 * ServletContextListener - Mina框架初始化加载器
 * Created by LiuRi on 6/18/16.
 */
public class LLMina implements ServletContextListener {

    private IoAcceptor acceptor;

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        acceptor = new NioSocketAcceptor();
        acceptor.getSessionConfig().setReadBufferSize(2048);// 设置读取数据的缓冲区大小
        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 60);// 设置超时时间,指定时间无数据交互则断开连接
        acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"), LineDelimiter.MAC.getValue(), LineDelimiter.MAC.getValue())));
        acceptor.setHandler(new LHMina());
        try {
            acceptor.bind(new InetSocketAddress(3385));
            LULog.info("MINA FRAMEWORK LOAD SUCCESSFUL!");
        } catch (IOException e) {
            e.printStackTrace();
            LULog.error("MINA FRAMEWORK LOAD FAILED!");
        }
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("CLOSE MINA !");
        acceptor.unbind();
    }
}
