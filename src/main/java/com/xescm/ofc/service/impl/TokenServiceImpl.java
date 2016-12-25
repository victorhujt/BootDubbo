package com.xescm.ofc.service.impl;

import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.core.constant.UamConstant;
import com.xescm.core.exception.BusinessException;
import com.xescm.core.utils.*;
import com.xescm.ofc.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * <p>Title:	  TokenServiceImpl. </p>
 * <p>Company:    http://www.hnxianyi.com </p>
 *
 * @Author         wangsongtao
 * @CreateDate     2016/11/27 14:18
 */
@Service
public class TokenServiceImpl implements TokenService {

    private static Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);

    //Token过期剩余时间,根据此变量设置续租
    @Value("${auth.expiredRemainMinutes}")
    private Integer expiredRemainMinutes;

    //Token过期分钟数
    @Value("${auth.expiredMinutes}")
    private Integer expiredMinutes;

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Value("${env}")
    private String dev;

    private static final String SECURITY_KEY = "secretKey";
    private static final String API_SECURITY_KEY = "secretKey";
    private static final String TOKEN_KEY = "PSD_TOKEN_KEY";
    private static final String TOKEN_API_KEY = "PSD_TOKEN_API_KEY";


    /**
     * 获取页面的Token的密钥
     */
    public String getViewPrivateKey(){
        ValueOperations<String,String> ops = redisTemplate.opsForValue();
        String decodeKey = ops.get(TOKEN_KEY);
        if (PubUtils.isNull(decodeKey)){
            decodeKey = SECURITY_KEY;
        }else{
            // 如果不为空,则需要解密后返回
            decodeKey = MD5Util.decodeByAES(decodeKey);
        }
        return decodeKey;
    }


    /**
     * 获取API的Token密钥
     */
    public String getApiPrivateKey(){
        ValueOperations<String,String> ops = redisTemplate.opsForValue();
        String decodeKey = ops.get(TOKEN_API_KEY);
        if (PubUtils.isNull(decodeKey)){
            decodeKey = API_SECURITY_KEY;
        }else{
            // 如果不为空,则需要解密后返回
            decodeKey = MD5Util.decodeByAES(decodeKey);
        }
        return decodeKey;
    }

    @Override
    public String getApiTokenByAppKey(String appKey) {
        return encodeApiToken(getApiPrivateKey(),appKey);
    }

    public void setApiTokenKey(String tokenKey){
        String encodeKey;
        if (PubUtils.isNull(tokenKey)){
            encodeKey = API_SECURITY_KEY;
        }else{
            encodeKey = tokenKey;
        }
        ValueOperations<String,String> ops = redisTemplate.opsForValue();
        //加密后存入
        ops.set(TOKEN_API_KEY, MD5Util.encodeByAES(encodeKey));
    }


    public void setTokenKey(String tokenKey){
        String encodeKey;
        if (PubUtils.isNull(tokenKey)){
            encodeKey = SECURITY_KEY;
        }else{
            encodeKey = tokenKey;
        }
        ValueOperations<String,String> ops = redisTemplate.opsForValue();
        //加密后存入
        ops.set(TOKEN_KEY, MD5Util.encodeByAES(encodeKey));
    }

    @Override
    public AuthResDto getAuthResDtoByToken() {
        AuthResDto authResDto = null;
        if("debug".equals(dev)){
            // TODO 预留
        }else {
            authResDto = (AuthResDto) ThreadLocalMap.get(UamConstant.TOKEN_AUTH_DTO);
            if(PublicUtil.isEmpty(authResDto)){
                throw new BusinessException("验证token失败");
            }
        }
        return authResDto;
    }

    public String encodeToken(AuthResDto authResDto){
        String token = null;
        try {
            String authResDtoString = JacksonUtil.toJson(authResDto);
            //将用户信息放入Token
            DateTime nowDate = new DateTime();
            DateTime laterDate = nowDate.plusMinutes(expiredMinutes.intValue());
            String tokenKey = getViewPrivateKey();//AppkeyLoader.getViewPrivateKey();
            token = Jwts.builder().setSubject(authResDto.getLoginName()).claim("authResDto", authResDtoString).setIssuedAt(nowDate.toDate()).setExpiration(laterDate.toDate()).signWith(SignatureAlgorithm.HS256, tokenKey).compact();
        } catch (Exception e) {
            logger.error("生成token, 出现异常={}", e.getMessage(), e);
            throw new BusinessException("生成token失败!");
        }
        return token;
    }


    /**
     * 根据AppKey,payload相关数据,做加密处理
     * @param appKey
     * @return
     */
    public String encodeApiToken(String apiPrivateKey,String appKey){
        //将用户信息放入Token
        DateTime nowDate = new DateTime();
        //API的Token过期时间默认先设置成1年
        DateTime oneYearLater = nowDate.plusYears(1);
        String tokenKey = apiPrivateKey;
        String token = Jwts.builder().setSubject(appKey).setIssuedAt(nowDate.toDate()).setExpiration(oneYearLater.toDate()).signWith(SignatureAlgorithm.HS256, tokenKey).compact();
        return token;
    }

    /**
     * 此处制作Token有效性校验,不校验AppKey
     * @param token
     * @return
     * @throws ServletException
     */
    public void checkApiToken(String token) throws ServletException {
        try {
            String tokenKey = getApiPrivateKey();//AppkeyLoader.getApiPrivateKey();
            Jwts.parser().setSigningKey(tokenKey).parseClaimsJws(token).getBody();
        } catch (final SignatureException e) {
            throw new ServletException("Invalid API token.");
        }
    }

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
    public AuthResDto getAuthResDtoByToken(HttpServletResponse resp, String token) throws Exception{
        AuthResDto authResDto = null;
        try {
            //前端做续租处理
            reletViewToken(resp,token);
            String tokenKey = getViewPrivateKey();//AppkeyLoader.getViewPrivateKey();
            final Claims claims = Jwts.parser().setSigningKey(tokenKey).parseClaimsJws(token).getBody();
            String authResDtoString = (String) claims.get("authResDto");
            authResDto = JacksonUtil.parseJson(authResDtoString, AuthResDto.class);
        } catch (Exception e) {
            logger.error("token解密失败={} ", e.getMessage(), e );
            throw new BusinessException("token解密失败");
        }
        return authResDto;
    }


    /**
     * 续租Token
     * @param token
     */
    public void reletViewToken(HttpServletResponse resp,String token){
        String tokenKey = getViewPrivateKey();//AppkeyLoader.getViewPrivateKey();
        final Claims claims = Jwts.parser().setSigningKey(tokenKey).parseClaimsJws(token).getBody();
        Date expiration = claims.getExpiration();
        String authResDtoString = (String) claims.get("authResDto");
        AuthResDto authResDto;
        DateTime nowDate = new DateTime();
        DateTime expireDate = new DateTime(expiration);

        if(nowDate.plusMinutes(expiredRemainMinutes.intValue()).isAfter(expireDate)){
            try {
                authResDto = JacksonUtil.parseJson(authResDtoString, AuthResDto.class);
            } catch (Exception e) {
                logger.error("==> 解析token失败={}", e.getMessage(), e);
                throw new BusinessException("解析token失败");
            }
            //如果当前时间到过期时间小于20分钟,则需要续租
            String newToken = this.encodeToken(authResDto);
            resp.setHeader("newtoken",newToken);
        }
    }


    /**
     * Filter异常处理
     * @param req
     * @param res
     * @param msg
     * @return
     * @throws IOException
     */
    public boolean handleException(HttpServletRequest req, ServletResponse res, String msg) throws IOException {
        if(req.getRequestURL().toString().contains("api")){
            //如果是API进来的处理,则直接返回JSON
            res.resetBuffer();
            res.setContentType("application/json");
            res.getWriter().write(msg);
            res.flushBuffer();
        }else{
            //TODO 其他请求默认为页面请求,跳转到error页面
            ((HttpServletResponse)res).sendRedirect("/errorAuth");
        }
        return false;
    }

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
    public boolean handleNotPermException(HttpServletResponse response, String ontentType, String html) throws IOException {

        response.resetBuffer();
        response.setContentType(ontentType);
        response.getWriter().print(html);
        response.flushBuffer();
        return false;

    }

    /**
     * 向客户端输出响应
     */
    protected void sendResponse(HttpServletResponse response, String text){

        try {
            response.setContentType("application/json; charset=UTF-8");
            response.setHeader("Cache-Control", "no-cache");
            PrintWriter out = response.getWriter();
            out.print(text);
            out.flush() ;
        } catch (Exception e){
            logger.error("sendResponse has Exception={}", e);
        }
    }
}
