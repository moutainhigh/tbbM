package com.xinguang.tubobo.impl.merchant.service;

import com.hzmux.hzcms.common.utils.StringUtils;
import com.xinguang.tubobo.impl.merchant.cache.RedisCache;
import com.xinguang.tubobo.impl.merchant.entity.MerchantDeliverFeeTemEntity;
import com.xinguang.tubobo.impl.merchant.repository.MerchantDeliverFeeTemRepository;
import com.xinguang.tubobo.impl.merchant.repository.MerchantTypeRepository;
import com.xinguang.tubobo.merchant.api.MerchantDeliverFeeTemInterface;
import com.xinguang.tubobo.merchant.api.dto.MerchantDeliverFeeTemDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangxb on 2017/10/13.
 */
@Service
@Transactional(readOnly = true)
public class MerchantDeliverTemService implements MerchantDeliverFeeTemInterface {
    @Autowired
    MerchantDeliverFeeTemRepository merchantDeliverFeeTemRepository;
    @Autowired
    MerchantTypeRepository merchantTypeRepository;

    @Override
    @Cacheable(value= RedisCache.MERCHANT,key="'merchantDeliverFeeTem_all'")
    public List<MerchantDeliverFeeTemDTO> findAll() {
        List<MerchantDeliverFeeTemEntity> merchantDeliverFeeTemEntities = merchantDeliverFeeTemRepository.findAllByDelFlagOrderByIdDesc(MerchantDeliverFeeTemEntity.DEL_FLAG_NORMAL);
        List<MerchantDeliverFeeTemDTO> merchantDeliverFeeTemDTOS = new ArrayList<>();

        if (merchantDeliverFeeTemEntities!=null&&merchantDeliverFeeTemEntities.size()>0){
            for (MerchantDeliverFeeTemEntity merchantDeliverFeeTemEntity : merchantDeliverFeeTemEntities) {
                MerchantDeliverFeeTemDTO merchantDeliverFeeTemDTO = new MerchantDeliverFeeTemDTO();
                BeanUtils.copyProperties(merchantDeliverFeeTemEntity, merchantDeliverFeeTemDTO);
                merchantDeliverFeeTemDTOS.add(merchantDeliverFeeTemDTO);
            }
        }
        return merchantDeliverFeeTemDTOS;
    }

    @Override
    @Cacheable(value= RedisCache.MERCHANT,key="'merchantDeliverFeeTem_'+#id")
    public MerchantDeliverFeeTemDTO findById(Long id) {
        MerchantDeliverFeeTemEntity merchantDeliverFeeTemEntity = merchantDeliverFeeTemRepository.findByIdAndDelFlag(id, MerchantDeliverFeeTemEntity.DEL_FLAG_NORMAL);
        MerchantDeliverFeeTemDTO merchantDeliverFeeTemDTO = new MerchantDeliverFeeTemDTO();

        if (merchantDeliverFeeTemEntity !=null){
            BeanUtils.copyProperties(merchantDeliverFeeTemEntity, merchantDeliverFeeTemDTO);
        }
        return merchantDeliverFeeTemDTO;
    }

    @Override
    @Cacheable(value= RedisCache.MERCHANT,key="'merchantDeliverFeeTem_'+#name")
    public MerchantDeliverFeeTemDTO findByName(String name) {
        MerchantDeliverFeeTemEntity merchantDeliverFeeTemEntity = merchantDeliverFeeTemRepository.findByNameAndDelFlag(name, MerchantDeliverFeeTemEntity.DEL_FLAG_NORMAL);
        MerchantDeliverFeeTemDTO merchantDeliverFeeTemDTO = new MerchantDeliverFeeTemDTO();

        if (merchantDeliverFeeTemEntity !=null){
            BeanUtils.copyProperties(merchantDeliverFeeTemEntity, merchantDeliverFeeTemDTO);
        }
        return merchantDeliverFeeTemDTO;
    }

    @Override
    @CacheEvict(value= RedisCache.MERCHANT,key="'merchantDeliverFeeTem_*'")
    @Transactional(readOnly = false)
    public Boolean delete(Long id) {
        MerchantDeliverFeeTemEntity merchantDeliverFeeTemEntity  = merchantDeliverFeeTemRepository.findByIdAndDelFlag(id, MerchantDeliverFeeTemEntity.DEL_FLAG_NORMAL);
        if (merchantDeliverFeeTemEntity!=null){
            merchantDeliverFeeTemEntity.setDelFlag(MerchantDeliverFeeTemEntity.DEL_FLAG_DELETE);
        }
        //TODO 需要对应删除关联表的数据吗???
        merchantDeliverFeeTemRepository.save(merchantDeliverFeeTemEntity);
        return true;
    }

    @Override
    @CacheEvict(value= RedisCache.MERCHANT,key="'merchantDeliverFeeTem_*'")
    @Transactional(readOnly = false)
    public Boolean save(MerchantDeliverFeeTemDTO merchantDeliverFeeTemDTO) {
        if (merchantDeliverFeeTemDTO == null){
            return false;
        }
        String name = merchantDeliverFeeTemDTO.getName();
        Long id = merchantDeliverFeeTemDTO.getId();
        if (id != null){
            MerchantDeliverFeeTemEntity entity = merchantDeliverFeeTemRepository.findByIdAndDelFlag(id,MerchantDeliverFeeTemEntity.DEL_FLAG_NORMAL);
            if (entity!=null){
                MerchantDeliverFeeTemEntity merchantDeliverFeeTemEntity = merchantDeliverFeeTemRepository.findByNameAndDelFlag(name, MerchantDeliverFeeTemEntity.DEL_FLAG_NORMAL);
                if (merchantDeliverFeeTemEntity==null||merchantDeliverFeeTemEntity.getName().equals(entity.getName())){
                    entity.setName(merchantDeliverFeeTemDTO.getName());
                    entity.setUpdateBy(merchantDeliverFeeTemDTO.getUpdateBy());
                    merchantDeliverFeeTemRepository.save(entity);
                    return true;
                }
            }
        } else{
            if (StringUtils.isNotBlank(name)){
                MerchantDeliverFeeTemEntity merchantDeliverFeeTemEntity = merchantDeliverFeeTemRepository.findByNameAndDelFlag(name, MerchantDeliverFeeTemEntity.DEL_FLAG_NORMAL);
                if (merchantDeliverFeeTemEntity ==null){
                    merchantDeliverFeeTemEntity = new MerchantDeliverFeeTemEntity();
                    merchantDeliverFeeTemEntity.setName(merchantDeliverFeeTemDTO.getName());
                    merchantDeliverFeeTemEntity.setCreateBy(merchantDeliverFeeTemDTO.getCreateBy());
                    merchantDeliverFeeTemRepository.save(merchantDeliverFeeTemEntity);
                    return true;
                }
            }
        }
        return false;
    }
}
