package 123.rbac.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import 123.AppBootstrapTest;
import 123.common.model.dto.PageQueryDTO;
import 123.common.model.dto.PageResultDTO;
import 123.rbac.model.dto.RbacPermissionDTO;
import 123.rbac.model.query.RbacPermissionQuery;

import java.util.List;

public class RbacPermissionServiceTest extends AppBootstrapTest {


    @Before
    public void init() {
        RbacPermissionDTO permissionDTO = getPermissionDTO();
        boolean result = permissionService.saveEntity(permissionDTO);
        Assert.assertTrue(result);
    }

    @Test
    public void page() {
        PageQueryDTO<RbacPermissionQuery> pageQuery = new PageQueryDTO<>();
        RbacPermissionQuery query = RbacPermissionQuery.builder().permissionCode(ACCOUNT).build();
        pageQuery.setParams(query);
        PageResultDTO<RbacPermissionDTO> pageResultDTO = permissionService.page(pageQuery);
        Assert.assertTrue( 1 == pageResultDTO.getTotal());
    }

    @Test
    public void list() {
        RbacPermissionQuery query = RbacPermissionQuery.builder().permissionCode(ACCOUNT).build();
        List<RbacPermissionDTO> list =  permissionService.list(query);
        Assert.assertEquals(1, list.size());
    }

    @After
    public void done() {
        boolean result = permissionService.removeById(STATIC_ID);
        Assert.assertTrue(result);
    }
}
