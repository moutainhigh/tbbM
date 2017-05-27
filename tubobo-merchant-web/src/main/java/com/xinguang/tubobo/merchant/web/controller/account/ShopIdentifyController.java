package com.xinguang.tubobo.merchant.web.controller.account;

import com.hzmux.hzcms.common.utils.AliOss;
import com.hzmux.hzcms.common.utils.StringUtils;
import com.xinguang.tubobo.account.api.TbbAccountService;
import com.xinguang.tubobo.account.api.request.AccountInfoRequest;
import com.xinguang.tubobo.account.api.response.AccountInfo;
import com.xinguang.tubobo.account.api.response.TbbAccountResponse;
import com.xinguang.tubobo.impl.merchant.common.AESUtils;
import com.xinguang.tubobo.impl.merchant.manager.MerchantInfoManager;
import com.xinguang.tubobo.merchant.api.enums.EnumIdentifyType;
import com.xinguang.tubobo.merchant.web.MerchantBaseController;
import com.xinguang.tubobo.merchant.api.MerchantClientException;
import com.xinguang.tubobo.merchant.api.enums.EnumRespCode;
import com.xinguang.tubobo.merchant.api.enums.EnumAuthentication;
import com.xinguang.tubobo.impl.merchant.entity.MerchantInfoEntity;
import com.xinguang.tubobo.impl.merchant.service.MerchantInfoService;
import com.xinguang.tubobo.merchant.web.request.ShopIdentifyRequest;
import com.xinguang.tubobo.merchant.web.response.MerchantInfoResponse;
import com.xinguang.tubobo.merchant.web.response.RespShopIdentify;
import com.xinguang.tubobo.rider.api.RiderToAdminServiceInterface;
import com.xinguang.tubobo.rider.api.dto.RiderInfoDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2017/4/13.
 */
@Controller
@RequestMapping(value = "/shop/identify")
public class ShopIdentifyController extends MerchantBaseController<ShopIdentifyRequest,MerchantInfoResponse> {
//    @Autowired
//    MerchantInfoService merchantInfoService;
//    @Autowired
//    RiderToAdminServiceInterface riderToAdminServiceInterface;
//
//    @Autowired
//    TbbAccountService tbbAccountService;
    @Autowired
    private MerchantInfoManager merchantInfoManager;

    @Override
    protected MerchantInfoResponse doService(String userId, ShopIdentifyRequest req) throws MerchantClientException {
        logger.info("收到店铺申请请求 ：{}，",req.toString() );

//        RiderInfoDTO riderInfoDTO = riderToAdminServiceInterface.findByUserId(userId);
//        if (riderInfoDTO!= null){
//            logger.error("店铺申请失败，该用户已经申请成为骑手。userId:{}",userId);
//            throw new MerchantClientException(EnumRespCode.MERCHANT_ALREADY_APPLY_RIDER);
//        }
//        req.setIdCardFrontImageUrl(AliOss.subAliossUrl(req.getIdCardFrontImageUrl()));
//        req.setIdCardBackImageUrl(AliOss.subAliossUrl(req.getIdCardBackImageUrl()));
//        req.setShopImageUrl(AliOss.subAliossUrl(req.getShopImageUrl()));
//        MerchantInfoEntity infoEntity  = merchantInfoService.findByUserId(userId);
//        if (null == infoEntity){
//            // 生成账户信息
//            AccountInfoRequest request = new AccountInfoRequest();
//            request.setName(req.getRealName());
//            request.setPhone(req.getPhone());
//            MerchantInfoEntity entity = translateRequestToEntity(userId,req);
//
//            String password = AESUtils.decrypt(req.getPayPassword());
//            if (StringUtils.isBlank(password)){
//                throw new MerchantClientException(EnumRespCode.PASSWORD_DECRYPT_FAIL);
//            }
//            TbbAccountResponse<AccountInfo> response = tbbAccountService.createAccount(userId,password,request);
//            if (response != null && response.isSucceeded() && null != response.getData()){
//                logger.info("create account info SUCCESS. request:{}, response:{}",response.getErrorCode(),response.getMessage(),request.toString(),response.getData().toString());
//                Long accountId = response.getData().getId();
//                entity.setAccountId(accountId);
//                merchantInfoService.merchantApply(userId,entity);
//
//                MerchantInfoEntity entity1 = merchantInfoService.findByUserId(userId);
//                RespShopIdentify resp = new RespShopIdentify();
//                if (null != entity1){
//                    BeanUtils.copyProperties(entity1,resp);
//                }
//                logger.info("店铺申请响应，userId:{},response:{}",resp.toString());
//                return resp;
//            }else{
//                if (null != null){
//                    logger.error("create account info FAIL. errorCode:{}, errorMsg:{}, request:{}, response:{}",response.getErrorCode(),response.getMessage(),request.toString(),response.toString());
//                }
//                throw new MerchantClientException(EnumRespCode.ACCOUNT_CREATE_FAIL) ;
//            }
//        }else if ( EnumAuthentication.FROZEN.getValue().equals(infoEntity.getMerchantStatus())){
//            throw new MerchantClientException(EnumRespCode.MERCHANT_FROZEN);
//        }else if (null != infoEntity && EnumAuthentication.SUCCESS.getValue().equals(infoEntity.getMerchantStatus())){
//            throw new MerchantClientException(EnumRespCode.MERCHANT_APPLY_REPEAT);
//        }else if (null != infoEntity && EnumAuthentication.APPLY.getValue().equals(infoEntity.getMerchantStatus())){
//            throw new MerchantClientException(EnumRespCode.MERCHANT_VERIFYING);
//        }else {
//            BeanUtils.copyProperties(req,infoEntity);
//            boolean result = false;
//            TbbAccountResponse<Boolean> response = tbbAccountService.resetPayPassword(infoEntity.getAccountId(),AESUtils.decrypt(req.getPayPassword()));
//            if(response != null && response.isSucceeded() && response.getData()){
//                logger.info("重新认证，重置支付密码成功：userID：{}",userId);
//                result = merchantInfoService.merchantUpdate(infoEntity);
//            }
//            if (!result){
//                throw new MerchantClientException(EnumRespCode.FAIL);
//            }
//            MerchantInfoEntity entity1 = merchantInfoService.findByUserId(userId);
//            RespShopIdentify resp = new RespShopIdentify();
//            if (null != entity1){
//                BeanUtils.copyProperties(entity1,resp);
//            }
//            return resp;
//        }
        MerchantInfoEntity infoEntity = new MerchantInfoEntity();
        BeanUtils.copyProperties(req,infoEntity);
        MerchantInfoEntity respEntity = merchantInfoManager.identify(userId,req.getPayPassword(), EnumIdentifyType.MERCHANT.getValue(),infoEntity);
        MerchantInfoResponse infoResponse = new MerchantInfoResponse();
        BeanUtils.copyProperties(respEntity,infoResponse);
        return infoResponse;
    }

    /**
     * 请求参数和实体参数转化
     * @param userId
     * @param shopIdentifyRequest
     * @return
     */
    private MerchantInfoEntity translateRequestToEntity(String userId,ShopIdentifyRequest shopIdentifyRequest){
        MerchantInfoEntity merchant = new MerchantInfoEntity();
        BeanUtils.copyProperties(shopIdentifyRequest,merchant);
        merchant.setUserId(userId);
        merchant.setIdentifyType(EnumIdentifyType.MERCHANT.getValue());
        return merchant;
    }

    @Override
    protected boolean needIdentify() {
        return false;
    }
}
