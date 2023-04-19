package 123.rbac.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import 123.AppBootstrapTest;
import 123.common.exception.BaseException;
import 123.common.model.dto.PageQueryDTO;
import 123.common.model.dto.PageResultDTO;
import 123.rbac.common.constant.RamRbacConst;
import 123.rbac.common.exception.code.RbacUserStatusCode;
import 123.rbac.common.model.bo.UserDetailBO;
import 123.rbac.model.dto.RbacCaptchaDTO;
import 123.rbac.model.dto.RbacUserDTO;
import 123.rbac.model.dto.UserLoginFormDTO;
import 123.rbac.model.query.RbacUserQuery;

public class RbacUserServiceTest extends AppBootstrapTest {

    @Before
    public void before() {
        RbacUserDTO userDTO = getUserDTO();
        boolean result = userService.saveEntity(userDTO);
        Assert.assertTrue(result);
    }


    @Test
    public void checkAvailable() {
        boolean result = userService.checkUserIfAvailable(getUserDTO());
        Assert.assertFalse(result);
        RbacUserDTO userDTO = getUserDTO();
        userDTO.setAccount(ACCOUNT + 1);
        userDTO.setEmail(1 + EMAIL);
        userDTO.setPhoneNumber("13800139000");
        boolean success = userService.checkUserIfAvailable(userDTO);
        Assert.assertTrue(success);
    }

    @Test
    public void login() {
        UserLoginFormDTO loginFormDTO = UserLoginFormDTO.builder()
                .account(ACCOUNT)
                .accountAuth(getPassword())
                .loginType(RamRbacConst.DEFAULT_LOGIN_TYPE_NORMAL_NAME)
                .build();

        RbacCaptchaDTO captchaDTO = getCaptcha();
        // 需要验证验证码的
        if (captchaDTO.getRequired()) {
            String code = getCaptchaCode(captchaDTO.getId());

            loginFormDTO.setImgValidCodeId(captchaDTO.getId());
            loginFormDTO.setImgValidCodeText(code);
        }

        UserDetailBO userDetailBO = userService.login(loginFormDTO);

        Assert.assertNotNull(userDetailBO);
    }

    @Test
    public void changePassword() {
        boolean result = userService.changeCurrentUserPassword(STATIC_ID, getPassword(), getPassword() + 1);
        Assert.assertTrue(result);
        try {
            boolean failed = userService.changeCurrentUserPassword(STATIC_ID, getPassword(), getPassword());
            Assert.assertFalse(failed);
        } catch (BaseException be) {
            Assert.assertEquals(RbacUserStatusCode.USER_PASSWORD_ERROR, be.getStatus());
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void page() {
        PageQueryDTO<RbacUserQuery> pageQuery = new PageQueryDTO<>();
        RbacUserQuery query = RbacUserQuery.builder().account(ACCOUNT).build();
        pageQuery.setParams(query);
        PageResultDTO<RbacUserDTO> pageResultDTO = userService.page(pageQuery);
        Assert.assertTrue(pageResultDTO.getTotal() >= 1L);
    }

    @Test
    public void getByUniqueKey() {
        RbacUserDTO userDTO = userService.getUserByUniqueKey(ACCOUNT);
        Assert.assertNotNull(userDTO);
    }

    @After
    public void done() {
        boolean result = userService.removeById(STATIC_ID);
        Assert.assertTrue(result);
    }
}
