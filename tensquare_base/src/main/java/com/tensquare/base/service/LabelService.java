package com.tensquare.base.service;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import utils.IdWorker;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/***
 * @author dengliming
 * @email 295004647@qq.com
 * @version 1.0
 * @create 2018-09-19 18:51
 **/
@Service
public class LabelService {

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private IdWorker worker;


    public void save(Label label) {
        long id = worker.nextId();
        label.setId(id + "");
        labelDao.save(label);
    }

    public void update(Label label) {
        labelDao.save(label);
    }

    public List<Label> findAll() {
        return labelDao.findAll();
    }

    public Label findOne(String id) {
        return labelDao.findById(id).get();
    }

    public void delete(String id){
        labelDao.deleteById(id);
    }

    public List<Label> findAllByCondtion(Label label){

        return labelDao.findAll(new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                ArrayList<Predicate> arrayList = new ArrayList<>();
                if (label.getLabelname() != null && !"".equals(label.getLabelname())){
                    Expression<String> labelname = root.get("labelname");
                    Predicate like = criteriaBuilder.like(labelname, "%" + label.getLabelname() + "%");
                    arrayList.add(like);
                }
                if (label.getState() != null && !"".equals(label.getState())){
                    Expression<String> state = root.get("state").as(String.class);
                    Predicate like = criteriaBuilder.equal(state,label.getState());
                    arrayList.add(like);
                }

                Predicate[] predicates = new Predicate[arrayList.size()];
                predicates =  arrayList.toArray(predicates);

                return criteriaBuilder.and(predicates);
            }
        });

    }

    /**
     * 分页查询
     * @param label
     * @param curPage
     * @param pageSize
     * @return
     */
    public Page<Label> searchInfoByPage(Label label,int curPage,int pageSize){

        Specification<Label> specification = new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                ArrayList<Predicate> predicate = new ArrayList<>();
                if (label.getLabelname() != null && !"".equals(label.getLabelname())){
                    Predicate labelname = criteriaBuilder.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");
                    predicate.add(labelname);
                }
                if (label.getState() != null && !"".equals(label.getState())){
                    Predicate state = criteriaBuilder.equal(root.get("state").as(String.class), label.getState());
                    predicate.add(state);
                }
                Predicate[] predicates = new Predicate[predicate.size()];
                predicates = predicate.toArray(predicates);
                return criteriaBuilder.and(predicates);
            }
        };

        Pageable pageable = PageRequest.of(curPage - 1 ,pageSize);


        return labelDao.findAll(specification, pageable);


    }

}
