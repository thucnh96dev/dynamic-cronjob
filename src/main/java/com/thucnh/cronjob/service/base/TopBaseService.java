package com.thucnh.cronjob.service.base;

import com.thucnh.cronjob.dao.base.BaseDao;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * @author : thucnh
 * @mailto : thucnh96.dev@gmail.com
 * @created :27/10/2021 - 10:36 AM
 */
public class TopBaseService<E,D extends BaseDao> {

    @Autowired
    protected D dao;

    /**
     *
     * @param id
     * @return E
     */
    public E findById(Long id){
        return (E) dao.findById(id);
    }

    /**
     *
     * @param e
     * @return E
     */
    public E save(E e){
        return (E) dao.save(e);
    }


    /**
     *
     * @param id
     */
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id){
        dao.findById(id);
    }

    /**
     *
     * @param ids
     */
    @Transactional(rollbackFor = Exception.class)
    public void deletes(Collection<Long> ids){
        for (Long id : ids){
            dao.deleteById(id);
        }
    }

    /**
     *
     * @param spec
     * @param pageable
     * @return Page
     */
    @Transactional(rollbackFor = Exception.class)
    public Page<E> findAll(Specification<E> spec, Pageable pageable){
        final   Page<E> data = dao.findAll(spec,pageable);
        return data;
    }

    /**
     *
     * @param pageable
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public List<E> findAll(Pageable pageable){
        final  List<E> data = (List<E>) dao.findAll(pageable);
        return data;
    }

    @Transactional(rollbackFor = Exception.class)
    public List<E> findAll(){
        final  List<E> data = (List<E>) dao.findAll();
        return data;
    }
}
