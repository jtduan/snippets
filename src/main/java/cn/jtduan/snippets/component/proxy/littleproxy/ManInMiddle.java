package cn.jtduan.snippets.component.proxy.littleproxy;

import java.io.FileNotFoundException;
import java.net.InetSocketAddress;
import java.util.Queue;

import org.littleshoot.proxy.ChainedProxy;
import org.littleshoot.proxy.ChainedProxyAdapter;
import org.littleshoot.proxy.ChainedProxyManager;
import org.littleshoot.proxy.HttpFilters;
import org.littleshoot.proxy.HttpFiltersAdapter;
import org.littleshoot.proxy.HttpFiltersSourceAdapter;
import org.littleshoot.proxy.HttpProxyServer;
import org.littleshoot.proxy.impl.DefaultHttpProxyServer;
import org.springframework.util.ResourceUtils;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.util.AttributeKey;
import net.lightbody.bmp.mitm.PemFileCertificateSource;
import net.lightbody.bmp.mitm.manager.ImpersonatingMitmManager;

/**
 * 功能:
 * @source
 * @依赖
 */
public class ManInMiddle {
    public static void main(String[] args) throws FileNotFoundException {
        new ManInMiddle().startServer();
    }
    public void startServer() throws FileNotFoundException {
        PemFileCertificateSource fileCertificateSource = new PemFileCertificateSource(
                ResourceUtils.getFile("classpath:my-ca.cer"),
                ResourceUtils.getFile("classpath:my-key.pem"),
                "123456");

        HttpProxyServer server =
                DefaultHttpProxyServer.bootstrap()
                        .withAddress(new InetSocketAddress("0.0.0.0", 8081))
//                        .withAllowRequestToOriginServer(true)
//                        .withServerResolver(new HostResolver() {
//                            @Override
//                            public InetSocketAddress resolve(String host, int port) throws UnknownHostException {
//                                return new InetSocketAddress("127.0.0.1", 8081);
//                            }
//                        })
                        .withChainProxyManager(new ChainedProxyManager() {
                            @Override
                            public void lookupChainedProxies(final HttpRequest httpRequest, Queue<ChainedProxy> chainedProxies) {
                                chainedProxies.add(new ChainedProxyAdapter() {
                                    @Override
                                    public InetSocketAddress getChainedProxyAddress() {
                                        return new InetSocketAddress("127.0.0.1", 6666);
                                    }
                                });
                                chainedProxies.add(ChainedProxyAdapter.FALLBACK_TO_DIRECT_CONNECTION);
                            }
                        })
                        .withManInTheMiddle(ImpersonatingMitmManager.builder().rootCertificateSource(fileCertificateSource).trustAllServers(true).build())
                        .withFiltersSource(new HttpFiltersSourceAdapter() {

                            @Override
                            public HttpFilters filterRequest(HttpRequest originalRequest) {
                                return super.filterRequest(originalRequest);
                            }

                            @Override
                            public int getMaximumResponseBufferSizeInBytes() {
                                return 10 * 1024 * 1024;
                            }

                            @Override
                            public int getMaximumRequestBufferSizeInBytes() {
                                return 10 * 1024 * 1024;
                            }

                            public HttpFilters filterRequest(HttpRequest originalRequest, ChannelHandlerContext ctx) {
                                String uri = originalRequest.getUri();
                                if (originalRequest.getMethod() == HttpMethod.CONNECT) {
                                    if (ctx != null) {
                                        String prefix = "https://" + uri.replaceFirst(":443$", "");
                                        ctx.channel().attr(AttributeKey.valueOf("connected_url")).set(prefix);
                                    }
                                    return new HttpFiltersAdapter(originalRequest);
                                }
                                String connectedUrl = (String) ctx.channel().attr(AttributeKey.valueOf("connected_url")).get();

                                return new HttpFiltersAdapter(originalRequest) {
                                    @Override
                                    public HttpResponse clientToProxyRequest(HttpObject httpObject) {
                                        if (httpObject instanceof FullHttpRequest) {
                                            FullHttpRequest request = (FullHttpRequest) httpObject;///1 ///7
                                            System.out.println((connectedUrl == null ? "" : connectedUrl) + request.getUri());
                                        }
                                        return null;
                                    }
                                };
                            }
                        })
                        .start();
    }
}
