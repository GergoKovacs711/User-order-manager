package hu.eteosf.gergokovacs.userorders;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import hu.eteosf.gergokovacs.userorders.service.DefaultUserService;

@SpringBootApplication
@ComponentScan(excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = DefaultUserService.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = UserOrdersManagerApplication.class)}
)
public class TestUserOrdersManagerApplication {
    public static void main(String[] args) { }
}
