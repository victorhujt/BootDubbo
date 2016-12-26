package com.xescm.ofc.service;


import com.xescm.base.model.dto.auth.AuthResDto;

import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>Title:	  TokenService. </p>
 * <p>Description jwt token </p>
 * <p>Company:    http://www.hnxianyi.com </p>
 *
 * @Author         <a href="liu_zhaoming@sina.cn"/>刘兆明</a>
 * @CreateDate     2016/11/27 11:39
 */
public interface TokenService {


    /**
     * 获取Token的解密Key
     * @return
     */
    String getViewPrivateKey();

    /**
     * 获取Token的解密API-Key
     * @return
     */
    String getApiPrivateKey();


    /**
     * 根据AppKey生成API的Token并返回
     * @param appKey
     * @return
     */
    String getApiTokenByAppKey(String appKey);

    /**
     * 设置Token的Key
     * @param tokenKey
     */
    void setTokenKey(String tokenKey);


    /**
     * 设置Api的Token的Key
     * @param tokenKey
     */
    void setApiTokenKey(String tokenKey);

    /**
     * <p>Title:	  getAuthResDtoByToken. </p>
     * <p>Description 获取Jwt权限信息</p>
     *
     * @param
     * @Author        <a href="liu_zhaoming@sina.cn"/>刘兆明</a>
     * @CreateDate    2016/11/27 14:05
     * @return
     */
    AuthResDto getAuthResDtoByToken();

    String encodeToken(AuthResDto authResDto);


    /**
     * 根据AppKey,payload相关数据,做加密处理
     * @param appKey
     * @return
     */
    String encodeApiToken(String apiPrivateKey, String appKey);

    /**
     * 此处制作Token有效性校验,不校验AppKey
     * @param token
     * @return
     * @throws ServletException
     */
    void checkApiToken(String token) throws ServletException;

    /**
     *
     * <p>Title:	  getAuthResDtoByToken </p>
     * <p>Description 获取AuthResDto </p>
     * @Author        <a href="liu_zhaoming@sina.cn"/>刘兆明</a>
     * @CreateDate    2016年10月20日 下午1:02:48 <br/>
     * @param resp
     * @param token
     * @return
     * @throws Exception
     */
    AuthResDto getAuthResDtoByToken(HttpServletResponse resp, String token) throws Exception;


    /**
     * 续租Token
     * @param token
     */
    void reletViewToken(HttpServletResponse resp, String token);


    /**
     * Filter异常处理
     * @param req
     * @param res
     * @param msg
     * @return
     * @throws IOException
     */
    boolean handleException(HttpServletRequest req, ServletResponse res, String msg) throws IOException ;

    /**
     *
     * <p>Title:	  handleNotPermException </p>
     * <p>Description 处理没有权限问题 </p>
     * @Author        <a href="liu_zhaoming@sina.cn"/>刘兆明</a>
     * @CreateDate    2016年10月23日 上午9:59:43 <br/>
     * @param response
     * @param html
     * @return
     * @throws IOException
     */
    boolean handleNotPermException(HttpServletResponse response, String ontentType, String html) throws IOException ;





}
