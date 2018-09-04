package nbcenter.test.service;

import com.pi.nbcenter.device.bean.dto.nb.manage.ManageCompanyInfo;
import com.pi.nbcenter.device.service.e.BaseEService;
import com.pi.nbcenter.device.service.e.ManageService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author lusj
 * @version V1.0
 * @Type DeviceETest
 * @Desc
 * @date 2018/6/25
 */
public class DeviceETest {


    private Logger logger = LoggerFactory.getLogger(DeviceETest.class);


    @Autowired
    private BaseEService baseEService;

    @Autowired
    private ManageService manageService;


    @Test
    public void getAccessToken() {

      String token =  baseEService.getAccessToken();
        System.out.println(token);
    }



}
