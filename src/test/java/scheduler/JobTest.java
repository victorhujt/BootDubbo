package scheduler;

import com.alibaba.dts.common.exception.InitException;
import com.alibaba.edas.schedulerX.SchedulerXClient;

public class JobTest {
    public static void main(String[] args) throws InitException {
        SchedulerXClient schedulerXClient = new SchedulerXClient();
        schedulerXClient.setGroupId("201-3-2-3913");
        schedulerXClient.setRegionName("cn-test");
        schedulerXClient.init();
    }
}