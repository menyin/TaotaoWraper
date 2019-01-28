package dbmapper;

import com.taotao.common.pojo.SearchItem;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.service.SearchItemService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
public class SearchItemServiceTest {

    @Test
    public void getList(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        SearchItemService searchItemService=(SearchItemService)applicationContext.getBean("searchItemService");
        List<SearchItem> itemList = searchItemService.getItemList();
        SearchItem searchItem = itemList.get(0);
        System.out.println(itemList.size());
        System.out.println(searchItem.getId());
        System.out.println(searchItem.getCategory_name());
        System.out.println(searchItem.getPrice());

    }
    @Test
    public void importItemsToIndex(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        SearchItemService searchItemService=(SearchItemService)applicationContext.getBean("searchItemService");
        TaotaoResult taotaoResult = searchItemService.importItemsToIndex();
        System.out.println(taotaoResult);
    }
}
