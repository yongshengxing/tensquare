package com.tensquare.user.service;

import com.tensquare.user.dao.UserDao;
import com.tensquare.user.pojo.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utils.IdWorker;
import utils.JwtUtil;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 服务层
 * 
 * @author Administrator
 *
 */
@Service
@Transactional
public class UserService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private IdWorker idWorker;

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private HttpServletRequest request;

	/**
	 * 查询全部列表
	 * @return
	 */
	public List<User> findAll() {
		return userDao.findAll();
	}

	
	/**
	 * 条件查询+分页
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<User> findSearch(Map whereMap, int page, int size) {
		Specification<User> specification = createSpecification(whereMap);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return userDao.findAll(specification, pageRequest);
	}

	
	/**
	 * 条件查询
	 * @param whereMap
	 * @return
	 */
	public List<User> findSearch(Map whereMap) {
		Specification<User> specification = createSpecification(whereMap);
		return userDao.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public User findById(String id) {
		return userDao.findById(id).get();
	}

	/**
	 * 增加
	 * @param user
	 */
	public void add(User user) {
		user.setId( idWorker.nextId()+"" );
		user.setId( idWorker.nextId()+"" );
		user.setPassword(encoder.encode(user.getPassword()));
		user.setFollowcount(0);//关注数
		user.setFanscount(0);//粉丝数
		user.setOnline(0L);//在线时长
		user.setRegdate(new Date());//注册日期
		user.setUpdatedate(new Date());//更新日期
		user.setLastdate(new Date());//最后登陆日期
		userDao.save(user);
	}

	/**
	 * 修改
	 * @param user
	 */
	public void update(User user) {
		userDao.save(user);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(String id) {
		// 删除用户，必须拥有管理员权限，否则不能删除。
		//前后端约定：前端请求微服务时需要添加头信息Authorization ,内容为Bearer+空格  +token
		//获取请求头
	/*	try {
			String authorization = request.getHeader("Authorization");
			if (authorization.isEmpty()) {
				throw new RuntimeException("权限不足");
			}
			if (!authorization.startsWith("Bearer ")) {
				throw new RuntimeException("权限不足");
			}
			String token = authorization.substring(new String("Bearer ").length());
			if (token == null || "".equals(token)) {
				throw new RuntimeException("权限不足");
			}

			Claims claims = jwtUtil.parseJWT(token);
			String roles = (String) claims.get("roles");
			String name = claims.getSubject();
			if (roles == null || !roles.equals("admin")) {
				throw new RuntimeException("权限不足");
			}
			//必须以admin管理员删除
			if (name == null || !name.equals("admin") || "".equals(name)){
				throw new RuntimeException("需要admin用户权限");
			}
			userDao.deleteById(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}*/
		String token = (String) request.getAttribute("admin_claims");
		if (token==null || "".equals(token)){
			throw new RuntimeException("权限不足！");
		}
		userDao.deleteById(id);


	}

	/**
	 * 动态条件构建
	 * @param searchMap
	 * @return
	 */
	private Specification<User> createSpecification(Map searchMap) {

		return new Specification<User>() {

			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                // ID
                if (searchMap.get("id")!=null && !"".equals(searchMap.get("id"))) {
                	predicateList.add(cb.like(root.get("id").as(String.class), "%"+(String)searchMap.get("id")+"%"));
                }
                // 手机号码
                if (searchMap.get("mobile")!=null && !"".equals(searchMap.get("mobile"))) {
                	predicateList.add(cb.like(root.get("mobile").as(String.class), "%"+(String)searchMap.get("mobile")+"%"));
                }
                // 密码
                if (searchMap.get("password")!=null && !"".equals(searchMap.get("password"))) {
                	predicateList.add(cb.like(root.get("password").as(String.class), "%"+(String)searchMap.get("password")+"%"));
                }
                // 昵称
                if (searchMap.get("nickname")!=null && !"".equals(searchMap.get("nickname"))) {
                	predicateList.add(cb.like(root.get("nickname").as(String.class), "%"+(String)searchMap.get("nickname")+"%"));
                }
                // 性别
                if (searchMap.get("sex")!=null && !"".equals(searchMap.get("sex"))) {
                	predicateList.add(cb.like(root.get("sex").as(String.class), "%"+(String)searchMap.get("sex")+"%"));
                }
                // 头像
                if (searchMap.get("avatar")!=null && !"".equals(searchMap.get("avatar"))) {
                	predicateList.add(cb.like(root.get("avatar").as(String.class), "%"+(String)searchMap.get("avatar")+"%"));
                }
                // E-Mail
                if (searchMap.get("email")!=null && !"".equals(searchMap.get("email"))) {
                	predicateList.add(cb.like(root.get("email").as(String.class), "%"+(String)searchMap.get("email")+"%"));
                }
                // 兴趣
                if (searchMap.get("interest")!=null && !"".equals(searchMap.get("interest"))) {
                	predicateList.add(cb.like(root.get("interest").as(String.class), "%"+(String)searchMap.get("interest")+"%"));
                }
                // 个性
                if (searchMap.get("personality")!=null && !"".equals(searchMap.get("personality"))) {
                	predicateList.add(cb.like(root.get("personality").as(String.class), "%"+(String)searchMap.get("personality")+"%"));
                }
				
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}

	public void sendSms(String mobile){

		String randomCode = RandomStringUtils.randomNumeric(6);
		//验证码存到redis
		redisTemplate.opsForValue().set("randomCode_" + mobile , randomCode,5, TimeUnit.HOURS);
		//发送消息
		Map<String,String> codeInfo = new HashMap<>();
		codeInfo.put("userMobile",mobile );
		codeInfo.put("randomCode",randomCode );
		rabbitTemplate.convertAndSend("sms" ,codeInfo );
		System.out.println(randomCode);
	}

	public boolean checkCode(String code, String mobile) {

		String randomCode = (String) redisTemplate.opsForValue().get("randomCode_" + mobile);
		if (randomCode.isEmpty()){
			throw  new RuntimeException("请点击获取短信验证码");
		}
		if (!randomCode.equals(code)){
			return false;
		}
		return true;
	}

	/**
	 * 用户登录查询
	 * @param username
	 * @param password
	 * @return
	 */
	public User findByUserName(String username,String password){

		User dbUser = userDao.findByNickname(username);
		if (dbUser != null && encoder.matches(password,dbUser.getPassword() )){
			return dbUser;
		}
		return null;
	}


	public void updateFansCount(int num,String id){
		userDao.updateFansCount(num,id);
	}

	public void updateFollowCount(int num,String id){
		userDao.updateFollowCount(num,id );
	}


}
