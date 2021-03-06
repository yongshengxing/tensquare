package com.tensquare.qa.controller;
import java.util.List;
import java.util.Map;

import com.tensquare.qa.cilent.LableCilent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tensquare.qa.pojo.Problem;
import com.tensquare.qa.service.ProblemService;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;
/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/problem")
public class ProblemController {

	@Autowired
	private ProblemService problemService;

	@Autowired
	private LableCilent labelClient;
	
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",problemService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",problemService.findById(id));
	}


	/**
	 * 分页+多条件查询
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
		Page<Problem> pageList = problemService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Problem>((int) pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",problemService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param problem
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody Problem problem  ){
		problemService.add(problem);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param problem
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody Problem problem, @PathVariable String id ){
		problem.setId(id);
		problemService.update(problem);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		problemService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}


	/**
	 * 查询最新问题
	 * @param label
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/newlist/{label}/{page}/{size}",method= RequestMethod.GET)
	public Result findNewList(@PathVariable String label,@PathVariable int page,@PathVariable int size ){
		Page<Problem> problems = problemService.findNewList(label,page,size);
		return new Result(true,StatusCode.OK,"查询成功",new PageResult<Problem>(problems.getTotalPages(),problems.getContent()));
	}




	/**
	 * 查询带回复
	 * @param label
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/waitlist/{label}/{page}/{size}",method= RequestMethod.GET)
	public Result waitList(@PathVariable String label,@PathVariable int page,@PathVariable int size ){
		Page<Problem> problems = problemService.waitlist(label,page,size);
		return new Result(true,StatusCode.OK,"查询成功",new PageResult<Problem>(problems.getTotalPages(),problems.getContent()));
	}




	/**
	 * 查询最热门回答
	 * @param label
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/hotlist/{label}/{page}/{size}",method= RequestMethod.GET)
	public Result findHotList(@PathVariable String label,@PathVariable int page,@PathVariable int size ){
		Page<Problem> problems = problemService.hotlist(label,page,size);
		return new Result(true,StatusCode.OK,"查询成功",new PageResult<Problem>(problems.getTotalPages(),problems.getContent()));
	}



	/**
	 * 所有问题
	 * @param label
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/all/{label}/{page}/{size}",method= RequestMethod.GET)
	public Result findAll(@PathVariable String label,@PathVariable int page,@PathVariable int size ){
		Page<Problem> problems = problemService.findAllProduct(label,page,size);
		return new Result(true,StatusCode.OK,"查询成功",new PageResult<Problem>(problems.getTotalPages(),problems.getContent()));
	}


	@RequestMapping(value = "/label/{labelid}")
	public Result findLabelById(@PathVariable String labelid){
		Result result = labelClient.findOne(labelid);
		return result;
	}

	@RequestMapping("/findAll")
	public Result findAlls(){
		Result result = labelClient.findAll();
		return result;
	}


}
