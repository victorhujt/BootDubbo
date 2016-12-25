package com.xescm.ofc.web.jwt;

import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.base.util.JacksonUtil;
import com.xescm.uam.exception.BusinessException;
import com.xescm.uam.model.enums.ResultCodeEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;


/**
 * Created by wangsongtao on 16/3/31.
 */
@Service
public class TokenUtils {
	
	private static Logger logger = LoggerFactory.getLogger(TokenUtils.class);
	
    //Token过期剩余时间,根据此变量设置续租
    @Value("${auth.expiredRemainMinutes}")
    private Integer expiredRemainMinutes;

    //Token过期分钟数
    @Value("${auth.expiredMinutes}")
    private Integer expiredMinutes;
    
    public String encodeToken(AuthResDto authResDto){
    	String token = null;
    	try {
			String authResDtoString = JacksonUtil.toJson(authResDto);
			//将用户信息放入Token
			DateTime nowDate = new DateTime();
			DateTime laterDate = nowDate.plusMinutes(expiredMinutes.intValue());
			String tokenKey = AppkeyLoader.getViewPrivateKey();
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
            String tokenKey = AppkeyLoader.getApiPrivateKey();
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
            String tokenKey = AppkeyLoader.getViewPrivateKey();
            final Claims claims = Jwts.parser().setSigningKey(tokenKey).parseClaimsJws(token).getBody();
            String authResDtoString = (String) claims.get("authResDto");
            authResDto = JacksonUtil.parseJson(authResDtoString, AuthResDto.class);
        } catch (Exception e) {
        	logger.error("token解密失败={} ", e.getMessage(), e );
            throw new BusinessException(ResultCodeEnum.ES000006.code(), ResultCodeEnum.ES000006.msg());
        }
        return authResDto;
    }


    /**
     * 续租Token
     * @param token
     */
    public void reletViewToken(HttpServletResponse resp,String token){
        String tokenKey = AppkeyLoader.getViewPrivateKey();
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
	public boolean handleNotPermException(HttpServletResponse response, String ontentType, String html) throws IOException{
		
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
